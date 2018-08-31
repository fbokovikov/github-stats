package ru.yandex.market.github.pr.stats.service;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.CommitService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.market.github.pr.stats.config.FunctionalTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fbokovikov
 */
public class CommitServiceTest extends FunctionalTest {

    private static final IRepositoryIdProvider MBI_REPO = () -> "market-java/mbi";

    @Autowired
    private CommitService commitService;

    @Test
    public void getMasterCommits() {
        List<RepositoryCommit> commits = new ArrayList<>();
        PageIterator<RepositoryCommit> commitsIterator = commitService.pageCommits(MBI_REPO, 100);
        while (commitsIterator.hasNext() && commits.size() < 500) {
            commits.addAll(commitsIterator.next());
        }
        MatcherAssert.assertThat(
                commits,
                Matchers.hasSize(500)
        );
    }
}
