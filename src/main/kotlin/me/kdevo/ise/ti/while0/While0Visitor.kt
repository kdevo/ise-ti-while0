package me.kdevo.ise.ti.while0

import org.antlr.v4.runtime.tree.TerminalNode
import java.io.IOException
import java.util.*
import kotlin.streams.toList

open class VariableException(message: String) : IOException(message)
class VariableNotDeclaredException(variable: String) : VariableException("$variable is not declared!")


class While0CompilerVisitor(private val withDebugInfo: Boolean = false) : While0BaseVisitor<String>() {
    var compiledUrm = mutableListOf<String>()
    var varToReg = mutableMapOf<String, String>()
    var beginLineNoStack = Stack<Pair<String, Int>>()
    var replaceMarkerLineNoStack = Stack<Int>()
    var regCount = 1;
    var bodyCount = 1;


    override fun visitHeaderSignature(ctx: While0Parser.HeaderSignatureContext): String? {
        val inputVars = ctx.headerIn().`var`().stream().map { v -> v.text }.distinct().toList()
        for (v in inputVars) {
            varToReg[v] = newReg()
        }
        val inputRegisters = varToReg.values.joinToString(",")
        val outputVar = ctx.headerOut().`var`().text
        varToReg[outputVar] = newReg()
        // Just an example for compiler checks:
        if (inputVars.contains(outputVar)) {
            throw VariableException("Output variable $outputVar cannot also be an input variable!")
        }
        printDebugInfo("Header signature: ${ctx.headerProgramName().text}($inputRegisters, ${varToReg[outputVar]});")
        return super.visitHeaderSignature(ctx)
    }

    override fun visitHeaderLocals(ctx: While0Parser.HeaderLocalsContext): String? {
        val localVars = ctx.`var`().stream().map { v -> v.text }.distinct().toList()
        for (v in localVars) {
            varToReg[v] = newReg()
        }
        val localRegisters = localVars.map { v -> varToReg[v] }.toList().joinToString(",")
        printDebugInfo("Locals: loc($localRegisters...);")
        return super.visitHeaderLocals(ctx)
    }

    override fun visitVar(ctx: While0Parser.VarContext): String {
        return varToReg[ctx.text] ?: throw VariableNotDeclaredException(ctx.text)
    }

    override fun visitStmtAssignment(ctx: While0Parser.StmtAssignmentContext): String? {
        val reg1 = visitVar(ctx.`var`()[0])
        if (ctx.ZERO() != null) {
            return urm("assign $reg1 = 0", "$reg1 = 0;")
        } else {
            val reg2 = visitVar(ctx.`var`()[1])
            return urm("assign $reg1 = $reg2 + 1", "$reg2++\n${copy(reg1, reg2)}")
        }
    }

    override fun visitBody(ctx: While0Parser.BodyContext): String? {
        bodyCount++;
        return super.visitBody(ctx)
    }

    override fun visitStmtWhile(ctx: While0Parser.StmtWhileContext): String? {
        val reg1 = visitVar(ctx.`var`()[0])
        val reg2 = visitVar(ctx.`var`()[1])
        val tmpReg1 = newReg()
        val tmpReg2 = newReg()
        val jmpCopy1 = addCopy(reg1, tmpReg1)
        val jmpCopy2 = addCopy(reg2, tmpReg2)
        // Push first copy statement line number to stack (we need it after executing the while body):
        beginLineNoStack.push(Pair("while $reg1 != $reg2", jmpCopy1))

        urm(
            "HEAD: while $reg1 != $reg2",
            """
            if $tmpReg1 == 0 goto ${jmp(6)};
            if $tmpReg2 == 0 goto ${jmp(5)};
            $tmpReg1--;
            $tmpReg2--;
            goto ${jmp(1)};
            if $tmpReg2 == 0 goto END;"""
        )
        // Push while body line number to stack:
        replaceMarkerLineNoStack.push(compiledUrm.size - if (withDebugInfo) 2 else 1)
        return super.visitStmtWhile(ctx)
    }

    override fun visitTerminal(node: TerminalNode): String? {
        when (node.text) {
            "end" -> {
                val whileBegin = beginLineNoStack.pop()
                printDebugInfo("Reached end of while ${whileBegin.first}. Replacing markers.")
                urm("TAIL: ${whileBegin.first}", "goto ${whileBegin.second}")
                val replaceLineNo = replaceMarkerLineNoStack.pop()
                compiledUrm[replaceLineNo] = compiledUrm[replaceLineNo].replace("END", "${compiledUrm.size}")
            }
        }
        return super.visitTerminal(node)
    }

    private fun addCopy(fromReg: String, toReg: String): Int {
        urm("COPY($fromReg, $toReg)", copy(fromReg, toReg))
        return compiledUrm.size
    }

    private fun copy(fromReg: String, toReg: String): String {
        val tmpReg = newReg()
        return """
            $fromReg = 0;
            if $toReg == 0 goto ${jmp(6)};
            $toReg--;
            $tmpReg++;
            goto ${jmp(2)};
            if $tmpReg == 0 goto ${jmp(11)};
            $tmpReg--;
            $fromReg++;
            $toReg++;
            goto ${jmp(6)};""".trimIndent()
    }

    private fun jmp(no: Int): Int {
        // When there is debug info, there is one prepended line, that's why 1 is added in this case:
        return compiledUrm.size + no + if (withDebugInfo) 1 else 0
    }

    private fun newReg(): String {
        return "R${regCount++}"
    }

    private fun printDebugInfo(text: String) {
        if (withDebugInfo) {
            println("DEBUG: $text")
        }
    }

    private fun urm(name: String, code: String): String {
        printDebugInfo("Processing $name (lines: ${code.lines().size})...")
        val expandedCode: String;
        if (withDebugInfo) {
            expandedCode = "; START `$name`\n" + code.replaceIndent("    ") + "\n; END `$name`"
        } else {
            expandedCode = code.replaceIndent("");
        }
        expandedCode.lines().forEach { line -> compiledUrm.add(line) };
        return expandedCode
    }
}