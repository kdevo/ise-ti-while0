package me.kdevo.ise.ti.while0

import org.antlr.v4.runtime.*

fun main() {
    println("✎ Write your While0 program here and terminate via CTRL+D:")
    val stream = CharStreams.fromStream(System.`in`)
    try {
        val lexer = While0Lexer(stream)
        val listener = ErrorListener()
        // lexer.removeErrorListener(ConsoleErrorListener.INSTANCE)
        lexer.addErrorListener(listener)
        val parser = While0Parser(CommonTokenStream(lexer))
        parser.addErrorListener(listener)
        parser.program()
        println("“✓ While0 could be parsed successfully.")
    } catch (e: Exception) {
        error(e)
    }
}