package crypto

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderValidationTest {
    private val aValidOrder = Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())

    @Test
    fun `Order quantity can not be negative`() {
        assertThatThrownBy {
            aValidOrder.copy(orderQuantity = -0.1.toBigDecimal())
        }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Order quantity must be greater than 0")
    }

    @Test
    fun `Order quantity must be greater than 0`() {
        assertThatThrownBy {
            aValidOrder.copy(orderQuantity = BigDecimal.ZERO)
        }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Order quantity must be greater than 0")
    }

    @Test
    fun `Price per coin can not be negative`() {
        assertThatThrownBy {
            aValidOrder.copy(pricePerCoin = -0.1.toBigDecimal())
        }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Price per coin must be greater than 0")
    }

    @Test
    fun `Price per coin must be greater than 0`() {
        assertThatThrownBy {
            aValidOrder.copy(pricePerCoin = BigDecimal.ZERO)
        }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Price per coin must be greater than 0")
    }

    @Test
    fun `UserId can not be empty`() {
        assertThatThrownBy {
            aValidOrder.copy(userId = " ")
        }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("UserId can not be empty")
    }

    @Test
    fun `Coin type can not be empty`() {
        assertThatThrownBy {
            aValidOrder.copy(coinType = " ")
        }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Coin type can not be empty")
    }
}