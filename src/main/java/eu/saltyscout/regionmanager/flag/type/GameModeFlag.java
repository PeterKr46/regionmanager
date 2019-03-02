package eu.saltyscout.regionmanager.flag.type;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;

import java.util.Map;

/**
 * Created by Peter on 27.11.2016.
 */
public class GameModeFlag extends Flag<GameMode> {
    public GameModeFlag(GameMode value) {
        super(value);
    }

    @Override
    public GameMode parse(String str) {
        return GameMode.valueOf(str.toUpperCase());
    }

    @Override
    public String toString() {
        return String.format(super.toStringFormat(), getValue() == null ? "null" : getValue().toString());
    }

    @Override
    public String getTypeDescription() {
        return "Gamemode";
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("gamemode", String.valueOf(getValue()));
        return map;
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        if (serialized.containsKey("gamemode")) {
            setValue(GameMode.valueOf(String.valueOf(serialized.get("gamemode"))));
            return true;
        }
        return true;
    }
    @Override
    public Flag<GameMode> clone() {
        return new GameModeFlag(getValue());
    }

    @Override
    public String toChatString() {
        return ChatColor.YELLOW + String.valueOf(getValue());
    }
}
