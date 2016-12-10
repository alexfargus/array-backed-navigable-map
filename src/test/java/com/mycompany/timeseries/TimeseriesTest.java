/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timeseries;

import com.google.common.collect.testing.*;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.MapFeature;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import junit.framework.TestSuite;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TimeseriesTest.GuavaTests.class, TimeseriesTest.AdditionalTests.class})
public class TimeseriesTest {

    public static class AdditionalTests extends Assertions {

        @Test
        public void descendingSubmap_entrySet_containsAllEntries() {

            final NavigableMap<Long, Object> map = makeMap();
            final Set<Long> expected = new HashSet<>(map.keySet());
            final Set<Long> result = new HashSet<>(map.descendingMap().keySet());

            assertThat(result).isEqualTo(expected);
        }

        protected final NavigableMap<Long, Object> makeMap() {
            final NavigableMap<Long, Object> map = new Timeseries();
            map.putAll(LongStream.range(0L, 10L)
                    .boxed()
                    .collect(Collectors.toMap(k -> k, v -> v.doubleValue())));
            return map;
        }

    }

    /**
     * This class will generate the guava test suite. It needs a public static
     * magic method called {@link GuavaTests#suite()} to do so.
     */
    public static class GuavaTests {

        public static TestSuite suite() {
            return NavigableMapTestSuiteBuilder
                    .using(new TimeseriesTestGenerator())
                    .named("NavigableMap Tests")
                    .withFeatures(CollectionSize.ANY,
                            MapFeature.SUPPORTS_PUT,
                            MapFeature.SUPPORTS_REMOVE,
                            CollectionFeature.SUPPORTS_ITERATOR_REMOVE
                    )
                    .createTestSuite();
        }

    }

}
