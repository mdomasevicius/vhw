package lt.vinted.homework.discount

import spock.lang.Specification

import java.time.LocalDate

class DiscountIntegrationSpec extends Specification {

    def 'monthly discounts should not exceed total of 10.00 monetary units'() {
        given:
            def discounts = new Discounts()
        expect:
            (1..100).inject(0) { acc, ignored ->
                acc += discounts.calculateDiscount(newOrder('2015-04-04', 'LP', 'L')).discount
                acc
            } <= 10.00
    }

    private static Order newOrder(String date, String provider, String size) {
        new Order(
            date: LocalDate.parse(date),
            provider: provider,
            size: size)
    }

}
