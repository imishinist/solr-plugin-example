package org.imishinist.solr.plugins;

import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.ExtendedDismaxQParser;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;

public class MyQueryParserPlugin extends QParserPlugin {
    public static final String NAME = "myquery";

    @Override
    public QParser createParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        // return new MyQueryParser(qstr, localParams, params, req);
        return new ExtendedDismaxQParser(qstr, localParams, params, req);
    }
}
