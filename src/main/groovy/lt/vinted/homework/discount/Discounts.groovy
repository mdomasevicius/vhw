package lt.vinted.homework.discount

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

import java.time.LocalDate

import static java.math.BigDecimal.TEN
import static java.math.RoundingMode.HALF_UP
import static java.util.Objects.requireNonNull

@CompileStatic
class Discounts {

    private final static BigDecimal MONTHLY_DISCOUNT_CAP = TEN

    private final Map<DateKey, BigDecimal> monthDiscountLimits = [:]
    private final List<DiscountRule> rules
    private final Providers providers

    Discounts() {
        this.providers = new Providers()
        this.rules = [
            new EveryThirdLPLargeDiscount(providers) as DiscountRule,
            new MatchLowestSmallShipmentPrice(providers) as DiscountRule
        ]
    }

    Discount calculateDiscount(Order order) {
        requireNonNull(order, 'order can not be null')
        requireNonNull(order.date, 'order.date can not be null')
        requireNonNull(order.providerName, 'order.providerName can not be null')
        requireNonNull(order.size, 'order.size can not be null')

        final accumulatedDiscount = rules.collect { it.apply(order) }.sum() as BigDecimal
        final deliveryPrice = providers.findDeliveryPrice(order.providerName, order.size)
        final matchedDiscount = matchDeliveryPriceWhenDiscountExceeded(deliveryPrice, accumulatedDiscount)

        final availableDiscount = calculateAvailableDiscount(order.date, matchedDiscount)

        return new Discount(
            (deliveryPrice - availableDiscount).setScale(2, HALF_UP),
            availableDiscount.setScale(2, HALF_UP))
    }

    private BigDecimal calculateAvailableDiscount(LocalDate orderDate, final BigDecimal calculatedDiscount) {
        def yearMonth = new DateKey(orderDate)
        if (!monthDiscountLimits[yearMonth]) {
            monthDiscountLimits[yearMonth] = MONTHLY_DISCOUNT_CAP
        }

        if (monthDiscountLimits[yearMonth] < calculatedDiscount) {
            def availableDiscount = monthDiscountLimits[yearMonth]
            monthDiscountLimits[yearMonth] = BigDecimal.ZERO
            return availableDiscount
        } else {
            monthDiscountLimits[yearMonth] = monthDiscountLimits[yearMonth] - calculatedDiscount
            return calculatedDiscount
        }
    }

    private static BigDecimal matchDeliveryPriceWhenDiscountExceeded(BigDecimal deliveryPrice, BigDecimal totalDiscount) {
        return totalDiscount > deliveryPrice ? deliveryPrice : totalDiscount
    }

    @EqualsAndHashCode
    private static class DateKey {
        final int year
        final int monthValue

        DateKey(LocalDate date) {
            this.year = date.year
            this.monthValue = date.monthValue
        }
    }
}
