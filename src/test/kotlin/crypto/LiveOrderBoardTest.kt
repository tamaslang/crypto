package crypto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class InMemoryOrderRepository : OrderRepository {
    val orderStore: MutableMap<OrderId, Order> = mutableMapOf()

    override fun saveOrder(order: Order): OrderId {
        val orderId = UUID.randomUUID()
        orderStore.put(orderId, order)
        return orderId
    }

    override fun listOrders(): List<Order> = orderStore.values.toList()

    override fun deleteOrder(orderId: OrderId) {
        orderStore.remove(orderId)
    }
}

class LiveOrderBoardTest {
    @Test
    fun `LiveOrder board should register an order`() {
        // Given
        val repository = InMemoryOrderRepository()
        val liveOrderBoard = LiveOrderBoard(repository)

        // When
        val anOrder = Order(OrderType.BUY, "userId", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        liveOrderBoard.placeAnOrder(anOrder)

        // Then
        assertThat(repository.listOrders()).containsExactly(anOrder)
    }

    @Test
    fun `LiveOrder board should cancel an order`() {
        // Given
        val repository = InMemoryOrderRepository()
        val anOrder = Order(OrderType.BUY, "userId", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        val orderId = repository.saveOrder(anOrder)

        val liveOrderBoard = LiveOrderBoard(repository)

        // When
        liveOrderBoard.cancelAnOrder(orderId)

        // Then
        assertThat(repository.listOrders()).isEmpty()
    }

    @Test
    fun `LiverOrder board should provide summary`() {
        // Given
        val repository = InMemoryOrderRepository()
        val orderSummary = listOf(OrderSummary(OrderType.SELL, "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()))
        val orderSummaryAlgebra: OrderSummaryCalculator = { _ -> orderSummary }
        val liveOrderBoard = LiveOrderBoard(repository, orderSummaryAlgebra)

        // When
        val returnedOrderSummary = liveOrderBoard.orderSummary()

        // Then
        assertThat(returnedOrderSummary).isEqualTo(orderSummary)
    }

}