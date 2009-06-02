package org.intermine.webservice.client.services;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.intermine.metadata.Model;
import org.intermine.objectstore.query.ConstraintOp;
import org.intermine.pathquery.Constraint;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.core.ServiceFactory;
import org.intermine.webservice.client.util.TestUtil;

/*
 * Copyright (C) 2002-2009 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * Tests functionality of QueryService - client class
 * 
 * @author Jakub Kulaviak
 **/
public class QueryServiceTest extends TestCase
{
    
    public void testCreatePathQuery() throws IOException {
        ServiceFactory factory = new ServiceFactory(TestUtil.getRootUrl(), "");
        ModelService modelService = factory.getModelService();
        Model model = modelService.getModel();
        PathQuery query = new PathQuery(model);
        query.addView("Employee.name,Employee.age,Employee.end,Employee.fullTime");
        query.addConstraint("Employee.name", new Constraint(ConstraintOp.CONTAINS, "EmployeeA"));
        query.addConstraint("Employee.age", new Constraint(ConstraintOp.GREATER_THAN_EQUALS, new Integer(10)));
        query.addConstraint("Employee.age", new Constraint(ConstraintOp.LESS_THAN, new Integer(60)));
        query.addConstraint("Employee.fullTime", new Constraint(ConstraintOp.EQUALS, true));
        QueryService queryService = factory.getQueryService();
        queryService.setFakeResponses(Collections.singleton("EmployeeA1	10	1	true\nEmployeeA2	20	2	true").iterator());
        List<List<String>> result = queryService.getResult(query, 100);
        TestUtil.checkRow(result.get(0), "EmployeeA1", "10", "1", "true");
        TestUtil.checkRow(result.get(1), "EmployeeA2", "20", "2", "true");
    }

    public void testGetResultPathQuery() throws IOException {
        QueryService queryService = TestUtil.getQueryService();
        queryService.setFakeResponses(Collections.singleton("EmployeeA1\tDepartmentA1\tCompanyA\ttrue\tEmployee Street, AVille\nEmployeeA2\tDepartmentA1\tCompanyA\ttrue\tEmployee Street, AVille\nEmployeeA3\tDepartmentA1\tCompanyA\tfalse\tEmployee Street, AVille").iterator());
        PathQuery query = queryService.createPathQuery(getSimpleXml());
        checkResult(queryService.getResult(query, 10));
    }

    private String getSimpleXml() {
        return "<query name=\"\" model=\"testmodel\" view=\"Employee.name Employee.department.name " 
        		+ "Employee.department.company.name Employee.fullTime Employee.address.address\" sortOrder=\"Employee.name\">" 
        		+ "<node path=\"Employee\" type=\"Employee\">"
        		+ "</node>"
                + "<node path=\"Employee.address\" type=\"Address\">"
                + "</node>"
                + "<node path=\"Employee.address.address\" type=\"String\">"
                + "<constraint op=\"CONTAINS\" value=\"AVille\" description=\"\" identifier=\"\" code=\"A\">"
                + "</constraint>"
                + "</node>"
                + "</query>";
    }
    
    public void testGetResultStringXmlQuery() throws IOException {
        QueryService service = TestUtil.getQueryService();
        service.setFakeResponses(Collections.singleton("EmployeeA1\tDepartmentA1\tCompanyA\ttrue\tEmployee Street, AVille\nEmployeeA2\tDepartmentA1\tCompanyA\ttrue\tEmployee Street, AVille\nEmployeeA3\tDepartmentA1\tCompanyA\tfalse\tEmployee Street, AVille").iterator());
        List<List<String>> result = service.getResult(getSimpleXml(), 10);
        checkResult(result);
    }

    private void checkResult(List<List<String>> result) {
        assertEquals(3, result.size());
        TestUtil.checkRow(result.get(0), "EmployeeA1", "DepartmentA1", "CompanyA", "true", "Employee Street, AVille");
        TestUtil.checkRow(result.get(1), "EmployeeA2", "DepartmentA1", "CompanyA", "true", "Employee Street, AVille");
        TestUtil.checkRow(result.get(2), "EmployeeA3", "DepartmentA1", "CompanyA", "false", "Employee Street, AVille");
    }
}
