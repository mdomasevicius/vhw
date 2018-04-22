package lt.vinted.homework.discount

import groovy.transform.CompileStatic

@CompileStatic
class Discount {

    final BigDecimal discountedPrice
    final BigDecimal discount

    Discount(BigDecimal discountedPrice, BigDecimal discount) {
        this.discountedPrice = discountedPrice
        this.discount = discount
    }
}
