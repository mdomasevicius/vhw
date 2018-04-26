package lt.vinted.homework.discount

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class DiscountIntegrationSpec extends Specification {

    @Shared
    def discounts = new Discounts()

    @Unroll
    def 'Order(#date, #size, #provider) should cost #discountedPrice to deliver with total discount of #discount'() {
        when:
            def result = discounts.calculateDiscount(new Order(
                date: LocalDate.parse(date),
                size: size,
                providerName: provider))
        then:
            result.discountedPrice == discountedPrice
            result.discount == discount
        where:
            date         | size | provider || discountedPrice | discount
            '2015-02-01' | 'S'  | 'MR'     || 1.50            | 0.50
            '2015-02-02' | 'S'  | 'MR'     || 1.50            | 0.50
            '2015-02-03' | 'L'  | 'LP'     || 6.90            | 0.00
            '2015-02-05' | 'S'  | 'LP'     || 1.50            | 0.00
            '2015-02-06' | 'S'  | 'MR'     || 1.50            | 0.50
            '2015-02-06' | 'L'  | 'LP'     || 6.90            | 0.00
            '2015-02-07' | 'L'  | 'MR'     || 4.00            | 0.00
            '2015-02-08' | 'M'  | 'MR'     || 3.00            | 0.00
            '2015-02-09' | 'L'  | 'LP'     || 0.00            | 6.90
            '2015-02-10' | 'L'  | 'LP'     || 6.90            | 0.00
            '2015-02-10' | 'S'  | 'MR'     || 1.50            | 0.50
            '2015-02-10' | 'S'  | 'MR'     || 1.50            | 0.50
            '2015-02-11' | 'L'  | 'LP'     || 6.90            | 0.00
            '2015-02-12' | 'M'  | 'MR'     || 3.00            | 0.00
            '2015-02-13' | 'M'  | 'LP'     || 4.90            | 0.00
            '2015-02-15' | 'S'  | 'MR'     || 1.50            | 0.50
            '2015-02-17' | 'L'  | 'LP'     || 6.80            | 0.10
            '2015-02-17' | 'S'  | 'MR'     || 1.50            | 0.50
            '2015-02-24' | 'L'  | 'LP'     || 6.90            | 0.00
            '2015-02-24' | 'L'  | 'LP'     || 6.90            | 0.00
            '2015-03-01' | 'S'  | 'MR'     || 1.50            | 0.50
    }

}
