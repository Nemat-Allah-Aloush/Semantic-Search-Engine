package net.sf.extjwnl.dictionary;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Attempt to reproduce http://sourceforge.net/tracker/?func=detail&aid=3202925&group_id=33824&atid=409470
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 * @author wangyeee
 */
public class TestThreads extends MultiThreadedTestCase {

    private static final Log log = LogFactory.getLog(TestThreads.class);

    protected final String properties = "./src/main/resources/net/sf/extjwnl/file_properties.xml";
    protected final String[] list = {"tank", "cooler", "pile", "storm", "perfect", "crown", "computer science",
            "failure", "pleasure", "black", "Great Pyramid", "dictionary", "throw", "exception",
            "boredom", "file", "index", "list", "apple", "orange", "pear", "find", "treasure", "memory", "good",
            "claw", "feet", "cold", "green", "glee"};

    protected final String[] notlist = {"ttank", "ccooler", "ppile", "sstorm", "pperfect", "ccrown", "ccomputer sscience",
            "ffailure", "ppleasure", "bblack", "GGreat PPyramid", "ddictionary", "tthrow", "eexception",
            "bboredom", "ffile", "iindex", "llist", "aapple", "oorange", "ppear", "ffind", "ttreasure", "mmemory", "ggood",
            "cclaw", "ffeet", "ccold", "ggreen", "gglee"};

    public TestThreads(String s) {
        super(s);
    }

    public void testThreadedLookupAllIndexWords() throws FileNotFoundException, JWNLException {
        JWNL.initialize(new FileInputStream(properties));

        List<String> words = new ArrayList<String>(Arrays.asList(list));
        List<String> notwords = new ArrayList<String>(Arrays.asList(notlist));

        TestCaseRunnable t0 = new Lookup(words, true);
        TestCaseRunnable t1 = new Lookup(words, true);
        TestCaseRunnable t2 = new Lookup(notwords, false);

        runTestCaseRunnables(new TestCaseRunnable[]{t0, t1, t2});
    }

    private class Lookup extends TestCaseRunnable {

        private List<String> words;
        private boolean test;

        public Lookup(List<String> words, boolean test) {
            this.words = words;
            this.test = test;
        }

        @Override
        public void runTestCase() throws JWNLException {
            Dictionary dictionary = Dictionary.getInstance();
            //uncomment this to solve the problem,
            //but I think there's a better way to solve it.
//            synchronized (dictionary) {
            for (String word : words) {
                if (!isInterrupted()) {
                    //throws an Exception or just stop at here
                    log.debug("lookup: " + word);
                    IndexWord iws = dictionary.lookupIndexWord(POS.NOUN, word);
                    log.debug("finished: " + word);
                    Assert.assertEquals("Can't find: " + word, null != iws, test);
                } else {
                    break;
                }
            }
//            }
        }
    }
}