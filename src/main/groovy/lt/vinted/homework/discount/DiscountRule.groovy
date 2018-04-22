package lt.vinted.homework.discount

import groovy.transform.PackageScope

@PackageScope
interface DiscountRule {

    /**
     * Applies discount rule to order
     * @param order
     * @return eligible discount amount for order
     */
    BigDecimal apply(Order order)
}
