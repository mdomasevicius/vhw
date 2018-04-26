package lt.vinted.homework.discount

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class EveryThirdLPLargeDiscountSpec extends Specification {

    DiscountRule rule
    def providersMock

    void setup() {
        providersMock = Mock(Providers)
        rule = new EveryThirdLPLargeDiscount(providersMock)
    }

    def 'MR never gets discounts'() {
        given:
            providersMock.findDeliveryPrice(_, _) >> 1
        expect:
            applyAndReturnResult(100, newOrder('MR', 'L')) == 0
    }

    def 'every third LP order must be discounted'() {
        given:
            providersMock.findDeliveryPrice(_, _) >> 1
        expect:
            applyAndReturnResult(6, newOrder('LP', 'L')) == 2
    }

    @Unroll
    def 'for #providerName of size #size applied #timesApplied times should discount by #expectedDiscount'() {
        given:
            providersMock.findDeliveryPrice(_, _) >> 1
        expect:
            applyAndReturnResult(timesApplied, newOrder(providerName, size)) == expectedDiscount
        where:
            providerName | size | timesApplied || expectedDiscount
            'LP'         | 'L'  | 6            || 2
            'LP'         | 'S'  | 8            || 0
            'LP'         | 'M'  | 10           || 0
            'MR'         | 'S'  | 10           || 0
            'MR'         | 'L'  | 12           || 0
            'MR'         | 'M'  | 10           || 0
    }

    private static Order newOrder(String providerName, String size) {
        new Order(
            date: LocalDate.now(),
            provider: providerName,
            size: size)
    }

    private BigDecimal applyAndReturnResult(int times, Order order) {
        return (1..times).inject(0) { acc, ignored ->
            acc += rule.apply(order)
            acc
        } as BigDecimal
    }
}
