package ru.yandex.market.github.pr.stats.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.market.github.pr.stats.service.dao.GithubServiceDao;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fbokovikov
 */
@AllArgsConstructor
public class GithubService {

    private final PullRequestService pullRequestService;
    private final RepositoryService repositoryService;
    private final GithubServiceDao githubServiceDao;

    @SneakyThrows
    public Collection<PullRequest> getPullRequests(String repositoryOwner,
                                                   String repositoryName,
                                                   String state) {
        Repository repository = repositoryService.getRepository(repositoryOwner, repositoryName);
        return pullRequestService.getPullRequests(repository, state);
    }

    @SneakyThrows
    public Collection<RepositoryBranch> getRepositoryBranches(String repositoryOwner,
                                                              String repositoryName) {
        Repository repository = repositoryService.getRepository(repositoryOwner, repositoryName);
        return repositoryService.getBranches(repository);
    }

    public void importGithubBranches(String repositoryOwner,
                                     String repositoryName) {
        Collection<RepositoryBranch> repositoryBranches = getRepositoryBranches(repositoryOwner, repositoryName);
        List<GithubBranch> branches = repositoryBranches.stream()
                .map(b -> GithubBranch.builder()
                        .repository(repositoryName)
                        .branchName(b.getName())
                        .sha(b.getCommit().getSha())
                        .build()
                ).collect(Collectors.toList());
        System.out.println(branches.size());
        githubServiceDao.saveBranches(branches);
    }

}
