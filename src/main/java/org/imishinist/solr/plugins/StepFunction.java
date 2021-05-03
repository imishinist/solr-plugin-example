package org.imishinist.solr.plugins;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class StepFunction extends ValueSource {
    protected final ValueSource source;
    protected final float[] steps;
    protected final int[] points;

    public StepFunction(ValueSource source, float[] steps, int[] points) {
        this.source = source;
        this.steps = steps;
        this.points = points;
    }

    protected String name() {
        return "step";
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        final FunctionValues s = source.getValues(context, readerContext);

        return new IntDocValues(this) {
            @Override
            public int intVal(int doc) throws IOException {
                float x = s.floatVal(doc);

                if (x < steps[0] || steps[steps.length - 1] <= x)
                    return 0;
                int i = Arrays.binarySearch(steps, x);
                return points[i];
            }

            @Override
            public String toString(int doc) throws IOException {
                return Float.toString(s.floatVal(doc));
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (StepFunction.class != o.getClass())
            return false;

        StepFunction other = (StepFunction) o;
        return this.source.equals(other.source) &&
                Arrays.equals(this.steps, other.steps);
    }

    @Override
    public int hashCode() {
        int result = 123;
        result *= 31;
        result += Arrays.hashCode(this.steps);
        result *= 31;
        result += Arrays.hashCode(this.points);
        result *= 31;
        result += this.source.hashCode();
        return result;
    }

    @Override
    public String description() {
        return name() + "(steps: " + Arrays.toString(this.steps) + ", points: " + Arrays.toString(this.points) + ")";
    }
}
