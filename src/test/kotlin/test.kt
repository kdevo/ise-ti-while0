import me.kdevo.ise.ti.while0.While0Compiler
import org.antlr.v4.runtime.CharStreams
import org.junit.Assert
import org.junit.Test

class TestParser() {
    @Test
    fun shouldNotThrowException() {
        try {
            val compiler = While0Compiler()
            compiler.parse(CharStreams.fromStream(TestCompiler::class.java.getResourceAsStream("valid.while0")))
            compiler.printResult()
        } catch (e: Exception) {
            Assert.fail()
        }

    }

    @Test
    fun shouldThrowException() {
        try {
            val compiler = While0Compiler()
            compiler.compile(CharStreams.fromStream(TestCompiler::class.java.getResourceAsStream("invalid.while0")))
        } catch (e: Exception) {
            println("Exception ${e.message} has been thrown! Don't worry, that is a good sign for this test!")
        }
    }
}

class TestCompiler() {
    @Test
    fun shouldNotThrowException() {
        val compiler = While0Compiler()
        compiler.compile(CharStreams.fromStream(TestCompiler::class.java.getResourceAsStream("valid.while0")))
        compiler.printResult()
    }
}
