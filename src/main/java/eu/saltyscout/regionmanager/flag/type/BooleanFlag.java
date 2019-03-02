package eu.saltyscout.regionmanager.flag.type;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Peter on 16.10.2016.
 */
public class BooleanFlag extends Flag<Boolean> {
    public BooleanFlag(Boolean value) {
        super(value);
    }

    @Override
    public Boolean parse(@Nonnull String str) {
        if (str.equalsIgnoreCase("DENY")) {
            return false;
        } else if (str.equalsIgnoreCase("ALLOW")) {
            return true;
        }
        try {
            return Boolean.valueOf(str);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(super.toStringFormat(), String.valueOf(getValue()));
    }

    @Override
    public String getTypeDescription() {
        return "Boolean";
    }

    @Override
    public Flag<Boolean> clone() {
        return new BooleanFlag(getValue());
    }

    @Override
    public String toChatString() {
        return ChatColor.DARK_GREEN + String.valueOf(getValue()).toUpperCase();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("boolean", getValue());
        return map;
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        if (serialized.containsKey("boolean")) {
            setValue((Boolean) serialized.get("boolean"));
            return true;
        }
        return true;
    }
}
