package ru.yandex.market.github.pr.stats.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fbokovikov
 */
@RestController
@AllArgsConstructor
public class PageMatcherController {

    private final List<RequestMappingInfoHandlerMapping> handlerMapping;

    @GetMapping(value = "/help", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getMethods() {
        String result = handlerMapping.get(0).getHandlerMethods().keySet().stream()
                .map(PageMatcherController::toPagematchInfo)
                .collect(Collectors.joining("\n"));
        return result;
    }

    private static String toPagematchInfo(RequestMappingInfo requestMappingInfo) {
        RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
        PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
        return methodsCondition.getMethods() + " " + patternsCondition.getPatterns();
    }
}
