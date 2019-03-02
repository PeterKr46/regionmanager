package eu.saltyscout.regionmanager.flag.type.list;

import eu.saltyscout.regionmanager.flag.Flag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 16.10.2016.
 */
public class StringListFlag extends ListFlag<String> {

    public StringListFlag(Object value) {
        super(String.valueOf(value));
    }

    
    @Override
    public String parseElement(String str) {
        return String.valueOf(str);
    }
    
    @Override
    public String toString(String element) {
        return String.valueOf(element);
    }

    @Override
    public String getTypeDescription() {
        return "Text List";
    }

    @Override
    public Flag<List<String>> clone() {
        List<String> v = new ArrayList<>(getValue().size());
        Collections.copy(getValue(), v);
        return new StringListFlag(v);
    }
}
