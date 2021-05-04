package org.imishinist.solr.plugins;

import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;

import java.io.IOException;

public class MySearchComponent extends SearchComponent {
    @Override
    public void prepare(ResponseBuilder rb) throws IOException {}

    @Override
    public void process(ResponseBuilder rb) throws IOException {
        rb.rsp.add("mysearch", "Hello World");
    }

    @Override
    public String getDescription() {
        return "put `Hello World` message to `mysearch` field";
    }
}
