package com.th3hero.projectmanagerserver.utils;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUtils {

    public static <T> List<T> replaceList(List<T> destination, List<T> source) {
        destination.clear();
        destination.addAll(source);
        return destination;
    }

}
