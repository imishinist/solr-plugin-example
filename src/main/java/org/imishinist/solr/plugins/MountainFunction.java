package org.imishinist.solr.plugins;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.DoubleDocValues;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MountainFunction extends ValueSource {
    protected final ValueSource source;
    protected final double px;
    protected final double a;
    protected final double b1, b2;

    /**
     * y = a * x + b1 (x <= px) - - - 1
     * y = -a * x + b2 (other)  - - - 2
     *
     * px, a, bからb2を計算すると
     * py = a * px + b1
     * 2にpx, pyを代入して
     * a * px + b1 = -a * px + b2
     * b2 = 2 * px + b1
     *
     * @param source ValueSource
     * @param px double
     * @param a double
     * @param b double
     */
    public MountainFunction(ValueSource source, double px, double a, double b) {
        this.source = source;
        this.px = px;
        this.a = a;
        this.b1 = b;
        this.b2 = 2 * a * px + b;
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        final FunctionValues s = source.getValues(context, readerContext);

        return new DoubleDocValues(this) {
            @Override
            public double doubleVal(int doc) throws IOException {
                double x = s.doubleVal(doc);

                if (x <= px) {
                    return a * x + b1;
                }
                return - a * x + b2;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MountainFunction that = (MountainFunction) o;
        return Double.compare(that.px, px) == 0 && Double.compare(that.a, a) == 0 && Double.compare(that.b1, b1) == 0 && Double.compare(that.b2, b2) == 0 && source.equals(that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, px, a, b1, b2);
    }

    @Override
    public String description() {
        return "mountain function";
    }
}
