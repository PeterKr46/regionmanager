package eu.saltyscout.regionmanager.flag.type;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Peter on 16.10.2016.
 */
public class LocationFlag extends Flag<Location> {

    public LocationFlag(Object value) {
        super(String.valueOf(value));
    }

    @Override
    public Location parse(@Nonnull String str) {
        try {
            String[] sp = str.split("( )*,( )*");
            return new Location(
                    Bukkit.getWorld(sp[0]),
                    Integer.parseInt(sp[1]),
                    Integer.parseInt(sp[2]),
                    Integer.parseInt(sp[3]));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(super.toStringFormat(),
                getValue() == null ? "none,0,0,0" :
                        (getValue().getWorld() != null ? getValue().getWorld().getName() : "null") + ", " +
                                getValue().getBlockX() + ", " +
                                getValue().getBlockY() + ", " +
                                getValue().getBlockZ());
    }

    @Override
    public String getTypeDescription() {
        return "Location";
    }

    @Override
    public Flag<Location> clone() {
        return new LocationFlag(value == null ? null : value.clone());
    }

    @Override
    public String toChatString() {
        return getValue() == null ? "null" :
                ChatColor.GREEN + "(" +
                        ChatColor.YELLOW + (getValue().getWorld() != null ? getValue().getWorld().getName() : "null") + ChatColor.GREEN + ", " +
                        ChatColor.GOLD + getValue().getX() + ChatColor.GREEN + ", " +
                        ChatColor.GOLD + getValue().getY() + ChatColor.GREEN + ", " +
                        ChatColor.GOLD + getValue().getZ() +
                        ChatColor.GREEN + ")";
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        Location value = this.getValue() == null ? new Location(null, 0, 0, 0) : this.getValue();
        map.put("world", value.getWorld() == null ? null : value.getWorld().getName());
        map.put("x", value.getBlockX() + 0.5f);
        map.put("y", value.getBlockY() + 0.5f);
        map.put("z", value.getBlockZ() + 0.5f);
        if (value.getPitch() != 0) map.put("pitch", value.getPitch());
        if (value.getYaw() != 0) map.put("yaw", value.getYaw());
        return map;
    }

    @Override
    public boolean deserialize(Map<String, Object> serialized) {
        super.deserialize(serialized);
        if (!serialized.containsKey("world") || !serialized.containsKey("z") || !serialized.containsKey("y") || !serialized.containsKey("z"))
            return false;
        Location value = new Location(
                Bukkit.getWorld(String.valueOf(serialized.get("world"))),
                (Double) serialized.get("x"),
                (Double) serialized.get("y"),
                (Double) serialized.get("z")
        );
        if (serialized.containsKey("pitch")) {
            value.setPitch((float) ((double) serialized.get("pitch")));
        }
        if (serialized.containsKey("yaw")) {
            value.setPitch((float) ((double) serialized.get("yaw")));
        }
        setValue(value);

        return true;
    }

    @Override
    public Location getValue() {
        return value.clone();
    }
}
