package ru.yandex.market.github.pr.stats.service;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.market.github.pr.stats.config.datasource.DatasourceConfig;
import ru.yandex.market.github.pr.stats.config.placeholder.PlaceholderConfig;
import ru.yandex.market.github.pr.stats.service.dao.GithubServiceDao;

/**
 * @author fbokovikov
 */
@Configuration
@Import({PlaceholderConfig.class, DatasourceConfig.class})
public class GithubServiceConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${github.host}")
    private String guthubHost;

    @Value("${github.user}")
    private String githubUser;

    @Value("${github.password}")
    private String githubPassword;

    @Bean
    public GithubServiceDao githubServiceDao() {
        return new GithubServiceDao(jdbcTemplate);
    }

    @Bean
    public GitHubClient gitHubClient() {
        GitHubClient gitHubClient = new GitHubClient(guthubHost);
        gitHubClient.setCredentials(githubUser, githubPassword);
        return gitHubClient;
    }

    @Bean
    public CommitService commitService() {
        return new CommitService(gitHubClient());
    }

    @Bean
    public PullRequestService pullRequestService() {
        return new PullRequestService(gitHubClient());
    }

    @Bean
    public RepositoryService repositoryService() {
        return new RepositoryService(gitHubClient());
    }

    @Bean
    public GithubService githubService() {
        return new GithubService(
                pullRequestService(),
                repositoryService(),
                githubServiceDao(),
                commitService()
        );
    }

}
