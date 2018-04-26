package lt.vinted.homework.discount

import groovy.transform.PackageScope

// I really did not want to over-think this
// so I thought as simple as possible - there are rules for Orders
// you apply a rule and get back applicable discount amount
@PackageScope
interface DiscountRule {

    /**
     * Applies discount rule to order
     * @param order
     * @return eligible discount amount for order
     */
    BigDecimal apply(Order order)
}
