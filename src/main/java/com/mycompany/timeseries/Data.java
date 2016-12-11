/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author alex
 */
public interface Data {
    
    public Stream<Map.Entry<Long, Object>> entryStream(Long fromKey, boolean ascending);
    
    public Long firstKey(boolean ascending);
    
    public Long lastKey(boolean ascending);
    
    public Long upperBound();
    
    public Long lowerBound();

    public int size();

    public boolean isEmpty();

    public boolean containsKey(Long key);

    public boolean containsValue(Object value);

    public Object get(Long key);

    public Object put(Long key, Object value);

    public Object remove(Long key);

    public void putAll(Map<? extends Long, ? extends Object> map);

    public void clear();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);
    
    
}
