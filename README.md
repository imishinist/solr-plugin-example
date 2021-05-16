# Solr plugin examples

## ValueSourceParser

独自Functionを作る時に使う。

ref: https://cwiki.apache.org/confluence/display/SOLR/SolrPlugins#SolrPlugins-ValueSourceParser

実際の例:
```xml:solrconfig.xml
<valueSourceParser name="myfloatfunc"
class="org.imishinist.solr.plugins.MyValueSourceParser" />
<valueSourceParser name="step"
class="org.imishinist.solr.plugins.StepFunctionValueSourceParser" />
<valueSourceParser name="dotproduct"
class="org.imishinist.solr.plugins.DotProductFunctionValueSourceParser" />
<valueSourceParser name="rand"
class="org.imishinist.solr.plugins.RandFunctionValueSourceParser" />
```

## QParserPlugin

独自のクエリの投げ方を実装できる。

ref: https://cwiki.apache.org/confluence/display/SOLR/SolrPlugins#SolrPlugins-QParserPlugin

実際の例:
```xml:solrconfig.xml
<queryParser name="myquery" class="org.imishinist.solr.plugins.MyQueryParserPlugin" />
```

## SearchComponent

独自の検索機能を作りたい時に使う。  
例えば、LTR, MoreLikeThis, Facetのようなもの。

ref: https://cwiki.apache.org/confluence/display/SOLR/SolrPlugins#SolrPlugins-SearchComponent

実際の例:
```xml:solrconfig.xml
<searchComponent name="mysearch" class="org.imishinist.solr.plugins.MySearchComponent" />

<requestHandler name="/select" class="solr.SearchHandler">
    <!-- specify components -->
    <arr name="components">
      <str>mysearch</str>
    </arr>

    <!-- append components -->
    <arr name="last-components">
      <str>mysearch</str>
    </arr>
</requestHandler>
```

上記の内容を、requestHandlerの中に入れれば動く。(ただしRequestHandlerはsolr.SearchHandlerを使用している想定）

## RequestHandler

リクエストの投げ方を自分の好きにしたい時に使う。

ref: https://cwiki.apache.org/confluence/display/SOLR/SolrPlugins#SolrPlugins-SolrRequestHandler

実際の例:
```xml:solrconfig.xml
<requestHandler name="/noop" class="org.imishinist.solr.plugins.NoRequestHandler">
</requestHandler>
```

# License

MIT

# Author

imishinist (Taisuke Miyazaki)
