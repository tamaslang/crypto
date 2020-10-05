package crypto

class LiveOrderBoard(private val orderRepository: OrderRepository, private val orderSummaryCalculator: OrderSummaryCalculator = OrderSummaryCalculatorSpecdInTheExercise::createOrderSummary) {

    fun placeAnOrder(order: Order): OrderId =
            orderRepository.saveOrder(order)

    fun cancelAnOrder(orderId: OrderId) {
        orderRepository.deleteOrder(orderId)
    }

    fun orderSummary(): OrderSummary {
        val orders = orderRepository.listOrders()
        return orderSummaryCalculator(orders)
    }
}