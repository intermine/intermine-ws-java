package org.intermine.client.live;

import junit.framework.TestCase;
import static org.junit.Assert.assertNotNull;

import org.intermine.client.core.ServiceFactory;
import org.intermine.client.services.ModelService;
import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.Model;
import org.intermine.client.util.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LiveModelServiceTest {

    private static final ServiceFactory servicef = new ServiceFactory(TestUtil.getRootUrl());

    @Test
    public void testGetModel() {
        ModelService service = servicef.getModelService();
        Model model = service.getModel();
        assertNotNull(model);
        ClassDescriptor descriptor = model.getClassDescriptorByName("org.intermine.model.testmodel.Employee");
        assertNotNull(descriptor);
        assertNotNull(descriptor.getAttributeDescriptorByName("fullTime"));
    }

}
