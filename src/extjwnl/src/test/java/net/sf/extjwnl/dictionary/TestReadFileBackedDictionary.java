package net.sf.extjwnl.dictionary;

import net.sf.extjwnl.JWNLException;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Creates a FileBackedDictionary and creates all the test cases.
 *
 * @author Brett Walenz <bwalenz@users.sourceforge.net>
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class TestReadFileBackedDictionary extends DictionaryReadTester {

    /**
     * Properties location.
     */
    protected String properties = "./src/main/resources/net/sf/extjwnl/file_properties.xml";

    public void initDictionary() throws IOException, JWNLException {
        dictionary = Dictionary.getInstance(new FileInputStream(properties));
    }
}