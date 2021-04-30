package org.imishinist.solr.plugins;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.ValueSourceParser;

public class MyValueSourceParser extends ValueSourceParser {
    @Override
    public void init(NamedList namedList) {
    }

    @Override
    public ValueSource parse(FunctionQParser fp) throws SyntaxError {
        ValueSource x = fp.parseValueSource();
        ValueSource y = fp.parseValueSource();
        float a = fp.parseFloat();
        float b = fp.parseFloat();
        float c = fp.parseFloat();
        float d = fp.parseFloat();
        float e = fp.parseFloat();
        float f = fp.parseFloat();
        return new MyFloatFunction(x, y, a, b, c, d, e, f);
    }
}
