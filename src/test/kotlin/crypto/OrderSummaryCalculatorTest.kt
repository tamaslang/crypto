package crypto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderSummaryCalculatorTest {

    @Test
    fun `OrderSummaryCalculator should return single BUY order`() {
        // Given
        val orders = listOf(Order(OrderType.BUY, "user1", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()))

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(OrderSummary(OrderType.BUY, "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()))
    }

    @Test
    fun `OrderSummaryCalculator should return single SELL order`() {
        // Given
        val orders = listOf(Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()))

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(OrderSummary(OrderType.SELL, "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()))
    }

    @Test
    fun `OrderSummaryCalculator should return single BUY and single SELL order`() {
        // Given
        val orders = listOf(
                Order(OrderType.BUY, "user1", "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal()),
                Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(
                OrderSummary(OrderType.SELL, "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                OrderSummary(OrderType.BUY, "Litecoin", 50.5.toBigDecimal(), 125.toBigDecimal())
        )
    }

    @Test
    fun `OrderSummaryCalculator should merge same price SELL orders from different users`() {
        // Given
        val orders = listOf(
                Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(OrderSummary(OrderType.SELL, "Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal()))
    }

    @Test
    fun `OrderSummaryCalculator should display SELL orders lowest price first`() {
        // Given
        val orders = listOf(
                Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL, "user4", "Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(
                OrderSummary(OrderType.SELL, "Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal()),
                OrderSummary(OrderType.SELL, "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        )
    }

    @Test
    fun `OrderSummaryCalculator should display BUY orders highest price first`() {
        // Given
        val orders = listOf(
                Order(OrderType.BUY, "user4", "Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal()),
                Order(OrderType.BUY, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(
                OrderSummary(OrderType.BUY, "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                OrderSummary(OrderType.BUY, "Litecoin", 3.5.toBigDecimal(), 5.6.toBigDecimal())
        )
    }

    @Test
    fun `OrderSummaryCalculator should merge same price BUY orders from different users`() {
        // Given
        val orders = listOf(
                Order(OrderType.BUY, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.BUY, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(OrderSummary(OrderType.BUY, "Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal()))
    }

    @Test
    fun `OrderSummaryCalculator should merge and sort SELL orders (example)`() {
        // Given
        val orders = listOf(
                Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL, "user2", "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                Order(OrderType.SELL, "user3", "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                Order(OrderType.SELL, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(
                OrderSummary(OrderType.SELL, "Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal()),
                OrderSummary(OrderType.SELL, "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                OrderSummary(OrderType.SELL, "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal())
        )
    }

    @Test
    fun `OrderSummaryCalculator should merge and sort BUY orders`() {
        // Given
        val orders = listOf(
                Order(OrderType.BUY, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.BUY, "user2", "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                Order(OrderType.BUY, "user3", "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                Order(OrderType.BUY, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(
                OrderSummary(OrderType.BUY, "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                OrderSummary(OrderType.BUY, "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                OrderSummary(OrderType.BUY, "Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal())
        )
    }

    @Test
    fun `OrderSummaryCalculator should merge and sort SELL and BUY orders`() {
        // Given
        val orders = listOf(
                Order(OrderType.BUY, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL, "user2", "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                Order(OrderType.BUY, "user2", "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                Order(OrderType.SELL, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.SELL, "user3", "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                Order(OrderType.BUY, "user3", "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                Order(OrderType.SELL, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal()),
                Order(OrderType.BUY, "user4", "Ethereum", 3.5.toBigDecimal(), 13.6.toBigDecimal())
        )

        // When
        val orderSummary = OrderSummaryCalculatorSpecdInTheExercise.createOrderSummary(orders)

        // Then
        assertThat(orderSummary).containsExactly(
                OrderSummary(OrderType.SELL, "Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal()),
                OrderSummary(OrderType.SELL, "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                OrderSummary(OrderType.SELL, "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                OrderSummary(OrderType.BUY, "Ethereum", 50.5.toBigDecimal(), 14.toBigDecimal()),
                OrderSummary(OrderType.BUY, "Ethereum", 441.8.toBigDecimal(), 13.9.toBigDecimal()),
                OrderSummary(OrderType.BUY, "Ethereum", 353.6.toBigDecimal(), 13.6.toBigDecimal())
        )
    }

    @Test
    fun `OrderSummaryCalculator should only return the top 10 SELL or BUY orders`() {
        val orders = listOf(
                Order(OrderType.BUY, "user1", "Ethereum", 350.1.toBigDecimal(), 13.6.toBigDecimal())
        )
    }
}