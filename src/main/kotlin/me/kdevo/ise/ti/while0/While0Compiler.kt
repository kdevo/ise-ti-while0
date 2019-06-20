package me.kdevo.ise.ti.while0

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ConsoleErrorListener
import org.antlr.v4.runtime.ParserRuleContext

class While0Compiler(private val addDebugInfo: Boolean = false) {
    var output = mutableListOf<String>()

    fun parse(while0code: CharStream): ParserRuleContext {
        val lexer = While0Lexer(while0code)
        val listener = While0ErrorLIstener()
        lexer.removeErrorListener(ConsoleErrorListener.INSTANCE)
        lexer.addErrorListener(listener)
        val parser = While0Parser(CommonTokenStream(lexer))
        parser.addErrorListener(listener)
        return parser.program()
    }

    fun compile(parserContext: ParserRuleContext): List<String> {
        val visitor = While0CompilerVisitor(addDebugInfo);
        val res = visitor.visit(parserContext)
        output = visitor.compiledUrm
        return output
    }

    fun compile(while0code: CharStream): List<String> {
        return compile(parse(while0code))
    }

    fun printResult(withLineNo: Boolean = true) {
        for (indexedLine in output.withIndex()) {
            if (withLineNo) {
                println("${indexedLine.index + 1}: ${indexedLine.value}")
            } else {
                println(indexedLine.value)
            }
        }
    }
}