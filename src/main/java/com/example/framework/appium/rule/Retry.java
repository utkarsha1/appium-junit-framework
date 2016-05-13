package com.example.framework.appium.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapted from http://stackoverflow.com/questions/8295100/how-to-re-run-failed-junit-tests-immediately
 * to deal with UI test/environment flakiness
 */
public class Retry implements TestRule {

    private static final Logger logger = LoggerFactory.getLogger(Retry.class);
    private int retryCount;

    public Retry(int retryCount) {
        this.retryCount = retryCount;
    }

    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;

                // implement retry logic here
                for (int i = 1; i <= retryCount; i++) {
                    try {
                        base.evaluate();
                        return;
                    } catch (Throwable t) {
                        caughtThrowable = t;
                        logger.info(description.getDisplayName() + ": run " + i + " failed");
                    }
                }
                logger.error(description.getDisplayName() + ": giving up after " + retryCount + " failures");
                throw caughtThrowable;
            }
        };
    }
}