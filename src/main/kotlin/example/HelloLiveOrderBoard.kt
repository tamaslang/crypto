package example

import crypto.LiveOrderBoard
import crypto.Order
import crypto.OrderSummary
import crypto.OrderSummaryCalculatorSpecdInTheExercise
import crypto.OrderType


fun main() {

    val liveOrderBoard = LiveOrderBoard(InMemoryOrderRepository())
    val orders = listOf(
            Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
            Order(OrderType.SELL, "user2", "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
            Order(OrderType.SELL, "user3", "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
            Order(OrderType.SELL, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
    )

    println("Received Orders:")
    orders.forEach{order ->
        println(order.toStringLine())
        liveOrderBoard.placeAnOrder(order)
    }

    // When
    val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

    println()
    println("Order Summary:")
    println(orderSummary.toStringLines())
}

fun Order.toStringLine() = "${this.orderType}: ${this.orderQuantity} ${this.coinType} £${this.pricePerCoin} [${this.userId}]"
fun List<OrderSummary>.toStringLines() =
        this.map { orderSummary -> "${orderSummary.orderQuantity} ${orderSummary.coinType} for £${orderSummary.pricePerCoin}" }
                .joinToString("\n\r")
