package ru.yandex.market.github.pr.stats.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.yandex.market.github.pr.stats.service.GithubService;
import ru.yandex.market.github.pr.stats.service.GithubServiceConfig;

/**
 * @author fbokovikov
 */
public class ImportGithubBranchesJob {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Expected 2 arguments");
            return;
        }
        String repositoryOwner = args[0];
        String repositoryName = args[1];
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(GithubServiceConfig.class);
        GithubService githubService = applicationContext.getBean("githubService", GithubService.class);
        githubService.importGithubBranches(repositoryOwner, repositoryName);
        System.out.println("Import completed successfully");
    }
}
