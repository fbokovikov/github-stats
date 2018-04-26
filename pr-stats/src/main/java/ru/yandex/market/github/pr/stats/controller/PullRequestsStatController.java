package ru.yandex.market.github.pr.stats.controller;

import lombok.AllArgsConstructor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.market.github.pr.stats.dto.BranchDTO;
import ru.yandex.market.github.pr.stats.dto.StatDTO;
import ru.yandex.market.github.pr.stats.service.GithubService;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;
import ru.yandex.market.github.pr.stats.util.StreamUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author fbokovikov
 */
@RestController
@AllArgsConstructor
public class PullRequestsStatController {

    private final GithubService githubService;

    @GetMapping(value = "/pullRequests")
    public List<StatDTO> getPrsToRepository(
            @ModelAttribute("repository") IRepositoryIdProvider repository,
            @RequestParam(name = "state", defaultValue = "open") String state
    ) {
        Collection<PullRequest> pullRequests = githubService.getPullRequests(repository, state);
        List<String> pullRequestsOwners = StreamUtil.mapToString(pullRequests, p -> p.getUser().getLogin());
        return aggregateStat(pullRequestsOwners);
    }

    @GetMapping("branches")
    public List<StatDTO> getBranchesStat(
            @ModelAttribute("repository") IRepositoryIdProvider repository
    ) {
        Collection<GithubBranch> allBranches = githubService.getAllBranches();
        List<String> branchOwners = StreamUtil.mapToString(allBranches, GithubBranch::getBranchOwner);
        return aggregateStat(branchOwners);
    }

    @GetMapping("branches/{login}")
    public List<BranchDTO> getUserBranches(
            @ModelAttribute("repository") IRepositoryIdProvider repository,
            @PathVariable(name = "login") String login
    ) {
        return githubService.getUserBranches(login).stream()
                .filter(b -> Objects.equals(b.getBranchOwner(), login))
                .map(BranchDTO::new)
                .sorted(Comparator.comparing(BranchDTO::getUpdatedAt))
                .collect(Collectors.toList());
    }

    @GetMapping("pulse")
    public List<StatDTO> pulse(
            @ModelAttribute("repository") IRepositoryIdProvider repository,
            @RequestParam(value = "n", defaultValue = "400") int n
    ) {
        Collection<RepositoryCommit> commits = githubService.getCommits(repository, n);
        List<String> logins = StreamUtil.mapToString(commits, c -> c.getAuthor().getLogin());
        return aggregateStat(logins);
    }

    private static List<StatDTO> aggregateStat(Collection<String> logins) {
        return logins.stream()
                .map(b -> new StatDTO(b, Collections.frequency(logins, b)))
                .distinct()
                .sorted(Comparator.comparing(StatDTO::getCount).reversed())
                .collect(Collectors.toList());
    }

    @ModelAttribute(name = "repository")
    IRepositoryIdProvider repository(
            @RequestParam(name = "repositoryName", defaultValue = "market-java") String repositoryName,
            @RequestParam(name = "repositoryOwner", defaultValue = "mbi") String repositoryOwner
    ) {
        return () -> repositoryName + "/" + repositoryOwner;
    }

}
