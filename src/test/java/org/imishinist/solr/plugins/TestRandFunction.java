package org.imishinist.solr.plugins;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.RandomIndexWriter;
import org.apache.lucene.queries.function.FunctionQuery;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.solr.SolrTestCaseJ4;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRandFunction extends SolrTestCaseJ4 {
    static Directory dir;

    @BeforeClass
    public static void beforeClass() throws Exception {
        createIndex();
    }

    public static void createIndex() throws Exception {
        dir = newDirectory();

        IndexWriterConfig iwc = newIndexWriterConfig().setMergePolicy(newLogMergePolicy());
        RandomIndexWriter iw = new RandomIndexWriter(random(), dir, iwc);

        addEmptyDoc(iw);

        iw.close();
    }

    private static void addEmptyDoc(RandomIndexWriter iw) throws Exception {
        Document d = new Document();
        iw.addDocument(d);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        if (null != dir) {
            dir.close();
        }
        dir = null;
    }

    @Test
    public void testRandomFunction() throws Exception {
        IndexReader r = DirectoryReader.open(dir);
        IndexSearcher s = newSearcher(r);

        ValueSource vs = new RandFunction();

        Query q = new FunctionQuery(vs);
        TopDocs td = s.search(q, 100);
        assertTrue("doc result length should be larger than 1", td.scoreDocs.length > 0);
        for (ScoreDoc doc : td.scoreDocs) {
            float score = doc.score;
            assertTrue(0 <= score && score <= 1);
        }

        r.close();
    }
}
