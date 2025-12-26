package com.xenialsoft.api.common.util;

import java.util.List;

import com.xenialsoft.api.common.Sequenceable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SequenceUtils {

    /**
     * 목록의 모든 요소에 1부터 순번을 할당합니다.
     * 
     * @param <T>
     * @param items
     */
    public <T extends Sequenceable> List<T> assign(List<T> items) {
        return assign(items, 1);
    }

    /**
     * 목록의 모든 요소에 순번을 할당합니다.
     * 
     * @param <T>
     * @param items
     * @param start
     */
    public <T extends Sequenceable> List<T> assign(List<T> items, long start) {
        return assign(items, start, false);
    }

    /**
     * 목록의 모든 요소에 순번을 할당합니다.
     * 
     * @param <T>
     * @param items
     * @param start
     * @param descending
     */
    public <T extends Sequenceable> List<T> assign(List<T> items, long start, boolean descending) {
        if (items == null || items.isEmpty()) {
            return items;
        }
        if (descending) {
            for (T item : items) {
                item.setSequence(start);
                start -= 1;
            }
        } else {
            for (T item : items) {
                item.setSequence(start);
                start += 1;
            }
        }
        return items;
    }

}
