package ru.yandex.market.github.pr.stats.service.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * @author fbokovikov
 */
@Builder
@Getter
public class GithubBranch {

    private final String branchName;
    private final String branchOwner;
    private final Instant updatedAt;
    private final String repository;
    private final String sha;
}
