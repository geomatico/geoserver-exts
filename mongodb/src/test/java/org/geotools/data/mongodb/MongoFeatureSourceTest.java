package org.geotools.data.mongodb;

import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.PropertyIsEqualTo;
import org.opengis.filter.spatial.BBOX;

public abstract class MongoFeatureSourceTest extends MongoTestSupport {

    protected MongoFeatureSourceTest(MongoTestSetup testSetup) {
        super(testSetup);
    }

    public void testBBOXFilter() throws Exception {
      FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
      BBOX f = ff.bbox(ff.property("geometry"),  0.5, 0.5, 1.5, 1.5, "epsg:4326");

      SimpleFeatureSource source = dataStore.getFeatureSource("ft1");

      Query q = new Query("ft1", f);
      assertEquals(1, source.getCount(q));
      assertEquals(new ReferencedEnvelope(1d,1d,1d,1d,null), source.getBounds(q));

      SimpleFeatureCollection features = source.getFeatures(q);
      SimpleFeatureIterator it = features.features();
      try {
          assertTrue(it.hasNext());
          assertFeature(it.next(), 1);
      }
      finally {
          it.close();
      }
    }

    public void testEqualToFilter() throws Exception {
        FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
        PropertyIsEqualTo f = ff.equals(ff.property("stringProperty"), ff.literal("two"));

        SimpleFeatureSource source = dataStore.getFeatureSource("ft1");
        Query q = new Query("ft1", f);
        
        assertEquals(1, source.getCount(q));
        assertEquals(new ReferencedEnvelope(2d,2d,2d,2d,null), source.getBounds(q));

        SimpleFeatureCollection features = source.getFeatures(q);
        SimpleFeatureIterator it = features.features();
        try {
            assertTrue(it.hasNext());
            assertFeature(it.next(), 2);
        }
        finally {
            it.close();
        }
    }
}
