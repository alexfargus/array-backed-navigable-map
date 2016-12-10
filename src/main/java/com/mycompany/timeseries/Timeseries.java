/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import com.mycompany.timeseries.data.ArrayListData;
import com.mycompany.timeseries.data.DataView;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.stream.Stream;

/**
 *
 * @author alex
 */
public class Timeseries extends AbstractTimeseries implements NavigableMap<Long, Object> {

    private final boolean ascending;

    public Timeseries() {
        super(new ArrayListData());
        ascending = true;
    }

    protected Timeseries(Data data, Boolean ascending) {
        super(data);
        this.ascending = ascending;
    }

    @Override
    public Stream<Entry<Long, Object>> entryStream() {
        return data.entryStream(firstKey(), ascending);
    }

    @Override
    public Stream<Entry<Long, Object>> descendingEntryStream() {
        return data.entryStream(lastKey(), !ascending);
    }

    @Override
    public NavigableMap<Long, Object> descendingMap() {
        return new Timeseries(data, !ascending);
    }

    @Override
    public NavigableMap<Long, Object> subMap(Long fromKey, boolean fromInclusive, Long toKey, boolean toInclusive) {
        final Data view = new DataView(data, fromKey, fromInclusive, toKey, toInclusive);
        return new Timeseries(view, ascending);
    }

    @Override
    public Comparator<? super Long> comparator() {
        Comparator<? super Long> comparator = Long::compareTo;
        if (ascending) {
            return comparator;
        } else {
            return comparator.reversed();
        }
    }

    @Override
    public Long firstKey() {
        return data.firstKey(ascending);
    }

    @Override
    public Long lastKey() {
        return data.lastKey(ascending);
    }

    @Override
    public String toString() {
        return "Timeseries{" + "ascending=" + ascending + '}';
    }

}
