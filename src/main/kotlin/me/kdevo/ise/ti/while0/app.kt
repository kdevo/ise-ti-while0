package me.kdevo.ise.ti.while0

import org.antlr.v4.runtime.*

fun main() {
    val compiler = While0Compiler(false)
    compiler.compile(CharStreams.fromString(
        """
            PROG(in X1, X2; out Y);
            var(Z);
            X2 = X1 + 1;
            while X1 != X2 do begin
                X1 = 0;
                while X2 != X1 do begin
                    X2 = 0
                end
            end
        """.trimIndent()))
    compiler.printResult(false)
    userInput()
}

fun userInput() {
    println("✎ Write your While0 program here and terminate via CTRL+D:")
    val compiler = While0Compiler(true)
    try {
        compiler.compile(CharStreams.fromStream(System.`in`))
        println("✓ While0 could be compiled successfully:")
        compiler.printResult(true)
    } catch (e: Exception) {
        error(e)
    }
}