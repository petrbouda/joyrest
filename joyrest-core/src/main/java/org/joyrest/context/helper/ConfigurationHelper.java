package org.joyrest.context.helper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.joyrest.common.annotation.General;
import org.joyrest.common.annotation.Ordered;
import org.joyrest.transform.Transformer;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public final class ConfigurationHelper {

    public static <T extends Ordered> List<T> sort(Collection<T> collection) {
        Comparator<T> aspectComparator =
            (e1, e2) -> Integer.compare(e1.getOrder(), e2.getOrder());

        return collection.stream()
            .sorted(aspectComparator)
            .collect(toList());
    }

    public static <T extends Transformer> Map<Boolean, List<T>> createTransformers(List<T> transform) {
        return transform.stream()
            .collect(partitioningBy(General::isGeneral));
    }
}
