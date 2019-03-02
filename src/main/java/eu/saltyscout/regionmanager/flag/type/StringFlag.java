package eu.saltyscout.regionmanager.flag.type;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Peter on 16.10.2016.
 */
public class StringFlag extends Flag<Object> {

    public StringFlag(Object value) {
        super(String.valueOf(value));
    }

    @Override
    public String parse(@Nonnull String str) {
        try {
            return String.valueOf(str);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getValue() {
        return String.valueOf(super.getValue());
    }

    @Override
    public String toString() {
        return String.format(super.toStringFormat(), String.valueOf(getValue()));
    }

    @Override
    public String getTypeDescription() {
        return "Text";
    }

    @Override
    public Flag<Object> clone() {
        return new StringFlag(getValue());
    }

    @Override
    public String toChatString() {
        return ChatColor.YELLOW + "'" + String.valueOf(getValue()) + "'";
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("string", getValue());
        return map;
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        if (serialized.containsKey("string")) {
            setValue(serialized.get("string"));
            return true;
        }
        return true;
    }
}
