package example

import crypto.LiveOrderBoard
import crypto.Order
import crypto.OrderSummary
import crypto.OrderSummaryCalculatorSpecdInTheExercise
import crypto.OrderType
import java.io.PrintStream

fun main() {
    LiveOrderBoardExample(System::out.get()).run()
}

class LiveOrderBoardExample(val console: PrintStream) {

    fun run() {
        val liveOrderBoard = LiveOrderBoard(InMemoryOrderRepository())
        val orders = listOf(
                Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL, "user2", "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                Order(OrderType.SELL, "user3", "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                Order(OrderType.SELL, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        console.println("Received Orders:")
        orders.forEach { order ->
            console.println(order.toStringLine())
            liveOrderBoard.placeAnOrder(order)
        }

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        console.println("")
        console.println("Order Summary:")
        orderSummary.toStringLines().forEach(console::println)
    }
}

fun Order.toStringLine() = "${this.orderType}: ${this.orderQuantity} ${this.coinType} £${this.pricePerCoin} [${this.userId}]"

fun List<OrderSummary>.toStringLines(): List<String> =
        this.map { orderSummary -> "${orderSummary.orderQuantity} ${orderSummary.coinType} for £${orderSummary.pricePerCoin}" }
