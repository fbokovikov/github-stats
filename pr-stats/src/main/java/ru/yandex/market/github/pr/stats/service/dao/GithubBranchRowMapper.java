package ru.yandex.market.github.pr.stats.service.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author fbokovikov
 */
class GithubBranchRowMapper implements RowMapper<GithubBranch> {

    @Nullable
    @Override
    public GithubBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
        GithubBranch githubBranch = GithubBranch.builder()
                .branchName(rs.getString("branch_name"))
                .branchOwner(rs.getString("branch_owner"))
                .repository(rs.getString("repository_name"))
                .sha(rs.getString("sha"))
                .updatedAt(rs.getTimestamp("updated_at").toInstant())
                .build();
        return githubBranch;
    }
}
