package org.imishinist.solr.plugins;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.FieldType;
import org.apache.solr.search.*;

public class MyQueryParser extends QParser {
    /**
     * Constructor for the QParser
     *
     * @param qstr        The part of the query string specific to this parser
     * @param localParams The set of parameters that are specific to this QParser.  See http://wiki.apache.org/solr/LocalParams
     * @param params      The rest of the {@link SolrParams}
     * @param req         The original {@link SolrQueryRequest}.
     */
    public MyQueryParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        super(qstr, localParams, params, req);
    }

    @Override
    public Query parse() throws SyntaxError {
        // Copy from TermQParserPlugin
        String fname = localParams.get(QueryParsing.F);
        FieldType ft = req.getSchema().getFieldTypeNoEx(fname);
        String val = localParams.get(QueryParsing.V);
        BytesRefBuilder term;
        if (ft != null) {
            if (ft.isPointField()) {
                return ft.getFieldQuery(this, req.getSchema().getField(fname), val);
            } else {
                term = new BytesRefBuilder();
                ft.readableToIndexed(val, term);
            }
        } else {
            term = new BytesRefBuilder();
            term.copyChars(val);
        }
        return new TermQuery(new Term(fname, term.get()));
    }
}
