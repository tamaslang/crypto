package example

import crypto.Order
import crypto.OrderId
import crypto.OrderRepository
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
