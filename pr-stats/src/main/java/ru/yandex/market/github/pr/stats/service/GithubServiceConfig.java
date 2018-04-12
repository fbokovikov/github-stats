package ru.yandex.market.github.pr.stats.service;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author fbokovikov
 */
public class GithubServiceConfig {

    @Value("${github.host}")
    private String guthubHost;

    @Value("${github.user}")
    private String githubUser;

    @Value("${github.password}")
    private String githubPassword;

    @Bean
    public GitHubClient gitHubClient() {
        GitHubClient gitHubClient = new GitHubClient(guthubHost);
        gitHubClient.setCredentials(githubUser, githubPassword);
        return gitHubClient;
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
        return new GithubService(pullRequestService(), repositoryService());
    }

}
