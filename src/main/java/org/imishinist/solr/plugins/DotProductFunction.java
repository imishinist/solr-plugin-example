package org.imishinist.solr.plugins;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class DotProductFunction extends ValueSource {
    protected final ValueSource[] value1;
    protected final ValueSource[] value2;

    public DotProductFunction(ValueSource[] value1, ValueSource[] value2) {
        assert(value1.length == value2.length);
        this.value1 = value1;
        this.value2 = value2;
    }

    protected String name() {
        return "dotproduct";
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        int length = value1.length;
        final FunctionValues[] fs1 = new FunctionValues[length];
        final FunctionValues[] fs2 = new FunctionValues[length];

        for (int i = 0; i < length; i++) {
            fs1[i] = value1[i].getValues(context, readerContext);
            fs2[i] = value2[i].getValues(context, readerContext);
        }

        return new FloatDocValues(this) {
            @Override
            public float floatVal(int doc) throws IOException {
                float sum = 0.0F;
                for (int i = 0; i < length; i++) {
                    float v1 = fs1[i].floatVal(doc);
                    float v2 = fs2[i].floatVal(doc);
                    sum += (v1 * v2);
                }
                return sum;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (DotProductFunction.class != o.getClass())
            return false;

        DotProductFunction other = (DotProductFunction) o;
        return Arrays.equals(this.value1, other.value1) &&
                Arrays.equals(this.value2, other.value2);
    }

    @Override
    public int hashCode() {
        int result = 111;

        result *= 31;
        result += Arrays.hashCode(value1);
        result *= 31;
        result += Arrays.hashCode(value2);

        return result;
    }

    @Override
    public String description() {
        return name() + "(" + Arrays.toString(value1) + " * " + Arrays.toString(value2) +  ")";
    }
}
