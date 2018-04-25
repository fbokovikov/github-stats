package ru.yandex.market.github.pr.stats.main;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.yandex.market.github.pr.stats.service.GithubService;
import ru.yandex.market.github.pr.stats.service.GithubServiceConfig;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import java.util.Collection;

/**
 * @author fbokovikov
 */
public class DeleteUserBranchesJob {

    private static final IRepositoryIdProvider REPO = () -> "market-java/mbi";

    public static void main(String ... args) {
        if (args.length != 1) {
            System.err.println("Expected one parameter");
            return;
        }
        String login = args[0];
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(GithubServiceConfig.class);
        GithubService githubService = applicationContext.getBean("githubService", GithubService.class);
        Collection<GithubBranch> userBranches = githubService.getUserBranches(login);
        userBranches.forEach(b -> githubService.deleteBranch(REPO, b.getBranchName()));
    }
}
