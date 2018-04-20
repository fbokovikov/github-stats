package ru.yandex.market.github.pr.stats.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author fbokovikov
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class StatDTO {

    @XmlElement(name = "login")
    private final String login;

    @XmlElement(name = "count")
    private final int count;
}
