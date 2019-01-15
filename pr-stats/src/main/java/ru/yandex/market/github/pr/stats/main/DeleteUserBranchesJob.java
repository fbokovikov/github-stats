package ru.yandex.market.github.pr.stats.main;

import com.google.common.collect.ImmutableSet;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.yandex.market.github.pr.stats.service.GithubService;
import ru.yandex.market.github.pr.stats.service.GithubServiceConfig;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import java.util.Collection;
import java.util.Set;

/**
 * @author fbokovikov
 */
public class DeleteUserBranchesJob {

    private static final Logger log = LoggerFactory.getLogger(DeleteUserBranchesJob.class);

    private static final IRepositoryIdProvider REPO = () -> "market-java/mbi";
    private static final Set<String> NON_DELETED_BRANCHES =
            ImmutableSet.of("MBI-32296");
    private static final String LOGIN = "ogonek";

    public static void main(String ... args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(GithubServiceConfig.class);
        GithubService githubService = applicationContext.getBean("githubService", GithubService.class);
        Collection<GithubBranch> userBranches = githubService.getUserBranches(LOGIN);
        int successfullyDeleted  = 0;
        for (GithubBranch branch : userBranches) {
            try {
                String branchName = branch.getBranchName();
                if (NON_DELETED_BRANCHES.contains(branchName)) {
                    continue;
                }
                githubService.deleteBranch(REPO, branch);
                successfullyDeleted++;
            } catch (Exception e) {
                log.error("Internal error", e);
            }
        }
        log.info("Successfully deleted {} of {} branches", successfullyDeleted, userBranches.size());
    }
}
