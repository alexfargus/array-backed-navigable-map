/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Stream;

/**
 *
 * @author alex
 */
public abstract class AbstractTimeseries extends AbstractMap<Long, Object> implements NavigableMap<Long, Object> {

    protected final Data data;

    protected TimeseriesEntrySet entrySet = null;
    protected TimeseriesKeySet keySet = null;

    public AbstractTimeseries(Data data) {
        this.data = data;
    }

    public abstract Stream<Map.Entry<Long, Object>> entryStream();

    public abstract Stream<Map.Entry<Long, Object>> descendingEntryStream();

    @Override
    public Entry<Long, Object> lowerEntry(Long key) {
        return descendingEntryStream()
                .filter(e -> e.getKey() < key)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long lowerKey(Long key) {
        final Entry<Long, Object> entry = lowerEntry(key);
        return entry == null ? null : entry.getKey();
    }

    @Override
    public Entry<Long, Object> floorEntry(Long key) {
        return descendingEntryStream()
                .filter(e -> e.getKey() <= key)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long floorKey(Long key) {
        final Entry<Long, Object> entry = floorEntry(key);
        return entry == null ? null : entry.getKey();
    }

    @Override
    public Entry<Long, Object> ceilingEntry(Long key) {
        return entryStream()
                .filter(e -> e.getKey() >= key)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long ceilingKey(Long key) {
        final Entry<Long, Object> entry = ceilingEntry(key);
        return entry == null ? null : entry.getKey();
    }

    @Override
    public Entry<Long, Object> higherEntry(Long key) {
        return entryStream()
                .filter(e -> e.getKey() > key)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long higherKey(Long key) {
        final Entry<Long, Object> entry = higherEntry(key);
        return entry == null ? null : entry.getKey();
    }

    @Override
    public Entry<Long, Object> firstEntry() {
        return entryStream().findFirst().orElse(null);
    }

    @Override
    public Entry<Long, Object> lastEntry() {
        return descendingEntryStream().findFirst().orElse(null);
    }

    @Override
    public NavigableMap<Long, Object> headMap(Long toKey, boolean inclusive) {
        return subMap(firstKey(), true, toKey, inclusive);
    }

    @Override
    public NavigableMap<Long, Object> tailMap(Long fromKey, boolean inclusive) {
        return subMap(fromKey, inclusive, lastKey(), true);
    }

    @Override
    public SortedMap<Long, Object> subMap(Long fromKey, Long toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    @Override
    public SortedMap<Long, Object> headMap(Long toKey) {
        return headMap(toKey, false);
    }

    @Override
    public SortedMap<Long, Object> tailMap(Long fromKey) {
        return tailMap(fromKey, true);
    }

    @Override
    public Set<Entry<Long, Object>> entrySet() {
        final TimeseriesEntrySet es = entrySet;
        return (es != null) ? es : (entrySet = new TimeseriesEntrySet(this));
    }

    @Override
    public Set<Long> keySet() {
        return navigableKeySet();
    }

    @Override
    public NavigableSet<Long> navigableKeySet() {
        final TimeseriesKeySet ks = keySet;
        return (ks != null) ? ks : (keySet = new TimeseriesKeySet(this));
    }

    @Override
    public NavigableSet<Long> descendingKeySet() {
        return descendingMap().navigableKeySet();
    }

    @Override
    public Entry<Long, Object> pollFirstEntry() {
        Entry<Long, Object> entry = firstEntry();
        if (entry != null) {
            entry = new SimpleImmutableEntry<>(entry.getKey(), entry.getValue());
            data.remove(entry.getKey());
        }
        return entry;
    }

    @Override
    public Entry<Long, Object> pollLastEntry() {
        Entry<Long, Object> entry = lastEntry();
        if (entry != null) {
            entry = new SimpleImmutableEntry<>(entry.getKey(), entry.getValue());
            data.remove(entry.getKey());
        }
        return entry;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Object put(Long key, Object value) {
        return data.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        if (Long.class.isInstance(key)) {
            return data.remove((Long) key);
        }
        return null;
    }

    @Override
    public Object get(Object key) {
        if (Long.class.isInstance(key)) {
            return data.get((Long) key);
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        if (Long.class.isInstance(key)) {
            return data.containsKey((Long) key);
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public void clear() {
        data.clear();
    }
    
}
