package ru.yandex.market.github.pr.stats.service.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * @author fbokovikov
 */
@AllArgsConstructor
public class GithubServiceDao {

    private static final String INSERT_BRANCH_SQL ="" +
            "INSERT INTO GITHUB_STATS.GITHUB_BRANCH (" +
            "    BRANCH_NAME, " +
            "    BRANCH_OWNER, " +
            "    UPDATED_AT, " +
            "    REPOSITORY_NAME, " +
            "    SHA" +
            ") VALUES (?, ?, ?, ?, ?)";

    private static final String TRUNCATE_STAT_SQL = "" +
            "TRUNCATE TABLE GITHUB_STATS.GITHUB_BRANCH";

    private static final String GET_ALL_BRANCHES_SQL = "" +
            "SELECT " +
            "    BRANCH_NAME, " +
            "    BRANCH_OWNER, " +
            "    UPDATED_AT, " +
            "    REPOSITORY_NAME, " +
            "    SHA " +
            "FROM GITHUB_STATS.GITHUB_BRANCH";

    private static final String GET_USER_BRANCHES_SQL =
            GET_ALL_BRANCHES_SQL + " WHERE BRANCH_OWNER = ?";

    private static final String DELETE_BRANCH_SQL = "" +
            "DELETE FROM GITHUB_STATS.GITHUB_BRANCH " +
            "WHERE BRANCH_NAME = ? AND REPOSITORY_NAME = ?";

    private static final RowMapper<GithubBranch> GITHUB_BRANCH_MAPPER = new GithubBranchRowMapper();

    private final JdbcTemplate jdbcTemplate;

    public void saveBranches(List<GithubBranch> githubBranches) {
        jdbcTemplate.update(TRUNCATE_STAT_SQL);
        jdbcTemplate.batchUpdate(
                INSERT_BRANCH_SQL,
                githubBranches,
                githubBranches.size(),
                (ps, branch) -> {
                    ps.setString(1, branch.getBranchName());
                    ps.setString(2, branch.getBranchOwner());
                    ps.setTimestamp(3, Timestamp.from(branch.getUpdatedAt()));
                    ps.setString(4, branch.getRepository());
                    ps.setString(5, branch.getSha());
                }
        );
    }

    public Collection<GithubBranch> getAllBranches () {
        return jdbcTemplate.query(
                GET_ALL_BRANCHES_SQL,
                GITHUB_BRANCH_MAPPER
        );
    }

    public Collection<GithubBranch> getUserBranches(String login) {
        return jdbcTemplate.query(
                GET_USER_BRANCHES_SQL,
                GITHUB_BRANCH_MAPPER,
                login
        );
    }

    public void deleteBranch(GithubBranch githubBranch) {
        jdbcTemplate.update(
                DELETE_BRANCH_SQL,
                githubBranch.getBranchName(),
                githubBranch.getRepository()
        );
    }


}
