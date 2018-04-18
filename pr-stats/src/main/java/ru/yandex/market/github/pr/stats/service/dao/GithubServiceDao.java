package ru.yandex.market.github.pr.stats.service.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import java.util.List;

/**
 * @author fbokovikov
 */
@AllArgsConstructor
public class GithubServiceDao {

    private static final String INSERT_BRANCH_SQL ="" +
            "INSERT INTO GITHUB_STATS.GITHUB_BRANCH (" +
            "    BRANCH_NAME," +
            "    BRANCH_OWNER," +
            "    UPDATE_DATETIME," +
            "    REPOSITORY_NAME" +
            ") VALUES (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public void saveBranches(List<GithubBranch> githubBranches) {
        jdbcTemplate.batchUpdate(
                INSERT_BRANCH_SQL,
                githubBranches,
                githubBranches.size(),
                (ps, branch) -> {
                    ps.setString(1, branch.getBranchName());
                    ps.setString(2, branch.getBranchOwner());
                    ps.setTimestamp(3, null);
                    ps.setString(4, branch.getRepository());
                }
        );
    }


}
