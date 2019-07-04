package org.intermine.client.live;

import static org.junit.Assert.assertTrue;

import org.intermine.client.core.ServiceFactory;
import org.intermine.client.util.TestUtil;

import org.junit.Test;

public class LiveServiceTest {

    private static final ServiceFactory servicef = new ServiceFactory(TestUtil.getRootUrl());

    @Test
    public void testVersion() {
        int apiVersion = servicef.getQueryService().getAPIVersion();
        assertTrue(apiVersion + " is > 5", apiVersion > 5);
    }

}
