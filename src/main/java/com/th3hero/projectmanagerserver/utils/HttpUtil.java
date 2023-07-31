package com.th3hero.projectmanagerserver.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtil {
    public static final String MISSING_PROJECT_WITH_ID = "Unable to find Project with provided id";
    public static final String MISSING_FIELD_WITH_ID = "Unable to find Field with provided id";
    public static final String MISSING_TAG_WITH_ID = "Unable to find Tag with provided id";

}