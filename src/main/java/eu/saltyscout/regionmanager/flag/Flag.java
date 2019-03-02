package eu.saltyscout.regionmanager.flag;

import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 16.10.2016.
 */
public abstract class Flag<T> implements Cloneable, Listener {
    // Missing price(?), buyable(?), potion-splash, pistons,

    protected T value;

    /**
     * @param value the value this flag will hold.
     */
    public Flag(T value) {
        this.value = value;
    }

    /**
     * This constructor will invoke {@link #parse(String)} to determine the actual value to store.
     * @param value the String to parse the value from.
     */
    public Flag(String value) {
        this.value = parse(value);
    }

    /**
     * Populates the flag with data from the given Map.
     * @param serialized the Map to populate from.
     * @return true if deserialization was successful.
     */
    public boolean deserialize(Map<String, Object> serialized) {
        /*if (serialized.containsKey("id")) {
            id = String.valueOf(serialized.get("id"));
        }*/
        return true;
    }

    /**
     * Serializes the flag as a Map.
     * @return the Map containing all relevant data.
     */
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        //map.put("id", getId());
        return map;
    }

    /**
     * Parses a String (eg. chat input) into a valid value
     * @param str the String to parse from.
     * @return the value, or null if the format was invalid.
     */
    public abstract T parse(String str);

    /**
     * Subclasses must provide an override of toString(), using {@link #toStringFormat()}.
     */
    @Override
    public abstract String toString();

    /**
     * @return a format String that all implementations should use for their toString() methods.
     */
    protected String toStringFormat() {
        try {
            return "Flag<" + getClass().getField("value").getType() + ">(%s)";
        } catch (NoSuchFieldException e) {
            return "Flag<?>(%s)";
        }
    }

    /**
     * @return A chat-friendly Type Descriptor for the flag.
     */
    public abstract String getTypeDescription();

    /**
     * Provides a deep clone of this flag.
     * @return the clone instance.
     */
    @Override
    public abstract Flag<T> clone();

    /**
     * Returns the ID that this instance is mapped with. The ID is semi-final (can only be changed if a serialization is performed).
     * @return the ID as String.
     *
    public final String getId() {
        return id;
    }*/

    /**
     * Returns the value held in this flag. If T is non-primitive, cloning is recommended.
     * @return the value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Transforms the flag (value only) into a chat-friendly, colorized String.
     * @return the String.
     */
    public abstract String toChatString();

    /**
     * Sets the value held in this flag. If T is non-primitive, cloning is recommended.
     */
    public void setValue(T value) {
        this.value = value;
    }

}