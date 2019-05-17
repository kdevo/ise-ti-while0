package me.kdevo.ise.ti.while0

import org.antlr.v4.runtime.*

fun main() {
    val stream = CharStreams.fromString(readLine())
    try {
        val lexer = While0Lexer(stream)
//        lexer.removeErrorListener(ConsoleErrorListener.INSTANCE)
        lexer.addErrorListener(ErrorListener())
        val parser = While0Parser(CommonTokenStream(lexer))
        val context = parser.program()
        parser.program()
    } catch (e: Exception) {
        error(e)
    }
}