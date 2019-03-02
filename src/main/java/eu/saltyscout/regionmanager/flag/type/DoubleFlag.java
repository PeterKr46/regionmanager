package eu.saltyscout.regionmanager.flag.type;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Peter on 16.10.2016.
 */
public class DoubleFlag extends Flag<Double> {
    protected DoubleFlag(Double value) {
        super(value);
    }


    @Override
    public Double parse(@Nonnull String str) {
        try {
            return Double.valueOf(str);
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
        return "Double";
    }


    @Override
    public Flag<Double> clone() {
        return new DoubleFlag(getValue());
    }

    @Override
    public String toChatString() {
        return ChatColor.GOLD + String.valueOf(getValue());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("double", getValue());
        return map;
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        if (serialized.containsKey("double")) {
            setValue((Double) serialized.get("double"));
            return true;
        }
        return true;
    }
}
