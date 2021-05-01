package org.imishinist.solr.plugins;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.ValueSourceParser;

import java.util.List;

public class DotProductFunctionValueSourceParser extends ValueSourceParser {
    @Override
    public void init(NamedList namedList) {}

    @Override
    public ValueSource parse(FunctionQParser fp) throws SyntaxError {
        List<ValueSource> list = fp.parseValueSourceList();

        int size = list.size();
        if (size % 2 != 0) {
            throw new SyntaxError("should be multiple of 2");
        }

        ValueSource[] v1 = new ValueSource[size / 2];
        ValueSource[] v2 = new ValueSource[size / 2];

        for (int i = 0; i < size / 2; i++) {
            v1[i] = list.get(i * 2);
            v2[i] = list.get(i * 2 + 1);
        }

        return new DotProductFunction(v1, v2);
    }
}
