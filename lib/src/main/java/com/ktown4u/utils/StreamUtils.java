package com.ktown4u.utils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Stream Pipeline을 사용하는 유틸리티 함수들을 모아놓은 클래스
 */
public class StreamUtils {
    /**
     * Collection에서 조건에 맞는 첫번째 아이템을 Optional로 반환한다.
     *
     * @param sourceCollection stream 처리할 소스 컬렉션
     * @param predicate        filter에 사용할 조건
     * @param <T>              반환할 타입
     * @return 조건에 맞는 첫번째 아이템 or empty Optional
     */
    public static <T> Optional<T> getItem(final Collection<T> sourceCollection, final Predicate<T> predicate) {
        return sourceCollection.stream()
                .filter(predicate)
                .findFirst();
    }

    /**
     * Collection에서 조건에 맞는 아이템을 반환한다.
     * 조건에 맞는 아이템이 존재하지 않으면 errorMessage를 가진 예외를 발생시킨다.
     *
     * @param sourceCollection stream 처리할 소스 리스트
     * @param predicate        filter에 사용할 조건
     * @param errorMessage     조건에 맞는 아이템이 없을 때 발생시킬 예외의 메시지
     * @param <T>              반환할 타입
     * @return 조건에 맞는 아이템
     */
    public static <T> T getItemOrElseThrow(final Collection<T> sourceCollection, final Predicate<T> predicate, final String errorMessage) {
        return getItem(sourceCollection, predicate)
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    /**
     * Collection에서 idExtract를 적용해서 Id 컬렉션을 반환한다.
     *
     * @param sourceCollection stream 처리할 소스 리스트
     * @param idExtractor      소스 리스트의 원소에서 id를 추출할 함수
     * @param <T>              sourceList의 타입
     * @return id 컬렉션
     */
    public static <T> Collection<Long> fetchIds(Collection<T> sourceCollection, Function<T, Long> idExtractor) {
        return sourceCollection.stream()
                .map(idExtractor)
                .toList();
    }

    /**
     * Collection에 fethId를 적용해서 얻은 Id 컬렉션으로 객체를 반환하는 fetch Function을 적용해서 얻은 객체 컬렉션을 반환한다.
     *
     * @param sourceCollection stream 처리할 소스 리스트
     * @param idExtractor      소스 리스트의 원소에서 id를 추출할 함수
     * @param fetchFunction    id를 받아서 데이터를 가져올 함수
     * @param <T>              sourceList의 타입
     * @return idExtractor가 반환한 id 값들을 갖는 객체 컬렉션
     */
    public static <T> Collection<T> fetchItems(Collection<T> sourceCollection, Function<T, Long> idExtractor, Function<Collection<Long>, Collection<T>> fetchFunction) {
        Collection<Long> ids = fetchIds(sourceCollection, idExtractor);
        return fetchFunction.apply(ids);
    }
}