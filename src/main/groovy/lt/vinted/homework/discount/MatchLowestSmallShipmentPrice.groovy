package lt.vinted.homework.discount

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import lt.vinted.homework.discount.Providers.Provider

import static java.math.BigDecimal.*

@CompileStatic
@PackageScope
class MatchLowestSmallShipmentPrice implements DiscountRule {

    // I thought really hard about whether access to Provider should belong here since this single line is preventing
    // `Provider` from better encapsulation but I feel like this give more flexibility to the rule and `Providers`
    // class stays cleaner
    private final static Closure CHEAPEST_S_DELIVERY_PRICE = { Provider a, Provider b -> (a.deliveryPriceS() <=> b.deliveryPriceS()) }
    private final Providers providers

    MatchLowestSmallShipmentPrice(Providers providers) {
        this.providers = providers
    }

    @Override
    BigDecimal apply(Order order) {
        if (order.size != 'S') {
            return ZERO
        }

        def currentPrice = providers.findDeliveryPrice(order.providerName, order.size)
        def cheapestPrice = providers.fetchProviders().min(CHEAPEST_S_DELIVERY_PRICE).deliveryPriceS()
        return currentPrice - cheapestPrice
    }
}
