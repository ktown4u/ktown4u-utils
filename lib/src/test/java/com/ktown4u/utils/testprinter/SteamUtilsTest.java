package com.ktown4u.utils.testprinter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TODO:
 * [X] getItem_whenNotFound, 컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소가 없으면 예외를 발생시킨다
 * [ ] getItem_whenFound, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소를 반환한다
 * [ ] fetchItems, 컬렉션, Id 추출 Function, Id 컬렉션으로 객체를 반환하는 fetch Function을 제공하면 Id 컬렉션을 추출하여 fetch Function을 실행한 결과를 반환한다
 */
class SteamUtilsTest {
    @DisplayName("컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소가 없으면 예외를 발생시킨다")
    @Test
    void getItem_whenNotFound() {
        List<User> sourceList = List.of(new User("msbaek"), new User("msbaek2"));
        Predicate<User> predicate = user -> user.name().equals("msbaek3");
        String errorMessage = "해당 사용자가 존재하지 않습니다.";
        assertThrows(IllegalArgumentException.class, () -> getItem(sourceList, predicate, errorMessage));
    }

    @DisplayName("컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소를 반환한다")
    @Test
    void getItem_whenFound() {
        List<User> sourceList = List.of(new User("msbaek"), new User("msbaek2"));
        Predicate<User> predicate = user -> user.name().equals("msbaek");
        String errorMessage = "해당 사용자가 존재하지 않습니다.";
        User user = getItem(sourceList, predicate, errorMessage);
        assertThat(user).isEqualTo(new User("msbaek"));
    }

    /**
     * List를 받아서 조건에 맞는 아이템을 추출한다.
     * 조건에 맞는 아이템이 존재하지 않으면 errorMessage를 가진 예외를 발생시킨다.
     *
     * @param sourceList stream 처리할 소스 리스트
     * @param predicate filter에 사용할 조건
     * @param errorMessage 조건에 맞는 아이템이 없을 때 발생시킬 예외의 메시지
     * @param <T> 반환할 타입
     * @return
     */
    public static <T> T getItem(final List<T> sourceList, final Predicate<T> predicate, final String errorMessage) {
        return sourceList.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }
}

record User(String name) {
    public User(String name) {
        this.name = name;
    }
}