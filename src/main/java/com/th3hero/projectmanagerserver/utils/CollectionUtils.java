package com.th3hero.projectmanagerserver.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtils {

    /**
     * Searches the provided collection, returning the first item after applying the filter.
     *
     * @param toSearch The collection to search for the item
     * @param predicate The predicate to apply to the filter
     * @return An optional containing the item if found
     */
    public static <T> Optional<T> findIn(final Collection<T> toSearch, final Predicate<T> predicate) {
        return toSearch.stream()
            .filter(predicate)
            .findFirst();
    }

    /**
     * Filters the provided collection.
     *
     * @param toFilter The collection to filter
     * @param predicate The predicate to apply to the filter
     * @return The filtered collection
     */
    public static <T> Collection<T> filter(final Collection<T> toFilter, final Predicate<T> predicate) {
        return toFilter.stream()
            .filter(predicate)
            .toList();
    }

    /**
     * Transforms the provided collection via a single mapping.
     *
     * @param toTransform The collection to transform
     * @param function The function to apply to the map
     * @return The transformed collection
     */
    public static <T, R> Collection<R> transform(final Collection<T> toTransform, final Function<? super T, R> function) {
        return toTransform.stream()
            .map(function)
            .toList();
    }

    /**
     * Replaces the destination collection with the source collection. <P>
     * Used for hibernate managed collections.
     *
     * @param destination The collection to replace
     * @param source The collection the destination will be replaced with
     * @return destination repopulated by source
     */
    public static <T> Collection<T> replaceList(final Collection<T> destination, final Collection<T> source) {
        destination.clear();
        destination.addAll(source);
        return destination;
    }
}
