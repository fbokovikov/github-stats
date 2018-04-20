package ru.yandex.market.github.pr.stats.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.egit.github.core.CommitUser;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import ru.yandex.market.github.pr.stats.service.dao.GithubServiceDao;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fbokovikov
 */
@AllArgsConstructor
public class GithubService {

    private final PullRequestService pullRequestService;
    private final RepositoryService repositoryService;
    private final GithubServiceDao githubServiceDao;
    private final CommitService commitService;

    @SneakyThrows
    public Collection<PullRequest> getPullRequests(String repositoryOwner,
                                                   String repositoryName,
                                                   String state) {
        Repository repository = repositoryService.getRepository(repositoryOwner, repositoryName);
        return pullRequestService.getPullRequests(repository, state);
    }

    public Collection<GithubBranch> getAllBranches() {
        return githubServiceDao.getAllBranches();
    }

    @SneakyThrows
    public void importGithubBranches(String repositoryOwner,
                                     String repositoryName) {
        Repository repository = repositoryService.getRepository(repositoryOwner, repositoryName);
        Collection<RepositoryBranch> repositoryBranches = repositoryService.getBranches(repository);
        List<GithubBranch> branches = repositoryBranches.stream()
                .map(b -> mapToGithubBranch(b, repository))
                .collect(Collectors.toList());
        githubServiceDao.saveBranches(branches);
    }

    @SneakyThrows
    private GithubBranch mapToGithubBranch(RepositoryBranch repositoryBranch, Repository repository) {
        String sha = repositoryBranch.getCommit().getSha();
        String repositoryName = repository.getName();
        String branchName = repositoryBranch.getName();
        RepositoryCommit commit = commitService.getCommit(repository, sha);
        @Nullable User user = commit.getAuthor();
        CommitUser commitUser = commit.getCommit().getAuthor();
        String login = Optional.ofNullable(user)
                .map(User::getLogin)
                .orElse(commitUser.getName());
        Instant updatedAt = commitUser.getDate().toInstant();
        Thread.sleep(500);
        return GithubBranch.builder()
                .sha(sha)
                .updatedAt(updatedAt)
                .branchName(branchName)
                .branchOwner(login)
                .repository(repositoryName)
                .build();
    }

}
