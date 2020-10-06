package example

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class HelloLiveOrderBoardTest() {

    @Test
    fun `HelloLiveOrderBoard should process orders and print to console`() {
        val outputStream = ByteArrayOutputStream()
        val output = PrintStream(outputStream)

        output.use { output ->
            LiveOrderBoardExample(output)
            val consoleLines = String(outputStream.toByteArray()).split("\n")
            assertThat(consoleLines).containsExactly(
                    "Received Orders:",
                    "SELL: 350.1 Ethereum £13.6 [user1]",
                    "SELL: 50.5 Ethereum £14 [user2]",
                    "SELL: 441.8 Ethereum £13.9 [user3]",
                    "SELL: 3.5 Ethereum £13.6 [user4]",
                    "",
                    "Order Summary:",
                    "353.6 Ethereum for £13.6",
                    "441.8 Ethereum for £13.9",
                    "50.5 Ethereum for £14",
                    ""
            )
        }
    }
}