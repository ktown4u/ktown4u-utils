package com.ktown4u.utils.testprinter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TODO:
 * [X] getItemOrElseThrow_throws_exception_whenNotFound, 컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소가 없으면 예외를 발생시킨다
 * [X] getItemOrElseThrow_returns_found_whenFound, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소를 반환한다
 * [X] getItem_returns_optional, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소나 empty Optional을 반환한다
 * [ ] fetchItems, 컬렉션, Id 추출 Function, Id 컬렉션으로 객체를 반환하는 fetch Function을 제공하면 Id 컬렉션을 추출하여 fetch Function을 실행한 결과를 반환한다
 */
class SteamUtilsTest {

    private List<User> sourceList;
    private String errorMessage;

    @BeforeEach
    void setUp() {
        sourceList = List.of(new User("msbaek"), new User("msbaek2"));
        errorMessage = "해당 사용자가 존재하지 않습니다.";
    }

    @DisplayName("컬렉션, Predicate를 제공하면 조건을 만족하는 첫번째 원소를 포함하는 Optional을 반환한다")
    @Test
    void getItem_returns_optional() {
        Optional<User> user = getItem(sourceList, user1 -> user1.name().equals("msbaek"));
        user.map(User::name).ifPresentOrElse(
                name -> assertThat(name).isEqualTo("msbaek"),
                () -> assertThat(false).isTrue()
        );
    }

    @DisplayName("컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소가 없으면 예외를 발생시킨다")
    @Test
    void getItemOrElseThrow_throws_exception_whenNotFound() {
        assertThrows(IllegalArgumentException.class, () -> getItemOrElseThrow(sourceList, user -> user.name().equals("msbaek3"), errorMessage));
    }

    @DisplayName("컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소를 반환한다")
    @Test
    void getItemOrElseThrow_returns_found_whenFound() {
        User user = getItemOrElseThrow(sourceList, user1 -> user1.name().equals("msbaek"), errorMessage);
        assertThat(user).isEqualTo(new User("msbaek"));
    }

    /**
     * List를 받아서 조건에 맞는 첫번째 아이템을 Optional로 반환한다.
     *
     * @param sourceList stream 처리할 소스 리스트
     * @param predicate  filter에 사용할 조건
     * @param <T>        반환할 타입
     * @return 조건에 맞는 첫번째 아이템 or empty Optional
     */
    public static <T> Optional<T> getItem(final List<T> sourceList, final Predicate<T> predicate) {
        return sourceList.stream()
                .filter(predicate)
                .findFirst();
    }

    /**
     * List를 받아서 조건에 맞는 아이템을 반환한다.
     * 조건에 맞는 아이템이 존재하지 않으면 errorMessage를 가진 예외를 발생시킨다.
     *
     * @param sourceList   stream 처리할 소스 리스트
     * @param predicate    filter에 사용할 조건
     * @param errorMessage 조건에 맞는 아이템이 없을 때 발생시킬 예외의 메시지
     * @param <T>          반환할 타입
     * @return 조건에 맞는 아이템
     */
    public static <T> T getItemOrElseThrow(final List<T> sourceList, final Predicate<T> predicate, final String errorMessage) {
        return getItem(sourceList, predicate)
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }
}

record User(String name) {
    public User(String name) {
        this.name = name;
    }
}