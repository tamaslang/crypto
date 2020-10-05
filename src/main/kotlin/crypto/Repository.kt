package crypto

import java.util.*

typealias OrderId = UUID

interface OrderRepository {
    fun saveOrder(order: Order): OrderId

    fun listOrders(): List<Order>

    fun deleteOrder(orderId: OrderId)
}