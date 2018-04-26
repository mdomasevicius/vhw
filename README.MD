[![Build Status](https://travis-ci.org/mdomasevicius/vinted-homework.svg?branch=master)](https://travis-ci.org/mdomasevicius/vinted-homework)

# Discounts

Delivery order discount module.

## Notes

I've decided to use [Groovy](http://groovy-lang.org/) for the <a href="homework.md">given homework</a> to avoid common boilerplate that plagues `Java`
and hopefully make code more readable.

For testing - [Spock](http://spockframework.org/).

Project build tool I've chosen [Gradle](https://gradle.org/) - it's self contained so no additional configuration is required.

This project also uses [CodeNarc](http://codenarc.sourceforge.net/).

## Prerequisites

* JDK 8 (1.8.0_121+) - that's it!

## Instructions

`$ ./gradlew build` - this will build a distributable and run tests.

`$ ./gradlew build itest` - this will build a distributable and run test + integration tests.

After distributable has been assembled it will be copied to root project directory - `discounts.jar`.

Simply run `java -jar discounts.jar <file_path>` (if you omit `<file_path>` the app will try to find `input.txt` in current directory).

Input file entries are assumed to adhere to the format described in <a href="homework.md">homework</a>.
