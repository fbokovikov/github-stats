package ru.yandex.market.github.pr.stats.config;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.yandex.market.github.pr.stats.config.placeholder.PlaceholderConfig;
import ru.yandex.market.github.pr.stats.service.GithubServiceConfig;

/**
 * @author fbokovikov
 */
@TestExecutionListeners({
        ExecutionListener.class,
        DependencyInjectionTestExecutionListener.class
})
@SpringJUnitConfig(classes = {GithubServiceConfig.class, PlaceholderConfig.class})
public class FunctionalTest {
}
