package org.intermine.client.live;
import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.intermine.pathquery.Constraints;
import org.intermine.pathquery.PathQuery;
import org.intermine.template.TemplateQuery;
import org.intermine.client.core.ServiceFactory;
import org.intermine.client.results.Page;
import org.intermine.client.services.QueryService;
import org.intermine.client.services.TemplateService;
import org.intermine.client.template.TemplateParameter;
import org.junit.Before;
import org.junit.Test;

/**
 * These are tests of the examples in the JavaDoc in the various Web-Service client classes.
 * All code examples should be present here in <em>verbatim</em> form, and should run as expected.
 * This test is as important as any other functional test.
 * @author Alex Kalderimis
 *
 */
public class LiveExamplesTest {

    private static final String serviceRootUrl = "https://www.flymine.org/flymine/service";
    private PrintWriter out;
    private StringWriter sw;

    private static final String EXPECTED_TEMPLATE_HEADER =
            "Chromosome.primaryIdentifier\tChromosome.organism.name\t" +
            "Chromosome.locatedFeatures.feature.gene.primaryIdentifier\t" +
            "Chromosome.locatedFeatures.feature.transcripts.primaryIdentifier\t" +
            "Chromosome.locatedFeatures.feature.primaryIdentifier\t" +
            "Chromosome.locatedFeatures.start\t" +
            "Chromosome.locatedFeatures.end\n";

    private static final String EXPECTED_TEMPLATE_BODY =
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0078166\tFBgn0002121:17\t21136\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0078167\tFBgn0002121:16\t21066\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0078168\tFBgn0002121:16\t21066\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0078169\tFBgn0002121:15\t21066\t21200\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0078169\tFBgn0002121:18\t21349\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0306589\tFBgn0002121:17\t21136\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0306590\tFBgn0002121:17\t21136\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0306591\tFBgn0002121:16\t21066\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0306592\tFBgn0002121:14\t20831\t20973\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0306592\tFBgn0002121:16\t21066\t21376\n" +
            "2L\tDrosophila melanogaster\tFBgn0002121\tFBtr0330655\tFBgn0002121:16\t21066\t21376\n";

    private static final String EXPECTED_QUERY_RESULTS =
            "There are 1 results for this query\n" +
            "zen\t1331\tZEN1_DROME\n";

    @Before
    public void setUp() {
        sw = new StringWriter();
        out = new PrintWriter(sw);
    }

    @Test
    public void templateServiceExample1() {
        ServiceFactory serviceFactory = new ServiceFactory(serviceRootUrl);
        TemplateService templateService = serviceFactory.getTemplateService();

        // Refer to the template by its name (displayed in the browser's address bar)
        String templateName = "ChromLocation_GeneTranscriptExon";
        TemplateQuery template = templateService.getTemplate(templateName);

        // You only need to specify the values of the constraints you wish to alter:

        template.replaceConstraint(template.getConstraintForCode("C"),
            Constraints.greaterThanEqualTo("Chromosome.locatedFeatures.start", "20000"));

        Iterator<List<Object>> resultSet = templateService.getRowListIterator(template, new Page(0, 10));

        out.println(StringUtils.join(template.getView(), "\t"));
        while (resultSet.hasNext()) {
            out.println(StringUtils.join(resultSet.next(), "\t"));
        }

        assertEquals(EXPECTED_TEMPLATE_HEADER + EXPECTED_TEMPLATE_BODY, sw.toString());
    }

    @Test
    public void templateServiceExample2() {
        ServiceFactory serviceFactory = new ServiceFactory(serviceRootUrl);
        TemplateService templateService = serviceFactory.getTemplateService();

        // Refer to the template by its name (displayed in the browser's address bar)
        String templateName = "ChromLocation_GeneTranscriptExon";

        // Specify the values for this particular request
        List<TemplateParameter> parameters = new ArrayList<TemplateParameter>();
        parameters.add(new TemplateParameter("Chromosome.organism.name", "=", "*melanogaster", null));
        parameters.add(new TemplateParameter("Chromosome.primaryIdentifier", "=", "2L", null));
        parameters.add(new TemplateParameter("Chromosome.locatedFeatures.start", ">=", "20000", null));
        parameters.add(new TemplateParameter("Chromosome.locatedFeatures.end", "<=", "22000", null));

        Iterator<List<Object>> resultSet = templateService.getRowListIterator(templateName, parameters,
                                              new Page(0, 10));

        // We can't print the header line, unless we know what the view is in advance.
        while (resultSet.hasNext()) {
           out.println(StringUtils.join(resultSet.next(), "\t"));
        }

        assertEquals(EXPECTED_TEMPLATE_BODY, sw.toString());

    }

    @Test
    public void queryServiceExample() {
       ServiceFactory services = new ServiceFactory(serviceRootUrl);
       QueryService queryService = services.getQueryService();

       PathQuery query = new PathQuery(services.getModel());
       query.addViews("Gene.symbol", "Gene.length", "Gene.proteins.primaryIdentifier");
       query.addConstraint(Constraints.lookup("Gene", "zen", "D. melanogaster"));

       //find out how many results there are
       out.printf("There are %d results for this query\n", queryService.getCount(query));

       Iterator<List<Object>> results = queryService.getRowListIterator(query);

       while (results.hasNext()) {
           out.println(StringUtils.join(results.next(), "\t"));
       }

       assertEquals(EXPECTED_QUERY_RESULTS, sw.toString());
    }

}
