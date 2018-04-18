package ru.yandex.market.github.pr.stats.config.datasource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author fbokovikov
 */
@Configuration
public class DatasourceConfig {

    @Bean
    public DataSource dataSource(
            @Value("${jdbc.url}") String jdbcUrl,
            @Value("${jdbc.user}") String jdbcUser,
            @Value("${jdbc.password}") String jdbcPassword
    ) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        dataSource.setUrl(jdbcUrl);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
