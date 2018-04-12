package ru.yandex.market.github.pr.stats.config.placeholder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author fbokovikov
 */
@Configuration
@PropertySource(value = {"application.properties", "file:/etc/secret.properties"})
public class PlaceholderConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
