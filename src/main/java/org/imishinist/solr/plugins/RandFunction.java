package org.imishinist.solr.plugins;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.DoubleDocValues;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class RandFunction extends ValueSource {
    protected String name() {
        return "rand";
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        Random rand = new Random();
        return new DoubleDocValues(this) {
            @Override
            public double doubleVal(int doc) throws IOException {
                return rand.nextDouble();
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        return RandFunction.class != o.getClass();
    }

    @Override
    public int hashCode() {
        return 123;
    }

    @Override
    public String description() {
        return name() + "()";
    }
}
