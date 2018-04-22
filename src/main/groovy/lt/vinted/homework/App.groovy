package lt.vinted.homework

import groovy.transform.CompileStatic
import lt.vinted.homework.discount.Discounts
import lt.vinted.homework.discount.Order

import java.nio.file.InvalidPathException
import java.nio.file.NoSuchFileException
import java.time.LocalDate

import static java.nio.file.Files.lines
import static java.nio.file.Paths.get

// Parsing and deserialization is done here
// I did not plan to spend much effort on it since it's not the focus of the task
// besides in real world these are usually handled mostly by libraries
@CompileStatic
class App {

    static void main(String[] args) {
        try {
            processInput()
        } catch (NoSuchFileException ignored) {
            println('File not found')
        } catch (InvalidPathException ignored) {
            println('Invalid file path')
        } catch (Exception e) {
            println(e)
        }
    }

    private static void processInput() {
        // in my experience this should be a part of DI framework
        def discounts = new Discounts()

        // I will pretend that a file input is like a queue, and each line is a raw message
        lines(get('input.txt')).each { String originalMessage ->
            try {
                def order = deserialize(originalMessage)
                def discount = discounts.calculateDiscount(order)
                println("$order $discount.discountedPrice ${discount.discount ? discount.discount : '-'}")
            } catch (Exception ignored) {
                // This is not completely fair, ideally I'd store mangled or poisoned messages for later revision
                // However in scope of this homework this works although not very clear why it's ignored
                // The homework states that this should be applied to unrecognizable delivery size and I think that
                // implicitly this get's the job done :)
                println("$originalMessage Ignored")
            }
        }
    }

    private static Order deserialize(String message) {
        // I will assume that a standard format is that of 3 rows in following order:
        // 0 - ISO date
        // 1 - Package size
        // 2 - Provider code (name in this home work)
        // if my assumptions fail the order is `Ignored`
        def split = message.split(' ')

        // I chose to only parse date into an object since I need more convenient access to it
        return new Order(
            date: LocalDate.parse(split[0]),
            size: split[1],
            providerName: split[2]
        )
    }

}
