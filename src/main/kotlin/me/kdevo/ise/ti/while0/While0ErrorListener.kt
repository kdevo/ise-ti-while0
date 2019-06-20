package me.kdevo.ise.ti.while0

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import java.io.IOException

class While0ErrorLIstener : BaseErrorListener() {
    var parsingError = false

    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        parsingError = true
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e)
        throw IOException("Syntax error. Line $line:$charPositionInLine. $msg.")
    }
}