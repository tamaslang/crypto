package example

import crypto.Order
import crypto.OrderRepository
import crypto.OrderType
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class InMemoryOrderRepositoryTest {

    private val createRepo: () -> OrderRepository = { InMemoryOrderRepository() }

    @Test
    fun `Repository should save an order and generate an id`(){
        // Given
        val repository = createRepo()
        val anOrder = Order(OrderType.BUY, "user2", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        // When
        repository.saveOrder(anOrder)
        // Then
        val orders = repository.listOrders()
        assertThat(orders).containsExactly(anOrder)
    }

    @Test
    fun `Repository should save an order and generate unique id`(){
        // Given
        val repository = createRepo()
        val order1 = Order(OrderType.BUY, "user1", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        val order2 = Order(OrderType.BUY, "user2", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        // When
        val orderId1 = repository.saveOrder(order1)
        val orderId2 = repository.saveOrder(order2)
        // Then
        val orders = repository.listOrders()
        assertThat(orders).containsExactly(order1,order2)
        assertThat(orderId1).isNotEqualTo(orderId2)
    }

    @Test
    fun `Repository should cancel an order`(){
        // Given
        val repository = createRepo()
        val anOrder = Order(OrderType.BUY, "user2", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        val orderId = repository.saveOrder(anOrder)
        // When
        repository.deleteOrder(orderId)
        // Then
        val orders = repository.listOrders()
        assertThat(orders).isEmpty()
    }

    @Test
    fun `Repository should list orders`(){
        // Given
        val repository = createRepo()
        val anOrder = Order(OrderType.BUY, "user2", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        repository.saveOrder(anOrder)
        // When
        repository.listOrders()
        // Then
        val orders = repository.listOrders()
        assertThat(orders).containsExactly(anOrder)
    }

}