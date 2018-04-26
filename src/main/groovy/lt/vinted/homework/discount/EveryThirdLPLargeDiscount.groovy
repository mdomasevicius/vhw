package lt.vinted.homework.discount

import groovy.transform.CompileStatic
import groovy.transform.PackageScope

@CompileStatic
@PackageScope
class EveryThirdLPLargeDiscount implements DiscountRule {

    private final static BigDecimal NO_DISCOUNT = 0
    private final Providers providers
    private long processCount = 0

    EveryThirdLPLargeDiscount(Providers providers) {
        this.providers = providers
    }

    @Override
    BigDecimal apply(Order order) {
        if (order.size != 'L') {
            return NO_DISCOUNT
        }

        if (order.provider != 'LP') {
            return NO_DISCOUNT
        }

        if (++processCount % 3 != 0) {
            return NO_DISCOUNT
        }

        return providers.findDeliveryPrice(order.provider, order.size)
    }
}
