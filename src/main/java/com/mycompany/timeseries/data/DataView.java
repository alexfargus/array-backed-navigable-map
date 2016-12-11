/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries.data;

import com.mycompany.timeseries.Data;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author alex
 */
public class DataView implements Data {

    private final Data delegate;

    private Long lower;
    private final Boolean includeLower;

    private Long upper;
    private final Boolean includeUpper;

    public DataView(Data delegate, Long fromKey, Boolean fromInclusive, Long toKey, Boolean toInclusive) {
        this.delegate = delegate;
        this.lower = fromKey;
        this.includeLower = fromInclusive;
        this.upper = toKey;
        this.includeUpper = toInclusive;
    }

    @Override
    public Stream<Map.Entry<Long, Object>> entryStream(Long fromKey, boolean ascending) {
        final Long from = firstKey(ascending);
        return delegate.entryStream(from, ascending)
                .filter(e -> keyWithinBounds(e.getKey()));
    }

    private boolean checkLowerBound(Long key) {
        if (includeLower) {
            return key >= lower;
        }
        return key > lower;
    }
    
    private boolean checkUpperBound(Long key) {
        if (includeUpper) {
            return key <= upper;
        }
        return key < upper;
    }    
    
    private Stream<Map.Entry<Long, Object>> stream() {
        return entryStream(firstKey(true), true);
    }

    private boolean keyWithinBounds(Long key) {
        return checkLowerBound(key) && checkUpperBound(key);
    }

    @Override
    public Long firstKey(boolean ascending) {
        if (lower == null) {
            return delegate.firstKey(ascending);
        }
        return ascending ? lower : upper;
    }

    @Override
    public Long lastKey(boolean ascending) {
        if (upper == null) {
            return delegate.lastKey(ascending);
        }
        return ascending ? upper : lower;
    }

    @Override
    public int size() {
        return (int) stream().count();
    }

    @Override
    public boolean isEmpty() {
        return stream().findAny().isPresent();
    }

    @Override
    public boolean containsKey(Long key) {
        return keyWithinBounds(key) && delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return stream().anyMatch(e -> Objects.equals(e.getValue(), value));
    }

    @Override
    public Object get(Long key) {
        if (keyWithinBounds(key)) {
            return delegate.get(key);
        }
        return null;
    }

    @Override
    public Object put(Long key, Object value) {
        if (key < lower) {
            lower = key;
        }
        if (key > upper) {
            upper = key;
        }
        return delegate.put(key, value);
    }

    @Override
    public Object remove(Long key) {
        return delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Object> map) {
        delegate.putAll(map);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

}