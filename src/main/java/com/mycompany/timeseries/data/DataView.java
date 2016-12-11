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
        return delegate.entryStream(fromKey, ascending)
                .filter(e -> keyWithinBounds(e.getKey()));
    }

    private boolean checkLowerBound(Long key) {
        final Long lowerBound = lowerBound();
        if (lowerBound == null) {
            return false;
        }
        if (includeLower) {
            return key >= lowerBound;
        }
        return key > lowerBound;
    }

    private boolean checkUpperBound(Long key) {
        final Long upperBound = upperBound();
        if (upperBound == null) {
            return false;
        }
        if (includeUpper) {
            return key <= upperBound;
        }
        return key < upperBound;
    }

    private Stream<Map.Entry<Long, Object>> stream() {
        return entryStream(lowerBound(), true);
    }

    private boolean keyWithinBounds(Long key) {
        return key != null && checkLowerBound(key) && checkUpperBound(key);
    }

    @Override
    public Long lowerBound() {
        return lower == null ? delegate.lowerBound() : lower;
    }

    @Override
    public Long upperBound() {
        return upper == null ? delegate.upperBound() : upper;
    }

    @Override
    public Long firstKey(boolean ascending) {
        final Long from;
        if (ascending) {
            from = lowerBound();
        } else {
            from = upperBound();
        }
        return entryStream(from, ascending).findFirst().map(e -> e.getKey()).orElse(null);
    }

    @Override
    public Long lastKey(boolean ascending) {
        final Long from;
        if (ascending) {
            from = upperBound();
        } else {
            from = lowerBound();
        }
        return entryStream(from, !ascending).findFirst().map(e -> e.getKey()).orElse(null);
    }

    @Override
    public int size() {
        return (int) stream().count();
    }

    @Override
    public boolean isEmpty() {
        if (upperBound() == null || lowerBound() == null) {
            return true;
        }
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
