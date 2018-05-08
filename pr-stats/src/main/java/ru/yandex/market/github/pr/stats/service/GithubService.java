package ru.yandex.market.github.pr.stats.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.egit.github.core.CommitUser;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import ru.yandex.market.github.pr.stats.service.dao.GithubServiceDao;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    private final BranchService branchService;

    @SneakyThrows
    public Collection<PullRequest> getPullRequests(IRepositoryIdProvider repository,
                                                   String state) {
        return pullRequestService.getPullRequests(repository, state);
    }

    public Collection<GithubBranch> getAllBranches() {
        return githubServiceDao.getAllBranches();
    }

    public Collection<GithubBranch> getUserBranches(String login) {
        return githubServiceDao.getUserBranches(login);
    }

    public void deleteBranch(IRepositoryIdProvider repo, GithubBranch githubBranch) {
        branchService.deleteBranch(repo, githubBranch.getBranchName());
        githubServiceDao.deleteBranch(githubBranch);
    }

    @SneakyThrows
    public void importGithubBranches(String repositoryOwner,
                                     String repositoryName) {
        Repository repository = repositoryService.getRepository(repositoryOwner, repositoryName);
        Collection<RepositoryBranch> repositoryBranches = repositoryService.getBranches(repository);
        List<GithubBranch> branches = repositoryBranches.stream()
                .map(b -> mapToGithubBranch(b, repository))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        githubServiceDao.saveBranches(branches);
    }

    @SneakyThrows
    private GithubBranch mapToGithubBranch(RepositoryBranch repositoryBranch, Repository repository) {
        try {
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
            Thread.sleep(200);
            return GithubBranch.builder()
                    .sha(sha)
                    .updatedAt(updatedAt)
                    .branchName(branchName)
                    .branchOwner(login)
                    .repository(repositoryName)
                    .build();
        } catch (RequestException e) {
            return null;
        }
    }

    public Collection<RepositoryCommit> getCommits(IRepositoryIdProvider repository, int n) {
        List<RepositoryCommit> commits = new ArrayList<>();
        PageIterator<RepositoryCommit> commitsIterator = commitService.pageCommits(repository, 100);
        while (commitsIterator.hasNext() && commits.size() < n) {
            commits.addAll(commitsIterator.next());
        }
        return commits;
    }


}
