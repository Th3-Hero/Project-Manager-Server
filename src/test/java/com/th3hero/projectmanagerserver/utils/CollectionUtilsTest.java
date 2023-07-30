package com.th3hero.projectmanagerserver.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {
    @Test
    void findInCollection_matchingItem() {
        final var set = Set.of(1, 2, 3);

        final var result = CollectionUtils.findIn(set, num -> num == 2);

        assertThat(result).contains(2);
    }

    @Test
    void findInCollection_noMatchingItem() {
        final var list = List.of(1, 2, 3);

        final var result = CollectionUtils.findIn(list, num -> num == 4);

        assertThat(result).isEmpty();
    }

    @Test
    void findInCollection_filteredItems() {
        final var set = Set.of(1, 2, 3);

        final var result = CollectionUtils.filter(set, num -> num != 2);

        assertThat(result).containsExactlyInAnyOrder(1, 3);
    }

    @Test
    void filter_noFilteredItems() {
        final var list = List.of(1, 2, 3);

        final var result = CollectionUtils.filter(list, num -> num != 4);

        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    void transform() {
        final var list = List.of(1, 2, 3);

        final var result = CollectionUtils.transform(list, num -> num + 1);

        assertThat(result).containsExactly(2, 3, 4);
    }
}