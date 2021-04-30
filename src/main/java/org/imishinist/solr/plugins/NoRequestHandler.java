package org.imishinist.solr.plugins;

import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

public class NoRequestHandler extends RequestHandlerBase {
    @Override
    public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp) throws Exception {
        rsp.add("message", "Hello, World!");
        rsp.add("version", "1.0");
    }

    @Override
    public String getDescription() {
        return "No Operation";
    }
}
