package ru.yandex.market.github.pr.stats.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.yandex.market.github.pr.stats.config.PrApplicationConfig;

/**
 * @author fbokovikov
 */
public class Main {

    public static void main(String ... args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PrApplicationConfig.class);
    }
}
