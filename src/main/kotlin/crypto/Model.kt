package crypto

import java.math.BigDecimal

enum class OrderType {
    BUY,
    SELL
}

data class Order(val orderType: OrderType, val userId: String, val coinType: String, val orderQuantity: BigDecimal, val pricePerCoin: BigDecimal)

data class OrderInformation(val coinType: String, val orderQuantity: BigDecimal, val pricePerCoin: BigDecimal)

fun Order.toOrderInformation(): OrderInformation = OrderInformation(this.coinType, this.orderQuantity, this.pricePerCoin)

data class OrderSummary(val sellOrders: List<OrderInformation>, val buyOrders: List<OrderInformation>)
