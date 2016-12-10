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
import junit.framework.TestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TimeseriesTest.GuavaTests.class, TimeseriesTest.AdditionalTests.class})
public class TimeseriesTest {

    public static class AdditionalTests {

        @Test
        public void testFoo() {

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
                            MapFeature.ALLOWS_NULL_VALUES,
                            CollectionFeature.SUPPORTS_ITERATOR_REMOVE
                    )
                    .createTestSuite();
        }

    }

}
