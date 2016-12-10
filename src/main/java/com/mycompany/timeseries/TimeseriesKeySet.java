/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import com.google.common.collect.ForwardingIterator;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedSet;

/**
 *
 * @author alex
 */
public class TimeseriesKeySet extends AbstractSet<Long> implements NavigableSet<Long> {

    final AbstractTimeseries timeseries;

    public TimeseriesKeySet(AbstractTimeseries map) {
        this.timeseries = map;
    }

    @Override
    public Iterator<Long> iterator() {
        return new KeyIterator(timeseries.entryStream().iterator());
    }

    @Override

    public int size() {
        return timeseries.size();
    }

    @Override
    public Comparator<? super Long> comparator() {
        return timeseries.comparator();
    }

    @Override
    public Long first() {
        return timeseries.firstKey();
    }

    @Override
    public Long last() {
        return timeseries.lastKey();
    }

    @Override
    public Long lower(Long e) {
        return timeseries.lowerKey(e);
    }

    @Override
    public Long floor(Long e) {
        return timeseries.floorKey(e);
    }

    @Override
    public Long ceiling(Long e) {
        return timeseries.ceilingKey(e);
    }

    @Override
    public Long higher(Long e) {
        return timeseries.higherKey(e);
    }

    @Override
    public boolean contains(Object o) {
        if (Long.class.isInstance(o)) {
            return timeseries.containsKey((Long) o);
        }
        return false;
    }

    @Override
    public Long pollFirst() {
        final Map.Entry<Long, Object> entry = timeseries.pollFirstEntry();
        return entry == null ? null : entry.getKey();
    }

    @Override
    public Long pollLast() {
        final Map.Entry<Long, Object> entry = timeseries.pollLastEntry();
        return entry == null ? null : entry.getKey();
    }

    @Override
    public NavigableSet<Long> descendingSet() {
        return timeseries.descendingKeySet();
    }

    @Override
    public Iterator<Long> descendingIterator() {
        return new KeyIterator(timeseries.descendingEntryStream().iterator());
    }

    @Override
    public NavigableSet<Long> subSet(Long fromElement, boolean fromInclusive, Long toElement, boolean toInclusive) {
        return timeseries.subMap(fromElement, fromInclusive, toElement, toInclusive).navigableKeySet();
    }

    @Override
    public NavigableSet<Long> headSet(Long toElement, boolean inclusive) {
        return timeseries.headMap(toElement, inclusive).navigableKeySet();
    }

    @Override
    public NavigableSet<Long> tailSet(Long fromElement, boolean inclusive) {
        return timeseries.tailMap(fromElement, inclusive).navigableKeySet();
    }

    @Override
    public SortedSet<Long> subSet(Long fromElement, Long toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<Long> headSet(Long toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<Long> tailSet(Long fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public void clear() {
        timeseries.clear();
    }

    private class KeyIterator extends ForwardingIterator<Long> {

        private final Iterator<Map.Entry<Long, Object>> delegate;
        private Map.Entry<Long, Object> next = null;

        public KeyIterator(Iterator<Map.Entry<Long, Object>> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Iterator delegate() {
            return delegate;
        }

        @Override
        public Long next() {
            next = delegate.next();
            return next.getKey();
        }

        @Override
        public void remove() {

            timeseries.remove(next.getKey());
            /*
            if (oldValue == null) {
                throw new IllegalStateException("remove() can only be called once before calling next()");
            }
             */
        }
    }

}
