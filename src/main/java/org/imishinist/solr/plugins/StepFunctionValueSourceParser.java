package org.imishinist.solr.plugins;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.ValueSourceParser;

public class StepFunctionValueSourceParser extends ValueSourceParser {
    @Override
    public void init(NamedList namedList) {
    }

    @Override
    public ValueSource parse(FunctionQParser fp) throws SyntaxError {
        ValueSource source = fp.parseValueSource();
        int num = fp.parseInt();
        final float[] steps = new float[num];
        for (int i = 0; i < num; i++) {
            steps[i] = fp.parseFloat();
        }

        final int[] points = new int[num];
        for (int i = 0; i < num-1; i++) {
            points[i] = fp.parseInt();
        }

        return new StepFunction(source, steps, points);
    }
}
