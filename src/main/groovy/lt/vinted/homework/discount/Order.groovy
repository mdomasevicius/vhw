package lt.vinted.homework.discount

import groovy.transform.CompileStatic

import java.time.LocalDate

@CompileStatic
class Order {

    LocalDate date
    String size
    String providerName

    @Override
    String toString() {
        // .toString() looks weird but java runtime wants strictly String and not GString returned
        return "$date $size $providerName".toString()
    }
}
