package ru.yandex.market.github.pr.stats.controller;

import lombok.AllArgsConstructor;
import org.eclipse.egit.github.core.PullRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.market.github.pr.stats.dto.UserStat;
import ru.yandex.market.github.pr.stats.service.GithubService;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fbokovikov
 */
@RestController
@AllArgsConstructor
public class PullRequestsStatController {

    private final GithubService githubService;

    @GetMapping(value = "/github/pr/stats")
    public List<UserStat> getPrsToRepository(
            @RequestParam(name = "owner") String repositoryOwner,
            @RequestParam(name = "repository") String repositoryName,
            @RequestParam(name = "state", defaultValue = "open") String state
    ) {
        Collection<PullRequest> pullRequests = githubService.getPullRequests(repositoryOwner, repositoryName, state);
        List<String> pullRequestsLogins = pullRequests.stream()
                .map(pr -> pr.getUser().getLogin())
                .collect(Collectors.toList());
        return pullRequestsLogins.stream()
                .map(u -> new UserStat(u, Collections.frequency(pullRequestsLogins, u)))
                .distinct()
                .sorted(Comparator.comparing(UserStat::getPrNumber))
                .collect(Collectors.toList());

    }
}
