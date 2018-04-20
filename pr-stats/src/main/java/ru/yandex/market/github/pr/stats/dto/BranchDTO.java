package ru.yandex.market.github.pr.stats.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.yandex.market.github.pr.stats.service.model.GithubBranch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author fbokovikov
 */
@XmlRootElement(name = "branch")
@XmlAccessorType(XmlAccessType.NONE)
@Getter
@ToString
@EqualsAndHashCode
public class BranchDTO {

    private final String updatedAt;
    private final String branchName;
    private final String branchOwner;

    public BranchDTO(GithubBranch branch) {
        this.updatedAt = branch.getUpdatedAt().toString();
        this.branchName = branch.getBranchName();
        this.branchOwner = branch.getBranchOwner();
    }
}
