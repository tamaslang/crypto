package crypto

import java.math.BigDecimal

typealias OrderSummaryCalculator = (List<Order>) -> List<OrderSummary>

object OrderSummaryCalculatorSpecdInTheExercise {
    private const val RETURNED_LIST_SIZE = 10

    private class OrderSummaryComparator {
        companion object : Comparator<OrderSummary> {
            override fun compare(o1: OrderSummary, o2: OrderSummary): Int {
                if (o1.orderType != o2.orderType) {
                    return if (o1.orderType == OrderType.SELL) -1 else 1
                }
                // same order type copmare the price
                return if (o1.orderType == OrderType.SELL) {
                    // ascending for SELL
                    o1.pricePerCoin.compareTo(o2.pricePerCoin)
                } else {
                    // descending for BUY
                    o2.pricePerCoin.compareTo(o1.pricePerCoin)
                }
            }
        }
    }

    fun createOrderSummary(orders: List<Order>): List<OrderSummary> {
        return orders
                .groupBy { OrderSummary(it.orderType, it.coinType, BigDecimal.ZERO, it.pricePerCoin) }
                .mapValues { it.value.map { it.orderQuantity }.reduce { acc, price -> acc.add(price) } }
                .map { (orderSummary, sumQuantity) -> orderSummary.copy(orderQuantity = sumQuantity) }
                .sortedWith(OrderSummaryComparator)
                .take(RETURNED_LIST_SIZE)
    }
}
