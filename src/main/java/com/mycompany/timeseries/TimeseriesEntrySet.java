/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import com.google.common.collect.ForwardingIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;

/**
 *
 * @author alex
 */
public class TimeseriesEntrySet extends AbstractSet<Map.Entry<Long, Object>> {

    private final AbstractTimeseries timeseries;

    public TimeseriesEntrySet(AbstractTimeseries timeseries) {
        this.timeseries = timeseries;
    }

    private class EntryIterator extends ForwardingIterator<Map.Entry<Long, Object>> {

        private final Iterator<Map.Entry<Long, Object>> delegate;
        private Long key = null;

        public EntryIterator(Iterator<Map.Entry<Long, Object>> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Iterator<Map.Entry<Long, Object>> delegate() {
            return this.delegate;
        }

        @Override
        public Map.Entry<Long, Object> next() {
            final Map.Entry<Long, Object> next = delegate.next();
            key = next.getKey();
            return next;
        }

        @Override
        public void remove() {
            final Object oldValue = timeseries.remove(key);
            /*
            if (oldValue == null) {
                throw new IllegalStateException("Entry has already been removed");
            }
            */
        }
    }

    @Override
    public Iterator<Map.Entry<Long, Object>> iterator() {
        return new EntryIterator(timeseries.entryStream().iterator());
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }

        final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
        if (!Long.class.isInstance(entry.getKey())) {
            return false;
        }

        final Long key = (Long) entry.getKey();
        final Object value = entry.getValue();

        return Objects.equals(timeseries.get(key), value);
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
        final Long key = (Long) entry.getKey();
        final Object value = entry.getValue();
        return Objects.equals(timeseries.remove(key), value);
    }

    @Override
    public int size() {
        return timeseries.size();
    }

    @Override
    public void clear() {
        timeseries.clear();
    }

    @Override
    public Spliterator<Map.Entry<Long, Object>> spliterator() {
        return timeseries.entryStream().spliterator();
    }

}
