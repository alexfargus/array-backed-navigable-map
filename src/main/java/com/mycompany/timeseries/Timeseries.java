/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingNavigableMap;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 *
 * @author alex
 */
public class Timeseries extends AbstractMap<Long, Object> implements NavigableMap<Long, Object> {

    private Long firstKey = null;
    private final long indexOffset = 1L;
    private final List<Double> values = new ArrayList<>();
    private Integer size = 0;

    private EntrySet entrySet = null;
    private KeySet keySet = null;

    @Override
    public Entry<Long, Object> lowerEntry(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long lowerKey(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<Long, Object> floorEntry(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long floorKey(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<Long, Object> ceilingEntry(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long ceilingKey(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<Long, Object> higherEntry(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long higherKey(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<Long, Object> firstEntry() {
        return getEntry(firstKey);
    }

    @Override
    public Entry<Long, Object> lastEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<Long, Object> pollFirstEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<Long, Object> pollLastEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NavigableMap<Long, Object> descendingMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NavigableSet<Long> descendingKeySet() {
        return navigableKeySet();
    }

    @Override
    public NavigableMap<Long, Object> subMap(Long fromKey, boolean fromInclusive, Long toKey, boolean toInclusive) {
        return new NavigableSubMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    @Override
    public NavigableMap<Long, Object> headMap(Long toKey, boolean inclusive) {
        return new NavigableSubMap(firstKey, true, toKey, inclusive);
    }

    @Override
    public NavigableMap<Long, Object> tailMap(Long fromKey, boolean inclusive) {
        return new NavigableSubMap(fromKey, inclusive, Long.MAX_VALUE, true);
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
    public Comparator<? super Long> comparator() {
        return Long::compareTo;
    }

    @Override
    public Long firstKey() {
        return firstKey;
    }

    @Override
    public Long lastKey() {
        return firstKey + (indexOffset * size);
    }

    @Override
    public Set<Entry<Long, Object>> entrySet() {
        final EntrySet es = entrySet;
        return (es != null) ? es : (entrySet = new EntrySet());
    }

    @Override
    public Set<Long> keySet() {
        return navigableKeySet();
    }

    @Override
    public NavigableSet<Long> navigableKeySet() {
        final KeySet ks = keySet;
        return (ks != null) ? ks : (keySet = new KeySet(this));
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
    public boolean containsKey(Object key) {
        if (Long.class.isInstance(key)) {
            return containsKey((Long) key);
        }
        return false;
    }

    public boolean containsKey(Long key) {
        if (isEmpty()) {
            return false;
        }
        final Integer index = getIndex(key);
        return index >= 0 && index <= values.size() && values.get(index) != null;

    }

    @Override
    public boolean containsValue(Object value) {
        if (Double.class.isInstance(value)) {
            return values.contains((Double) value);
        }
        return false;
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public Object get(Object key) {
        if (containsKey(key)) {
            final Integer index = getIndex((Long) key);
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
        }
        final Double doubleValue = (Double) value;
        final Integer index = getIndex(key);
        if (index < 0) {
            firstKey = key;
            values.add(0, doubleValue);
            size += 1;
            return null;
        } else if (index >= values.size()) {
            values.add(index, doubleValue);
            size += 1;
            return null;
        } else {
            return values.set(index, doubleValue);
        }
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public Object remove(Object key) {
        if (containsKey(key)) {
            final Integer index = getIndex((Long) key);
            size -= 1;

            if (isEmpty()) {
                firstKey = null;
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

    @Override
    public int hashCode() {
        return entrySet().stream()
                .map(Entry::hashCode)
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
        final Timeseries other = (Timeseries) obj;
        if (!Objects.equals(this.firstKey, other.firstKey)) {
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

    private Integer getIndex(Long key) {
        return (int) Math.floorDiv(key - firstKey, indexOffset);
    }

    protected Stream<Map.Entry<Long, Object>> entryStream(Long fromKey) {
        final Integer fromIndex = getIndex(fromKey);
        final Iterator it = values.subList(fromIndex, values.size()).iterator();
        return Stream.iterate(fromKey, key -> key + indexOffset)
                .limit(size)
                .filter(key -> it.next() != null)
                .map(TimeseriesEntry::new);
    }

    protected Stream<Map.Entry<Long, Object>> descendingEntryStream(Long fromKey) {
        return null;
    }    
    
    private class EntryIterator extends ForwardingIterator<Map.Entry<Long, Object>> {

        private final Iterator<Entry<Long, Object>> delegate;
        private Long key = null;

        public EntryIterator(Iterator<Entry<Long, Object>> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Iterator<Entry<Long, Object>> delegate() {
            return this.delegate;
        }

        @Override
        public Entry<Long, Object> next() {
            final Entry<Long, Object> next = delegate.next();
            key = next.getKey();
            return next;
        }

        @Override
        public void remove() {
            final Object oldValue = Timeseries.this.remove(key);
            if (oldValue == null) {
                throw new IllegalStateException("remove() can only be called once before calling next()");
            }
        }
    }

    private class KeyIterator extends ForwardingIterator<Long> {

        private final Iterator<Entry<Long, Object>> delegate;
        private Long key = null;

        public KeyIterator(Iterator<Entry<Long, Object>> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Iterator delegate() {
            return delegate;
        }

        @Override
        public Long next() {
            final Entry<Long, Object> next = delegate.next();
            key = next.getKey();
            return key;
        }

        @Override
        public void remove() {
            final Object oldValue = Timeseries.this.remove(key);
            if (oldValue == null) {
                throw new IllegalStateException("remove() can only be called once before calling next()");
            }
        }
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
            return Timeseries.this.get(key);
        }

        @Override
        public Object setValue(Object value) {
            return Timeseries.this.put(key, value);
        }

        @Override

        public int hashCode() {
            final Object value = Timeseries.this.get(key);
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

    private class EntrySet extends AbstractSet<Map.Entry<Long, Object>> {

        @Override
        public Iterator<Map.Entry<Long, Object>> iterator() {
            return new EntryIterator(entryStream(firstKey).iterator());
        }

        @Override
        @SuppressWarnings("element-type-mismatch")
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
            final Object key = entry.getKey();
            final Object value = entry.getValue();

            return Timeseries.this.containsKey(key)
                    && Objects.equals(Timeseries.this.get(key), value);
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            return Timeseries.this.remove(key, value);
        }

        @Override
        public int size() {
            return Timeseries.this.size;
        }

        @Override
        public void clear() {
            Timeseries.this.clear();
        }

        @Override
        public Spliterator<Map.Entry<Long, Object>> spliterator() {
            return entryStream(firstKey).spliterator();
        }
    }

    private class KeySet extends AbstractSet<Long> implements NavigableSet<Long> {

        final NavigableMap<Long, Object> map;

        public KeySet(NavigableMap<Long, Object> map) {
            this.map = map;
        }

        @Override
        public Iterator<Long> iterator() {
            return new KeyIterator(entryStream(map.firstKey()).iterator());
        }

        @Override

        public int size() {
            return map.size();
        }

        @Override
        public Comparator<? super Long> comparator() {
            return map.comparator();
        }

        @Override
        public Long first() {
            return map.firstKey();
        }

        @Override
        public Long last() {
            return map.lastKey();
        }

        @Override
        public Long lower(Long e) {
            return map.lowerKey(e);
        }

        @Override
        public Long floor(Long e) {
            return map.floorKey(e);
        }

        @Override
        public Long ceiling(Long e) {
            return map.ceilingKey(e);
        }

        @Override
        public Long higher(Long e) {
            return map.higherKey(e);
        }

        @Override
        public Long pollFirst() {
            return map.pollFirstEntry().getKey();
        }

        @Override
        public Long pollLast() {
            return map.pollLastEntry().getKey();
        }

        @Override
        public NavigableSet<Long> descendingSet() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Iterator<Long> descendingIterator() {
            return new KeyIterator(descendingEntryStream(map.lastKey()).iterator());
        }

        @Override
        public NavigableSet<Long> subSet(Long fromElement, boolean fromInclusive, Long toElement, boolean toInclusive) {
            return map.subMap(firstKey, fromInclusive, firstKey, toInclusive).navigableKeySet();
        }

        @Override
        public NavigableSet<Long> headSet(Long toElement, boolean inclusive) {
            return map.headMap(firstKey, inclusive).navigableKeySet();
        }

        @Override
        public NavigableSet<Long> tailSet(Long fromElement, boolean inclusive) {
            return map.tailMap(firstKey, inclusive).navigableKeySet();
        }

        @Override
        public SortedSet<Long> subSet(Long fromElement, Long toElement) {
            return subMap(firstKey, true, firstKey, false).navigableKeySet();
        }

        @Override
        public SortedSet<Long> headSet(Long toElement) {
            return headSet(toElement, false);
        }

        @Override
        public SortedSet<Long> tailSet(Long fromElement) {
            return tailSet(fromElement, true);
        }

    }
    
    private class DescendingTimeseries extends ForwardingNavigableMap<Long, Object> {

        @Override
        protected NavigableMap<Long, Object> delegate() {
            return Timeseries.this;
        }
        
        
        
    }

    private class NavigableSubMap extends AbstractMap<Long, Object> implements NavigableMap<Long, Object> {

        private final Long from;
        private final Long to;

        private EntrySet entrySet = null;

        public NavigableSubMap(Long from, Boolean fromInclusive, Long to, Boolean toInclusive) {
            this.from = from;
            this.to = to;
        }

        @Override
        public Set<Entry<Long, Object>> entrySet() {
            final EntrySet es = entrySet;
            return (es != null) ? es : (entrySet = new EntrySet());
        }

        @Override
        public Entry<Long, Object> lowerEntry(Long key) {
            return Timeseries.this.lowerEntry(key);
        }

        @Override
        public Long lowerKey(Long key) {
            return Timeseries.this.lowerKey(key);
        }

        @Override
        public Entry<Long, Object> floorEntry(Long key) {
            return Timeseries.this.floorEntry(key);
        }

        @Override
        public Long floorKey(Long key) {
            return Timeseries.this.floorKey(key);
        }

        @Override
        public Entry<Long, Object> ceilingEntry(Long key) {
            return Timeseries.this.ceilingEntry(key);
        }

        @Override
        public Long ceilingKey(Long key) {
            return Timeseries.this.ceilingKey(key);
        }

        @Override
        public Entry<Long, Object> higherEntry(Long key) {
            return Timeseries.this.higherEntry(key);
        }

        @Override
        public Long higherKey(Long key) {
            return Timeseries.this.higherKey(key);
        }

        @Override
        public Entry<Long, Object> firstEntry() {
            return Timeseries.this.getEntry(from);
        }

        @Override
        public Entry<Long, Object> lastEntry() {
            return Timeseries.this.getEntry(to);
        }

        @Override
        public Entry<Long, Object> pollFirstEntry() {
            final Entry<Long, Object> entry = entryStream(from).findFirst().orElse(null);
            if (entry != null) {
                Timeseries.this.remove(entry.getKey());
            }
            return entry;
        }

        @Override
        public Entry<Long, Object> pollLastEntry() {
            final Entry<Long, Object> entry = descendingEntryStream(to).findFirst().orElse(null);
            if (entry != null) {
                Timeseries.this.remove(entry.getKey());
            }
            return entry;
        }

        @Override
        public NavigableMap<Long, Object> descendingMap() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public NavigableSet<Long> navigableKeySet() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public NavigableSet<Long> descendingKeySet() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public NavigableMap<Long, Object> subMap(Long fromKey, boolean fromInclusive, Long toKey, boolean toInclusive) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public NavigableMap<Long, Object> headMap(Long toKey, boolean inclusive) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public NavigableMap<Long, Object> tailMap(Long fromKey, boolean inclusive) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public SortedMap<Long, Object> subMap(Long fromKey, Long toKey) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public SortedMap<Long, Object> headMap(Long toKey) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public SortedMap<Long, Object> tailMap(Long fromKey) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Comparator<? super Long> comparator() {
            return Timeseries.this.comparator();
        }

        @Override
        public Long firstKey() {
            return entryStream(from).findFirst().map(Entry::getKey).orElse(null);
        }

        @Override
        public Long lastKey() {
            return descendingEntryStream(to).findFirst().map(Entry::getKey).orElse(null);
        }

    }
}
