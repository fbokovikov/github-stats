package ru.yandex.market.github.pr.stats.service;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.market.github.pr.stats.config.FunctionalTest;

/**
 * @author fbokovikov
 */
public class BranchServiceTest extends FunctionalTest {
    private static final IRepositoryIdProvider MBI_REPO = () -> "market-java/mbi";

    @Autowired
    private BranchService branchService;

    @Test
    public void deleteBranchTest() {
        branchService.deleteBranch(MBI_REPO, "CSADMIN-17840");
    }
}
