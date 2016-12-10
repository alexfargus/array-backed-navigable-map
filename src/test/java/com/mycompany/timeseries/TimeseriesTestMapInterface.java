/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import com.google.common.collect.testing.SortedMapInterfaceTest;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author alex
 */
public class TimeseriesTestMapInterface extends SortedMapInterfaceTest<Long, Object> {

    public TimeseriesTestMapInterface() {
        super(false, false, true, true, true);
    }

    @Override
    protected SortedMap<Long, Object> makeEmptyMap() throws UnsupportedOperationException {
        return new Timeseries();
    }

    @Override
    protected SortedMap<Long, Object> makePopulatedMap() throws UnsupportedOperationException {
        final SortedMap<Long, Object> map = makeEmptyMap();
        map.put(1L, 1.0);
        map.put(2L, 2.0);
        map.put(3L, 3.0);
        return map;
    }

    @Override
    protected Long getKeyNotInPopulatedMap() throws UnsupportedOperationException {
        return 0L;
    }

    @Override
    protected Object getValueNotInPopulatedMap() throws UnsupportedOperationException {
        return 0.0;
    }

    @Override
    public void testTailMapClearThrough() {
        super.testTailMapClearThrough(); 
    }

    @Override
    public void testTailMapRemoveThrough() {
        super.testTailMapRemoveThrough(); 
    }

    @Override
    public void testTailMapWriteThrough() {
        super.testTailMapWriteThrough(); 
    }

    @Override
    protected SortedMap<Long, Object> makeEitherMap() {
        return super.makeEitherMap(); 
    }

    @Override
    public void testValuesClear() {
        super.testValuesClear(); 
    }

    @Override
    public void testValuesRetainAllNullFromEmpty() {
        super.testValuesRetainAllNullFromEmpty(); 
    }

    @Override
    public void testValuesRetainAll() {
        super.testValuesRetainAll(); 
    }

    @Override
    public void testValuesRemoveAllNullFromEmpty() {
        super.testValuesRemoveAllNullFromEmpty(); 
    }

    @Override
    public void testValuesRemoveAll() {
        super.testValuesRemoveAll(); 
    }

    @Override
    public void testValuesRemoveMissing() {
        super.testValuesRemoveMissing(); 
    }

    @Override
    public void testValuesRemove() {
        super.testValuesRemove(); 
    }

    @Override
    public void testValuesIteratorRemove() {
        super.testValuesIteratorRemove(); 
    }

    @Override
    public void testValues() {
        super.testValues(); 
    }

    @Override
    public void testKeySetRetainAllNullFromEmpty() {
        super.testKeySetRetainAllNullFromEmpty(); 
    }

    @Override
    public void testKeySetRemoveAllNullFromEmpty() {
        super.testKeySetRemoveAllNullFromEmpty(); 
    }

    @Override
    public void testKeySetClear() {
        super.testKeySetClear(); 
    }

    @Override
    public void testKeySetRetainAll() {
        super.testKeySetRetainAll(); 
    }

    @Override
    public void testKeySetRemoveAll() {
        super.testKeySetRemoveAll(); 
    }

    @Override
    public void testKeySetRemove() {
        super.testKeySetRemove(); 
    }

    @Override
    public void testSize() {
        super.testSize(); 
    }

    @Override
    public void testRemoveMissingKey() {
        super.testRemoveMissingKey(); 
    }

    @Override
    public void testRemove() {
        super.testRemove(); 
    }

    @Override
    public void testPutAllExistingKey() {
        super.testPutAllExistingKey(); 
    }

    @Override
    public void testPutAllNewKey() {
        super.testPutAllNewKey(); 
    }

    @Override
    public void testPutNullValueForExistingKey() {
        super.testPutNullValueForExistingKey(); 
    }

    @Override
    public void testPutNullValue() {
        super.testPutNullValue(); 
    }

    @Override
    public void testPutNullKey() {
        super.testPutNullKey(); 
    }

    @Override
    public void testPutExistingKey() {
        super.testPutExistingKey(); 
    }

    @Override
    public void testPutNewKey() {
        super.testPutNewKey(); 
    }

    @Override
    public void testHashCodeForEmptyMap() {
        super.testHashCodeForEmptyMap(); 
    }

    @Override
    public void testHashCode() {
        super.testHashCode(); 
    }

    @Override
    public void testGetNull() {
        super.testGetNull(); 
    }

    @Override
    public void testGetForEmptyMap() {
        super.testGetForEmptyMap(); 
    }

    @Override
    public void testGet() {
        super.testGet(); 
    }

    @Override
    public void testEqualsForEmptyMap() {
        super.testEqualsForEmptyMap(); 
    }

    @Override
    public void testEqualsForSmallerMap() {
        super.testEqualsForSmallerMap(); 
    }

    @Override
    public void testEqualsForLargerMap() {
        super.testEqualsForLargerMap(); 
    }

    @Override
    public void testEqualsForEqualMap() {
        super.testEqualsForEqualMap(); 
    }

    @Override
    public void testEntrySetSetValueSameValue() {
        super.testEntrySetSetValueSameValue(); 
    }

    @Override
    public void testEntrySetSetValue() {
        super.testEntrySetSetValue(); 
    }

    @Override
    public void testEntrySetAddAndAddAll() {
        super.testEntrySetAddAndAddAll(); 
    }

    @Override
    public void testEntrySetClear() {
        super.testEntrySetClear(); 
    }

    @Override
    public void testEntrySetRetainAllNullFromEmpty() {
        super.testEntrySetRetainAllNullFromEmpty(); 
    }

    @Override
    public void testEntrySetRetainAll() {
        super.testEntrySetRetainAll(); 
    }

    @Override
    public void testEntrySetRemoveAllNullFromEmpty() {
        super.testEntrySetRemoveAllNullFromEmpty(); 
    }

    @Override
    public void testEntrySetRemoveAll() {
        super.testEntrySetRemoveAll(); 
    }

    @Override
    public void testEntrySetRemoveNullKeyMissing() {
        super.testEntrySetRemoveNullKeyMissing(); 
    }

    @Override
    public void testEntrySetRemoveNullKeyPresent() {
        super.testEntrySetRemoveNullKeyPresent(); 
    }

    @Override
    public void testEntrySetRemoveDifferentValue() {
        super.testEntrySetRemoveDifferentValue(); 
    }

    @Override
    public void testEntrySetRemoveMissingKey() {
        super.testEntrySetRemoveMissingKey(); 
    }

    @Override
    public void testEntrySetRemove() {
        super.testEntrySetRemove(); 
    }

    @Override
    public void testEntrySetIteratorRemove() {
        super.testEntrySetIteratorRemove(); 
    }

    @Override
    public void testEntrySetContainsEntryNullKeyMissing() {
        super.testEntrySetContainsEntryNullKeyMissing(); 
    }

    @Override
    public void testEntrySetContainsEntryNullKeyPresent() {
        super.testEntrySetContainsEntryNullKeyPresent(); 
    }

    @Override
    public void testEntrySetContainsEntryIncompatibleKey() {
        super.testEntrySetContainsEntryIncompatibleKey(); 
    }

    @Override
    public void testEntrySetForEmptyMap() {
        super.testEntrySetForEmptyMap(); 
    }

    @Override
    public void testEntrySet() {
        super.testEntrySet(); 
    }

    @Override
    public void testContainsValue() {
        super.testContainsValue(); 
    }

    @Override
    public void testContainsKey() {
        super.testContainsKey(); 
    }

    @Override
    public void testClear() {
        super.testClear(); 
    }

    @Override
    protected void assertMoreInvariants(Map<Long, Object> map) {
        super.assertMoreInvariants(map); 
    }
    
    
    
}
