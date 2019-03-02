package eu.saltyscout.regionmanager.flag.type.list;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Peter on 16.10.2016.
 */
public abstract class ListFlag<T> extends Flag<List<T>> {

    public ListFlag(Object value) {
        super(String.valueOf(value));
    }

    @Override
    public List<T> parse(@Nonnull String str) {
        try {
            while(str.startsWith("[")) {
                str = str.substring(1, str.length() - 1);
            }
            if(str.startsWith("add:")) {
                str = str.substring(4);
                if(getValue().size() > 0) {
                    str = String.join(",", getValue().stream().map(this::toString).collect(Collectors.toList())) + "," + str;
                }
            }
            String[] sp = str.split("( )*,( )*");
            return Arrays.stream(sp).map(this::parseElement).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public abstract T parseElement(String str);
    
    public abstract String toString(T element);
    
    public void add(T element) {
        value.add(element);
    }
    
    public boolean contains(T element) {
        return value.contains(element);
    }

    @Override
    public String toString() {
        return String.format(super.toStringFormat(), getValue() == null ? "" : getValue().stream().map(this::toString).collect(Collectors.joining(",")));
    }

    @Override
    public abstract Flag<List<T>> clone();

    @Override
    public String toChatString() {
        return getValue() == null ? "null" : ChatColor.GREEN + "(" +
                                                String.join(
                                                        ChatColor.GREEN + ", ",
                                                        getValue().stream().map(v -> ChatColor.YELLOW + "'" + toString(v) + "'").collect(Collectors.toSet())) +
                                                ChatColor.GREEN + ")";
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("list", getValue().stream().map(this::toString).collect(Collectors.joining(",")));
        return map;
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        if (serialized.containsKey("list")) {
            setValue(parse(String.valueOf(serialized.get("list"))));
            return true;
        }
        return true;
    }

    @Override
    public List<T> getValue() {
        return Collections.unmodifiableList(value);
    }
}
