package ru.yandex.market.github.pr.stats.config;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * @author fbokovikov
 */
public class ExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) {
        System.out.println("Listener was called!");
    }
}
