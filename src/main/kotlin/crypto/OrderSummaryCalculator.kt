package crypto

import java.math.BigDecimal

typealias OrderSummaryCalculator = (List<Order>) -> OrderSummary

object OrderSummaryCalculatorSpecdInTheExercise {
    fun createOrderSummary(orders: List<Order>) : OrderSummary {
        val(sellOrders, buyOrders) = orders.partition { order -> order.orderType == OrderType.SELL }
        val buyOrderInformations = buyOrders
                .groupBy { Pair(it.coinType, it.pricePerCoin) }
                .mapValues { it.value.map { it.orderQuantity }.reduce{acc, price -> acc.add(price)}}
                .map { OrderInformation(it.key.first,it.value, it.key.second)}
                .sortedByDescending { it.pricePerCoin }
        val sellOrderInformations = sellOrders
                .groupBy { Pair(it.coinType, it.pricePerCoin) }
                .mapValues { it.value.map { it.orderQuantity }.reduce{acc, price -> acc.add(price)}}
                .map { OrderInformation(it.key.first,it.value, it.key.second)}
                .sortedBy { it.pricePerCoin }
        return OrderSummary(sellOrders = sellOrderInformations, buyOrders =  buyOrderInformations)

    }
}
