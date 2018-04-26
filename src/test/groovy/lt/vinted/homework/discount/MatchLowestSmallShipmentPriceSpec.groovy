package lt.vinted.homework.discount

import spock.lang.Specification

import java.time.LocalDate

import static lt.vinted.homework.discount.Providers.*

class MatchLowestSmallShipmentPriceSpec extends Specification {

    DiscountRule rule
    def providersMock

    def setup() {
        providersMock = Mock(Providers)
        rule = new MatchLowestSmallShipmentPrice(providersMock)
    }

    def 'must match the lowest provider price for S size order'() {
        given:
            providersMock.fetchProviders() >> [
                new Provider('A', 1.0, 2.0, 3.0),
                new Provider('B', 2.0, 4.0, 6.0),
            ]
            providersMock.findDeliveryPrice('A', 'S') >> 1.0
            providersMock.findDeliveryPrice('B', 'S') >> 2.0
        expect:
            rule.apply(newOrder('B', 'S')) == 1
        and:
            rule.apply(newOrder('A', 'S')) == 0
    }

    def 'L and M sizes are ignored'() {
        given:
            providersMock.fetchProviders() >> [
                new Provider('A', 1.0, 2.0, 3.0),
                new Provider('B', 2.0, 4.0, 6.0),
            ]
            providersMock.findDeliveryPrice('A', 'S') >> 1.0
            providersMock.findDeliveryPrice('B', 'S') >> 2.0
        expect:
            rule.apply(newOrder(providerName, size)) == 0
        where:
            providerName | size
            'A'          | 'M'
            'A'          | 'L'
            'B'          | 'M'
            'B'          | 'L'
    }

    private static Order newOrder(String providerName, String size) {
        return new Order(
            date: LocalDate.now(),
            size: size,
            provider: providerName)
    }
}
