package lt.vinted.homework.discount

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import lt.vinted.homework.common.NotFoundException

import static java.nio.file.Files.lines
import static java.nio.file.Paths.get
import static java.util.Objects.requireNonNull
import static java.util.Optional.ofNullable

@CompileStatic
@PackageScope
class Providers {
    private final static String PROVIDER_RESOURCE_NAME = 'providers.csv'
    private final List<Provider> providers = []

    Providers() {
       try {
           def url = this.class
               .getResource("/$PROVIDER_RESOURCE_NAME")
               .toURI()

           lines(get(url)).each { String line ->
               try {
                   providers << constructProvider(line)
               } catch (Exception ignored) {
                   println("Could not parse provider from: $line")
               }
           }
       } catch (Exception ignored) {
           println("Could not load: '$PROVIDER_RESOURCE_NAME'")
       }
    }

    private static Provider constructProvider(String line) {
        def split = line.split(',')
        return new Provider(
            split[0],
            new BigDecimal(split[1]),
            new BigDecimal(split[2]),
            new BigDecimal(split[3]))
    }

    BigDecimal findDeliveryPrice(String name, String size) {
        def provider = ofNullable(providers.find { it.name == name })
            .orElseThrow { new NotFoundException("Provider with name: $name - not found") }
        return provider.deliveryPrice(size)
    }

    List<Provider> fetchProviders() {
        return providers
    }

    // I didn't feel a particular need to have Enums for sizes since they don't really add benefits to extending code
    // so I've decided to use case sensitive string
    static class Provider {
        private final String name
        private final Map<String, BigDecimal> prices

        // some people find this style weird but argument *list* should read like a *list* when certain argument count
        // and/or character length is exceeded. On the plus side it also should provide some visual queue
        Provider(
            String name,
            BigDecimal sPrice,
            BigDecimal mPrice,
            BigDecimal lPrice
        ) {
            requireNonNull(name, 'name can not be null')
            requireNonNull(sPrice, 'sPrice can not be null')
            requireNonNull(mPrice, 'mPrice name can not be null')
            requireNonNull(lPrice, 'lPrice name can not be null')
            this.name = name
            prices = [S: sPrice, M: mPrice, L: lPrice]
        }

        BigDecimal deliveryPrice(String size) {
            return prices[size]
        }

        // these are for convenience only to avoid mistyping, some of them are not even used but I felt like covering
        // basics
        BigDecimal deliveryPriceS() {
            return prices.S
        }

        BigDecimal deliveryPriceM() {
            return prices.M
        }

        BigDecimal deliveryPriceL() {
            return prices.L
        }
    }
}
