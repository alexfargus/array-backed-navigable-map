/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import com.google.common.collect.testing.Helpers;
import com.google.common.collect.testing.SafeTreeMap;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestSortedMapGenerator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Stream;

/**
 *
 * @author alex
 */
public class TimeseriesTestGenerator implements TestSortedMapGenerator<Long, Object> {

    private static final Long MAX = 10L;
    private static final Long MIN = 0L;
    
    @Override
    public SortedMap<Long, Object> create(Object... elements) {
        final SortedMap<Long, Object> map = new Timeseries();
        Stream.of(elements)
                .map(element -> (Map.Entry<Long, Object>) element)
                .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        return map;
    }

    @Override
    public Map.Entry<Long, Object> belowSamplesLesser() {
        return makeEntry(MIN);
    }

    @Override
    public Map.Entry<Long, Object> belowSamplesGreater() {
        return makeEntry(MIN + 1);
    }

    @Override
    public Map.Entry<Long, Object> aboveSamplesLesser() {
        return makeEntry(MAX - 1);
    }

    @Override
    public Map.Entry<Long, Object> aboveSamplesGreater() {
        return makeEntry(MAX);
    }

    @Override
    public Long[] createKeyArray(int length) {
        return new Long[length];
    }

    @Override
    public Object[] createValueArray(int length) {
        return new Double[length];
    }

    @Override
    public SampleElements<Map.Entry<Long, Object>> samples() {
        return new SampleElements<>(
                makeEntry(3L),
                makeEntry(4L),
                makeEntry(5L),
                makeEntry(6L),                
                makeEntry(7L)
        );
    }

    @Override
    public Map.Entry<Long, Object>[] createArray(int length) {
        return new Map.Entry[length];
    }

    @Override
    public Iterable<Map.Entry<Long, Object>> order(List<Map.Entry<Long, Object>> insertionOrder) {
        return insertionOrder;
    }

    private Map.Entry<Long, Object> makeEntry(Long key) {
        return Helpers.mapEntry(key, key.doubleValue());
    }

    
}
