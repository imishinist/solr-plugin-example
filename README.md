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
class="org.imishinis.solr.plugins.DotProductFunctionValueSourceParser" />
```

## QParserPlugin

独自のクエリの投げ方を実装できる。

ref: https://cwiki.apache.org/confluence/display/SOLR/SolrPlugins#SolrPlugins-QParserPlugin

実際の例:
```xml:solrconfig.xml
<queryParser name="myquery" class="org.imishinist.solr.plugins.MyQueryParserPlugin" />
```