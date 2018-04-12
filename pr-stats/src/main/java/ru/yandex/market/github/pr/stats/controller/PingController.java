package ru.yandex.market.github.pr.stats.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fbokovikov
 */
@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "200;OK";
    }
}
