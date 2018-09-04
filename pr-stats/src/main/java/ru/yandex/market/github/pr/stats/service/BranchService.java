package ru.yandex.market.github.pr.stats.service;

import lombok.SneakyThrows;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GitHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fbokovikov
 */
public class BranchService extends GitHubService {

    private static final Logger log = LoggerFactory.getLogger(BranchService.class);

    /**
     * Create branch service
     *
     * @param client
     */
    public BranchService(GitHubClient client) {
        super(client);
    }

    @SneakyThrows
    public void deleteBranch(IRepositoryIdProvider repository, String branchName) {
        client.delete(createUrlString(repository, branchName));
    }

    private static String createUrlString(IRepositoryIdProvider repository, String branchName) {
        String deleteBranchUrl = new StringBuilder()
                .append("/repos/")
                .append(repository.generateId())
                .append("/git/refs/heads/")
                .append(branchName)
                .toString();
        log.info("Branch url is: {}", deleteBranchUrl);
        return deleteBranchUrl;
    }
}
