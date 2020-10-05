package crypto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderSummaryCalculatorTest{

    @Test
    fun `OrderSummaryCalculator should return single BUY order`(){
        // Given
        val orders = listOf(Order(OrderType.BUY,"user1", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()))

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary.buyOrders).containsExactly(OrderInformation("Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()))
        assertThat(orderSummary.sellOrders).isEmpty()
    }

    @Test
    fun `OrderSummaryCalculator should return single SELL order`(){
        // Given
        val orders = listOf(Order(OrderType.SELL,"user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()))

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary.sellOrders).containsExactly(OrderInformation("Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()))
        assertThat(orderSummary.buyOrders).isEmpty()
    }

    @Test
    fun `OrderSummaryCalculator should return single BUY and single SELL order`(){
        // Given
        val orders = listOf(
                Order(OrderType.BUY,"user1", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()),
                Order(OrderType.SELL,"user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary.sellOrders).containsExactly(OrderInformation("Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()))
        assertThat(orderSummary.buyOrders).containsExactly(OrderInformation("Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()))
    }

    @Test
    fun `OrderSummaryCalculator should merge same price SELL orders from different users`(){
        // Given
        val orders = listOf(
                Order(OrderType.SELL,"user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL,"user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary.sellOrders).containsExactly(OrderInformation("Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal()))
        assertThat(orderSummary.buyOrders).isEmpty()
    }

    @Test
    fun `OrderSummaryCalculator should display SELL orders lowest price first`(){
        // Given
        val orders = listOf(
                Order(OrderType.SELL,"user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL,"user4", "Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary.sellOrders).containsExactly(
                OrderInformation("Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal()),
                OrderInformation("Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        )
        assertThat(orderSummary.buyOrders).isEmpty()
    }

    @Test
    fun `OrderSummaryCalculator should display BUY orders highest price first`(){
        // Given
        val orders = listOf(
                Order(OrderType.BUY,"user4", "Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal()),
                Order(OrderType.BUY,"user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary.buyOrders).containsExactly(
                OrderInformation("Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                OrderInformation("Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal())
        )
        assertThat(orderSummary.sellOrders).isEmpty()
    }

    @Test
    fun `OrderSummaryCalculator should merge same price BUY orders from different users`(){
        // Given
        val orders = listOf(
                Order(OrderType.BUY,"user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.BUY,"user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary.buyOrders).containsExactly(OrderInformation("Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal()))
        assertThat(orderSummary.sellOrders).isEmpty()
    }
}