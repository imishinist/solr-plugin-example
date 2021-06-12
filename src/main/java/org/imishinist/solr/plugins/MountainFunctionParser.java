package org.imishinist.solr.plugins;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.ValueSourceParser;

public class MountainFunctionParser extends ValueSourceParser {
    @Override
    public ValueSource parse(FunctionQParser fp) throws SyntaxError {
        ValueSource source = fp.parseValueSource();
        double px = fp.parseDouble();
        double a = fp.parseDouble();
        double b = fp.parseDouble();

        return new MountainFunction(source, px, a, b);
    }
}
