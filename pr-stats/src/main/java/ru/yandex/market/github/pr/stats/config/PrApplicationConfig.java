package ru.yandex.market.github.pr.stats.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.yandex.market.github.pr.stats.config.placeholder.PlaceholderConfig;
import ru.yandex.market.github.pr.stats.config.server.JettyServerConfig;
import ru.yandex.market.github.pr.stats.service.GithubServiceConfig;

/**
 * @author fbokovikov
 */
@Configuration
@Import({JettyServerConfig.class,
        GithubServiceConfig.class})
public class PrApplicationConfig {
}
