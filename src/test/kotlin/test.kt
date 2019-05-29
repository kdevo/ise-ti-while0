import me.kdevo.ise.ti.while0.ErrorListener
import me.kdevo.ise.ti.while0.While0Lexer
import me.kdevo.ise.ti.while0.While0Parser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ConsoleErrorListener
import org.junit.Assert
import org.junit.Test

class TestParser() {
    @Test
    fun shouldNotThrowException() {
        val stream = CharStreams.fromStream(TestParser::class.java.getResourceAsStream("valid.while0"))
        try {
            val lexer = While0Lexer(stream)
            val listener = ErrorListener()
            // lexer.removeErrorListener(ConsoleErrorListener.INSTANCE)
            lexer.addErrorListener(listener)
            val parser = While0Parser(CommonTokenStream(lexer))
            parser.addErrorListener(listener)
            parser.program()
        } catch (e: Exception) {
            Assert.fail()
        }

    }

    @Test
    fun shouldThrowException() {
        val stream = CharStreams.fromStream(TestParser::class.java.getResourceAsStream("invalid.while0"))
        try {
            val lexer = While0Lexer(stream)
            val listener = ErrorListener()
            lexer.removeErrorListener(ConsoleErrorListener.INSTANCE)
            lexer.addErrorListener(listener)
            val parser = While0Parser(CommonTokenStream(lexer))
            parser.addErrorListener(listener)
            parser.program()
        } catch (e: Exception) {
            println("Exception ${e.message} has been thrown! Don't worry, that is a good sign for this test!")
        }
    }
}

