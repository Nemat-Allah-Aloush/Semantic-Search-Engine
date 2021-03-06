package net.sf.extjwnl.util.factory;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.dictionary.Dictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for configuration parameters.
 *
 * @author John Didion <jdidion@didion.net>
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class AbstractValueParam implements Param {

    protected final Dictionary dictionary;
    private final Map<String, Param> paramMap = new HashMap<String, Param>();

    protected AbstractValueParam(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    protected AbstractValueParam(Dictionary dictionary, List<Param> params) {
        this(dictionary);
        for (Param param : params) {
            addParam(param);
        }
    }

    public void addParam(Param param) {
        paramMap.put(param.getName(), param);
    }

    /**
     * If the value of this parameter is a class name, and that class is creatable, this method will create
     * an instance of it using this Param parameters.
     */
    public Object create() throws JWNLException {
        try {
            Class clazz = Class.forName(getValue());
            Constructor c = clazz.getConstructor(Dictionary.class, Map.class);
            return c.newInstance(dictionary, paramMap);
        } catch (ClassNotFoundException e) {
            throw new JWNLException("UTILS_EXCEPTION_004", getValue(), e);
        } catch (NoSuchMethodException e) {
            throw new JWNLException("UTILS_EXCEPTION_004", getValue(), e);
        } catch (InstantiationException e) {
            throw new JWNLException("UTILS_EXCEPTION_004", getValue(), e);
        } catch (IllegalAccessException e) {
            throw new JWNLException("UTILS_EXCEPTION_004", getValue(), e);
        } catch (InvocationTargetException e) {
            throw new JWNLException("UTILS_EXCEPTION_004", getValue(), e);
        }
    }
}
