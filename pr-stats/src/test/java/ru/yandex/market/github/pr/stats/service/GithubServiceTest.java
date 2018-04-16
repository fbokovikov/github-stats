package ru.yandex.market.github.pr.stats.service;

import org.eclipse.egit.github.core.PullRequest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.market.github.pr.stats.config.FunctionalTest;

import java.util.Collection;

/**
 * @author fbokovikov
 */
public class GithubServiceTest extends FunctionalTest {

    @Autowired
    private GithubService githubService;

    @Test
    public void pullRequests() {
        Collection<PullRequest> pullRequests = githubService.getPullRequests(
                "market-java",
                "mbi",
                "open"
        );
        MatcherAssert.assertThat(pullRequests, Matchers.not(Matchers.empty()));
    }
}
