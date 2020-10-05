package crypto

import java.math.BigDecimal

enum class OrderType {
    BUY,
    SELL
}

data class Order(val orderType: OrderType, val userId: String, val coinType: String, val orderQuantity: BigDecimal, val pricePerCoin: BigDecimal) {
    init {
        require(orderQuantity.compareTo(BigDecimal.ZERO) > 0) { "Order quantity must be greater than 0" }
        require(pricePerCoin.compareTo(BigDecimal.ZERO) > 0) { "Price per coin must be greater than 0" }
        require(userId.isNotBlank()) { "UserId can not be empty" }
        require(coinType.isNotBlank()) { "Coin type can not be empty" }
    }
}

data class OrderSummary(val orderType: OrderType, val coinType: String, val orderQuantity: BigDecimal, val pricePerCoin: BigDecimal)

