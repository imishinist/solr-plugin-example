package org.imishinist.solr.plugins;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;

import java.io.IOException;
import java.util.Map;

public class MyFloatFunction extends ValueSource {
    protected final ValueSource x, y;
    protected final float a, b, c, d, e, f;

    public MyFloatFunction(ValueSource x,
                           ValueSource y,
                           float a,
                           float b,
                           float c,
                           float d,
                           float e,
                           float f) {
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    protected String name() {
        return "myfloatfunc";
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        final FunctionValues xVals = x.getValues(context, readerContext);
        final FunctionValues yVals = y.getValues(context, readerContext);
        return new FloatDocValues(this) {
            @Override
            public float floatVal(int doc) throws IOException {
                float x = xVals.floatVal(doc);
                float y = yVals.floatVal(doc);
                return a * x * x + b * x * y + c * y * y + d * x + e * y + f;
            }

            @Override
            public String toString(int doc) throws IOException {
                String xd = xVals.toString(doc);
                String yd = yVals.toString(doc);

                return a + "*" + xd + "*" + xd
                        + "+" + b + "*" + xd + "*" + yd
                        + "+" + c + "*" + yd + "*" + yd
                        + "+" + d + "*" + xd
                        + "+" + e + "*" + yd
                        + "+" + f;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (MyFloatFunction.class != o.getClass())
            return false;
        MyFloatFunction other = (MyFloatFunction) o;
        return this.a == other.a
                && this.b == other.b
                && this.c == other.c
                && this.d == other.d
                && this.e == other.e
                && this.f == other.f
                && this.x.equals(other.x)
                && this.y.equals(other.y);
    }

    @Override
    public int hashCode() {
        int h = Float.floatToIntBits(a)
                + Float.floatToIntBits(b)
                + Float.floatToIntBits(c)
                + Float.floatToIntBits(d)
                + Float.floatToIntBits(e)
                + Float.floatToIntBits(f);
        h ^= (h << 13) | (h >>> 20);
        return h + (Float.floatToIntBits(b)) + x.hashCode() + y.hashCode();
    }

    @Override
    public String description() {
        return name() + '(' +
                a + ',' + b + ',' + c +
                d + ',' + e + ',' + f +
                ')';
    }
}
