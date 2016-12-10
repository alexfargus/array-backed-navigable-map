/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries.data;

import com.google.common.collect.FluentIterable;
import com.mycompany.timeseries.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author alex
 */
public class ArrayListData implements Data {

    private Long firstKey = null;
    private Long lastKey = null;
    private final long indexOffset = 1L;
    private final List<Object> values = new ArrayList<>();
    private Integer size = 0;

    private Integer getIndex(Long key) {
        return (int) Math.floorDiv(key - firstKey, indexOffset);
    }

    private Stream<Map.Entry<Long, Object>> ascendingEntryStream(Long fromKey) {
        if (isEmpty()) {
            return Stream.empty();
        }
        final Integer fromIndex = Math.max(getIndex(fromKey), 0);
        if (fromIndex > values.size()) {
            return Stream.empty();
        }        
        final ListIterator it = values.listIterator(fromIndex);
        return Stream.iterate(fromKey, key -> key + indexOffset)
                // Prevent infinite loops by limiting the max size of the stream
                .limit(values.size())
                .filter(key -> (it.hasNext() && it.next() != null) || Objects.equals(key, lastKey))
                // Terminate stream when we have all possible values
                .limit(size)
                .map(TimeseriesEntry::new);
    }

    private Stream<Map.Entry<Long, Object>> descendingEntryStream(Long fromKey) {
        if (isEmpty()) {
            return Stream.empty();
        }        
        final Integer fromIndex = Math.min(getIndex(fromKey), values.size());
        if (fromIndex < 0) {
            return Stream.empty();
        }                     
        final ListIterator it = values.listIterator(fromIndex);
        return Stream.iterate(fromKey, key -> key - indexOffset)
                // Prevent infinite loops by limiting the max size of the stream
                .limit(values.size())
                .peek(System.out::println)
                .filter(key -> (it.hasPrevious() && it.previous() != null) || Objects.equals(key, firstKey))
                .peek(System.out::println)
                // Terminate stream when we have all possible values
                .limit(size)
                .peek(System.out::println)
                .map(TimeseriesEntry::new);
    }

    @Override
    public Stream<Map.Entry<Long, Object>> entryStream(Long fromKey, boolean ascending) {
        if (ascending) {
            return ascendingEntryStream(fromKey);
        } else {
            return descendingEntryStream(fromKey);
        }
    }
    
    @Override
    public Long firstKey(boolean ascending) {
        return ascending ? firstKey : lastKey;
    }
    
    @Override
    public Long lastKey(boolean ascending) {
        return ascending ? lastKey : firstKey;
    }    

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Long key) {
        if (isEmpty()) {
            return false;
        }
        final Integer index = getIndex(key);
        return index >= 0 && index < values.size() && values.get(index) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public Object get(Long key) {
        if (containsKey(key)) {
            final Integer index = getIndex(key);
            return values.get(index);
        }
        return null;
    }

    protected Map.Entry<Long, Object> getEntry(Long key) {
        if (containsKey(key)) {
            return new TimeseriesEntry(key);
        }
        return null;
    }

    @Override
    public Object put(Long key, Object value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            firstKey = key;
            lastKey = key;
        }
        Integer index = getIndex(key);
        if (index < 0) {
            firstKey = key;
            values.addAll(0, makeListOfSize(-index));
            index = 0;
            size += 1;
        } else if (index >= values.size()) {
            lastKey = key;
            values.addAll(values.size(), makeListOfSize(index - values.size()));
            size += 1;
        } 

        return values.set(index, value);

    }

    private List<Object> makeListOfSize(Integer size) {
        return Stream.generate(() -> null)
                .limit(size + 1)
                .collect(Collectors.toList());
    }
    
    
    @Override
    public Object remove(Long key) {
        if (containsKey(key)) {
            final Integer index = getIndex(key);
            size -= 1;

            if (isEmpty()) {
                firstKey = null;
                lastKey = null;
            }

            return values.set(index, null);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Object> map) {
        map.forEach((key, value) -> this.put(key, value));
    }

    @Override
    public void clear() {
        this.firstKey = null;
        this.size = 0;
        this.values.clear();
    }

    private class TimeseriesEntry implements Map.Entry<Long, Object> {

        private final Long key;

        public TimeseriesEntry(Long key) {
            this.key = key;
        }

        @Override
        public Long getKey() {
            return this.key;
        }

        @Override
        public Object getValue() {
            return get(key);
        }

        @Override
        public Object setValue(Object value) {
            return put(key, value);
        }

        @Override
        public int hashCode() {
            final Object value = get(key);
            return (key == null ? 0 : key.hashCode())
                    ^ (value == null ? 0 : value.hashCode());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TimeseriesEntry other = (TimeseriesEntry) obj;
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            return true;
        }

    }

    @Override
    public int hashCode() {
        return ascendingEntryStream(firstKey)
                .map(Map.Entry::hashCode)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArrayListData other = (ArrayListData) obj;
        if (!Objects.equals(this.firstKey, other.firstKey)) {
            return false;
        }
        if (!Objects.equals(this.lastKey, other.lastKey)) {
            return false;
        }
        if (!Objects.equals(this.indexOffset, other.indexOffset)) {
            return false;
        }
        if (!Objects.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

}
