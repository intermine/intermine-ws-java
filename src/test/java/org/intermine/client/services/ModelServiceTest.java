package org.intermine.client.services;

import junit.framework.TestCase;

import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.Model;
import org.intermine.client.util.TestUtil;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * Tests functionality of ModelService - client class, implementing easy
 * access to InterMine web service. That's why it tests the web service itself
 * as well.
 * 
 * @author Jakub Kulaviak
 **/
public class ModelServiceTest extends TestCase
{
    public ModelService getModelService() {
        DummyModelService service = TestUtil.getModelService();
        service.setFakeResponse("<model name=\"testmodel\" package=\"org.intermine.model.testmodel\"><class name=\"Broke\" is-interface=\"true\"><attribute name=\"debt\" type=\"int\"/><reference name=\"bank\" referenced-type=\"Bank\" reverse-reference=\"debtors\"/></class><class name=\"Thing\" is-interface=\"true\"></class><class name=\"Employable\" extends=\"Thing\" is-interface=\"true\"><attribute name=\"name\" type=\"java.lang.String\"/></class><class name=\"HasAddress\" is-interface=\"true\"><reference name=\"address\" referenced-type=\"Address\"/></class><class name=\"HasSecretarys\" is-interface=\"true\"><collection name=\"secretarys\" referenced-type=\"Secretary\"/></class><class name=\"Contractor\" extends=\"Employable ImportantPerson\" is-interface=\"false\"><reference name=\"personalAddress\" referenced-type=\"Address\"/><reference name=\"businessAddress\" referenced-type=\"Address\"/><collection name=\"companys\" referenced-type=\"Company\" reverse-reference=\"contractors\"/><collection name=\"oldComs\" referenced-type=\"Company\" reverse-reference=\"oldContracts\"/></class><class name=\"Manager\" extends=\"Employee ImportantPerson\" is-interface=\"false\"><attribute name=\"title\" type=\"java.lang.String\"/></class><class name=\"Employee\" extends=\"Employable HasAddress\" is-interface=\"false\"><attribute name=\"fullTime\" type=\"boolean\"/><attribute name=\"age\" type=\"int\"/><attribute name=\"end\" type=\"java.lang.String\"/><reference name=\"department\" referenced-type=\"Department\" reverse-reference=\"employees\"/><reference name=\"departmentThatRejectedMe\" referenced-type=\"Department\" reverse-reference=\"rejectedEmployee\"/><collection name=\"simpleObjects\" referenced-type=\"SimpleObject\" reverse-reference=\"employee\"/></class><class name=\"Department\" extends=\"RandomInterface\" is-interface=\"false\"><attribute name=\"name\" type=\"java.lang.String\"/><reference name=\"company\" referenced-type=\"Company\" reverse-reference=\"departments\"/><reference name=\"manager\" referenced-type=\"Manager\"/><collection name=\"employees\" referenced-type=\"Employee\" reverse-reference=\"department\"/><collection name=\"rejectedEmployee\" referenced-type=\"Employee\" reverse-reference=\"departmentThatRejectedMe\"/></class><class name=\"Company\" extends=\"RandomInterface HasAddress HasSecretarys\" is-interface=\"true\"><attribute name=\"name\" type=\"java.lang.String\"/><attribute name=\"vatNumber\" type=\"int\"/><reference name=\"CEO\" referenced-type=\"CEO\" reverse-reference=\"company\"/><collection name=\"departments\" referenced-type=\"Department\" reverse-reference=\"company\"/><collection name=\"contractors\" referenced-type=\"Contractor\" reverse-reference=\"companys\"/><collection name=\"oldContracts\" referenced-type=\"Contractor\" reverse-reference=\"oldComs\"/></class><class name=\"Address\" extends=\"Thing\" is-interface=\"false\"><attribute name=\"address\" type=\"java.lang.String\"/></class><class name=\"RandomInterface\" is-interface=\"true\"></class><class name=\"CEO\" extends=\"Manager HasSecretarys\" is-interface=\"false\"><attribute name=\"salary\" type=\"int\"/><reference name=\"company\" referenced-type=\"Company\" reverse-reference=\"CEO\"/></class><class name=\"ImportantPerson\" is-interface=\"true\"><attribute name=\"seniority\" type=\"java.lang.Integer\"/></class><class name=\"Secretary\" is-interface=\"false\"><attribute name=\"name\" type=\"java.lang.String\"/></class><class name=\"Types\" is-interface=\"false\"><attribute name=\"name\" type=\"java.lang.String\"/><attribute name=\"booleanType\" type=\"boolean\"/><attribute name=\"floatType\" type=\"float\"/><attribute name=\"doubleType\" type=\"double\"/><attribute name=\"shortType\" type=\"short\"/><attribute name=\"intType\" type=\"int\"/><attribute name=\"longType\" type=\"long\"/><attribute name=\"booleanObjType\" type=\"java.lang.Boolean\"/><attribute name=\"floatObjType\" type=\"java.lang.Float\"/><attribute name=\"doubleObjType\" type=\"java.lang.Double\"/><attribute name=\"shortObjType\" type=\"java.lang.Short\"/><attribute name=\"intObjType\" type=\"java.lang.Integer\"/><attribute name=\"longObjType\" type=\"java.lang.Long\"/><attribute name=\"bigDecimalObjType\" type=\"java.math.BigDecimal\"/><attribute name=\"dateObjType\" type=\"java.util.Date\"/><attribute name=\"stringObjType\" type=\"java.lang.String\"/></class><class name=\"Bank\" is-interface=\"false\"><attribute name=\"name\" type=\"java.lang.String\"/><collection name=\"debtors\" referenced-type=\"Broke\" reverse-reference=\"bank\"/></class><class name=\"SimpleObject\" is-interface=\"false\" extends=\"java.lang.Object\"><attribute name=\"name\" type=\"java.lang.String\"/><reference name=\"employee\" referenced-type=\"Employee\" reverse-reference=\"simpleObjects\"/></class><class name=\"Range\" is-interface=\"false\"><attribute name=\"rangeStart\" type=\"int\"/><attribute name=\"rangeEnd\" type=\"int\"/><attribute name=\"name\" type=\"java.lang.String\"/><reference name=\"parent\" referenced-type=\"Company\"/></class></model>");
        return service;
    }

    public void testGetModel() {
        ModelService service = getModelService();
        Model model = service.getModel();
        assertNotNull(model);
        ClassDescriptor descriptor = model.getClassDescriptorByName("org.intermine.model.testmodel.Employee");
        assertNotNull(descriptor);
        assertNotNull(descriptor.getAttributeDescriptorByName("fullTime"));
    }
}
