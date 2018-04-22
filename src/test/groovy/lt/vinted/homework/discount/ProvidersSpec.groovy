package lt.vinted.homework.discount

import lt.vinted.homework.common.NotFoundException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static lt.vinted.homework.discount.Providers.*

class ProvidersSpec extends Specification {

    @Shared
    def providers = new Providers()

    def 'exception is thrown in case provider is missing'() {
        when:
            providers.findDeliveryPrice('a', 'b')
        then:
            thrown NotFoundException
    }

    @Unroll
    def 'provider #name delivers #size orders for #price'() {
        expect:
            providers.findDeliveryPrice(name, size) == price
        where:
            name | size || price
            'LP' | 'S'  || 1.50
            'LP' | 'M'  || 4.90
            'LP' | 'L'  || 6.90
            'MR' | 'S'  || 2
            'MR' | 'M'  || 3
            'MR' | 'L'  || 4
    }

    def 'utility methods must return correct prices'() {
        expect:
            utilityMethod(providers.fetchProviders()
                .find { it.name == name }) == price
        where:
            name | utilityMethod                        || price
            'LP' | { Provider p -> p.deliveryPriceS() } || 1.50
            'LP' | { Provider p -> p.deliveryPriceM() } || 4.90
            'LP' | { Provider p -> p.deliveryPriceL() } || 6.90
            'MR' | { Provider p -> p.deliveryPriceS() } || 2
            'MR' | { Provider p -> p.deliveryPriceM() } || 3
            'MR' | { Provider p -> p.deliveryPriceL() } || 4
    }

    def 'provider fields should all contain data'() {
        when:
            def list = providers.fetchProviders()
        then:
            list.findAll {
                it.name
                it.prices.S
                it.prices.M
                it.prices.L
            }
    }
}
