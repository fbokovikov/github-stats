package ru.yandex.market.github.pr.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author fbokovikov
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Getter
@ToString
public class ErrorResponse {

    @XmlElement(name = "message")
    private final String message;
}
