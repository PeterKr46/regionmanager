package eu.saltyscout.regionmanager.flag.type;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Peter on 16.10.2016.
 */
public class IntegerFlag extends Flag<Integer> {
    public IntegerFlag(Integer value) {
        super(value);
    }

    @Override
    public Integer parse(@Nonnull String str) {
        try {
            return Integer.valueOf(str);
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
        return "Integer";
    }

    @Override
    public IntegerFlag clone() {
        return new IntegerFlag(getValue());
    }


    @Override
    public String toChatString() {
        return ChatColor.GOLD + String.valueOf(getValue());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("integer", getValue());
        return map;
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        if (serialized.containsKey("integer")) {
            setValue((Integer) serialized.get("integer"));
            return true;
        }
        return true;
    }
}
