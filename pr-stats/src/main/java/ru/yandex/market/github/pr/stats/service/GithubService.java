package ru.yandex.market.github.pr.stats.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.Collection;

/**
 * @author fbokovikov
 */
@AllArgsConstructor
public class GithubService {

    private final PullRequestService pullRequestService;
    private final RepositoryService repositoryService;

    @SneakyThrows
    public Collection<PullRequest> getPullRequests(String repositoryOwner,
                                                   String repositoryName,
                                                   String state) {
        Repository repository = repositoryService.getRepository(repositoryOwner, repositoryName);
        return pullRequestService.getPullRequests(repository, state);
    }
}
