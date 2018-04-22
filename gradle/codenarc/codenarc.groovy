ruleset {

    description '''
        A Sample Groovy RuleSet containing all CodeNarc Rules, grouped by category.
        '''

    ruleset('rulesets/basic.xml')

    ruleset('rulesets/braces.xml')

    ruleset('rulesets/concurrency.xml')

    ruleset('rulesets/convention.xml') {
        NoDef(enabled: false)   // def is appreciated in local scope
    }

    ruleset('rulesets/design.xml') {
        AbstractClassWithoutAbstractMethod(enabled: false)      // it makes sense to have a base class without abstract methods
    }

    ruleset('rulesets/dry.xml') {
        DuplicateNumberLiteral(enabled: false)
        DuplicateStringLiteral(enabled: false)
    }

    ruleset('rulesets/exceptions.xml') {
        ThrowRuntimeException(enabled: false)
        CatchException(enabled: false)
    }

    ruleset('rulesets/formatting.xml') {
        ClassJavadoc(enabled: false)
        LineLength(enabled: false)
        SpaceAroundMapEntryColon(enabled: false)
    }

    ruleset('rulesets/generic.xml')

    ruleset('rulesets/groovyism.xml')

    ruleset('rulesets/imports.xml') {
        MisorderedStaticImports(comesBefore: false)
        NoWildcardImports(enabled: false)
    }

    ruleset('rulesets/jdbc.xml')

    ruleset('rulesets/junit.xml') {
        JUnitTestMethodWithoutAssert(enabled: false)
    }

    ruleset('rulesets/logging.xml') {
        Println(enabled: false)
    }

    ruleset('rulesets/naming.xml') {
        FactoryMethodName(enabled: false)

        MethodName {
            doNotApplyToClassNames = '*Spec'
        }
    }

    ruleset('rulesets/security.xml')

    ruleset('rulesets/serialization.xml')

    ruleset('rulesets/size.xml') {
        AbcMetric(enabled: false)               // Requires GMetrics.jar
        CrapMetric(enabled: false)              // Requires GMetrics.jar
        CyclomaticComplexity(enabled: false)    // Requires GMetrics.jar
    }

    ruleset('rulesets/unnecessary.xml') {
        UnnecessaryReturnKeyword(enabled: false)    // sometimes it adds readability
        UnnecessaryBooleanExpression {
            doNotApplyToClassNames = '*Spec'        // 'where:' data tables can use double-pipe
        }
    }

    ruleset('rulesets/unused.xml')
}
