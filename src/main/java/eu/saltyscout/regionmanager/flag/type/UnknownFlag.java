package eu.saltyscout.regionmanager.flag.type;

import eu.saltyscout.regionmanager.flag.Flag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 12.11.2016.
 */
public class UnknownFlag extends Flag<Map<String, Object>> {
    public UnknownFlag(Map<String, Object> value) {
        super(value);
        if (value == null) {
            setValue(new HashMap<>());
        }
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        getValue().putAll(serialized);
        return true;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.putAll(getValue());
        return map;
    }

    @Override
    public Map<String, Object> parse(String str) {
        return null;
    }

    @Override
    public String toString() {
        return String.format(super.toStringFormat(), getValue() == null ? "null" : "Map{size=" + getValue().size() + "}");
    }

    @Override
    public String getTypeDescription() {
        return "Unknown";
    }

    @Override
    public Flag<Map<String, Object>> clone() {
        return new UnknownFlag(getValue());
    }

    @Override
    public String toChatString() {
        return getValue() == null ? "null" : "Map{size=" + getValue().size() + "}";
    }

    @Override
    public Map<String, Object> getValue() {
        return new HashMap<>(value);
    }
}
