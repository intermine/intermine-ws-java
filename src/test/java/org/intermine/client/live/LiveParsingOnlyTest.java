package org.intermine.client.live;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.intermine.client.results.RowResultSet;
import org.intermine.client.results.XMLTableResult;
import org.junit.Test;

public class LiveParsingOnlyTest {

    private String uri = "https://beta.flymine.org/flymine/service/query/results?"
            + "query=%3Cquery+name%3D%22%22+model%3D%22genomic%22+view%3D%22Organism.taxonId+"
            + "Organism.name%22+sortOrder%3D%22Organism.taxonId+asc%22%3E%3C%2Fquery%3E";

    @Test
    public void testXML() throws IOException {

        URL origin = new URL(uri + "&format=xml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(origin.openStream()));

        XMLTableResult results = new XMLTableResult(reader);

        List<List<String>> table = results.getData();

        assertNotNull("The table is not null", table);
        assertTrue("The results are not empty", table.size() > 1);
        boolean found = false;

        List<String> lookingFor = Arrays.asList("7227", "Drosophila melanogaster");
        for(List<String> row: table) {
            found = lookingFor.equals(row);
            if (found)
                break;
        }
        assertTrue("Should include a row of 7227, D. mel", found);
    }

    @Test
    public void testJSON() throws IOException {

        URL origin = new URL(uri + "&format=json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(origin.openStream()));

        RowResultSet results = new RowResultSet(reader, Arrays.asList("taxon", "name"), 8);

        List<Map<String, Object>> table = results.getRowsAsMaps();

        assertNotNull("The table is not null", table);
        assertTrue("The results are not empty", table.size() > 1);
        boolean found = false;

        for(Map<String, Object> row: table) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (entry.getValue().equals("7227")) {
                    found = true;
                }
            }
            if (found)
                break;
        }
        assertTrue("Should include a row of 7227, D. mel", found);
    }

}
