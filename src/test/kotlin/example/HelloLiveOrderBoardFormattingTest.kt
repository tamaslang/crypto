package example

import crypto.Order
import crypto.OrderSummary
import crypto.OrderType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HelloLiveOrderBoardFormattingTest {

    @Test
    fun `SELL order should be formatted to string line`() {
        val anOrder = Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        assertThat(anOrder.toStringLine()).isEqualTo("SELL: 350.1 Ethereum £13.6 [user1]")
    }

    @Test
    fun `BUY order should be formatted to string line`() {
        val anOrder = Order(OrderType.BUY, "user2", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        assertThat(anOrder.toStringLine()).isEqualTo("BUY: 50.5 Litecoin £125 [user2]")
    }

    @Test
    fun `OrderSummary list should be formatted to string lines`() {
        val orderSummaryList = listOf(OrderSummary(OrderType.SELL, "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()))
        assertThat(orderSummaryList.toStringLines()).containsExactly("350.1 Ethereum for £13.6")
    }

    @Test
    fun `Empty OrderSummary list should be formatted to string lines`() {
        val orderSummaryList = emptyList<OrderSummary>()
        assertThat(orderSummaryList.toStringLines()).isEmpty()
    }

    @Test
    fun `OrderSummary list with multiple orders should be formatted to string lines`() {
        val orderSummaryList = listOf(
                OrderSummary(OrderType.SELL, "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                OrderSummary(OrderType.SELL, "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()))
        assertThat(orderSummaryList.toStringLines()).containsExactly("350.1 Ethereum for £13.6", "50.5 Litecoin for £125")
    }

}