package ru.yandex.market.github.pr.stats.util;

import ru.yandex.market.github.pr.stats.dto.StatDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author fbokovikov
 */
public class StreamUtil {

    private StreamUtil() {

    }

    public static  <T> List<String> mapToString(
            Collection<T> values,
            Function<T, String> mappingFunction
    ) {
        return values.stream()
                .map(v -> mappingFunction.apply(v))
                .collect(Collectors.toList());
    }
}
