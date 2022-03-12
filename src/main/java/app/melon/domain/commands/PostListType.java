package app.melon.domain.commands;

import java.util.Arrays;

public enum PostListType {
    Recent("recent"),
    Like("like"),
    Popular("popular"),
    Me("me");

    private final String name;

    PostListType(String name) {
        this.name = name;
    }

    public static PostListType fromName(String name) {
        return Arrays.stream(PostListType.values())
                .filter(e -> e.name.equals(name)).findFirst().get();
    }
}
