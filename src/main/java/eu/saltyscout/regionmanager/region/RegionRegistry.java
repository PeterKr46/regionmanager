package eu.saltyscout.regionmanager.region;

import eu.saltyscout.regionmanager.flag.Flag;
import eu.saltyscout.regionmanager.flag.type.BooleanFlag;
import eu.saltyscout.regionmanager.user.AccessToken;
import eu.saltyscout.utils.PriorityQueue;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;

public abstract class RegionRegistry {

    protected static RegionRegistry instance = null;

    protected abstract void _initialize(File dataFolder) throws Exception;

    public static void initialize(File dataFolder) throws Exception {
        instance._initialize(dataFolder);
    }
    
    /**
     * Re-registers a region under a new name. ONLY to be called by the BasicRegion itself.
     */
    public static void reregister(Region region, String newName) {
        instance._reregister(region, newName);
    }

    protected abstract void _reregister(Region region, String newName);
    
    /**
     * Checks a flag result for a given Player at a given Location, depending on some default values.
     * @param player the Player to check
     * @param location the Location to check
     * @param flag the Flag to check
     * @return the result
     */
    public synchronized static boolean check(Player player, Location location, String flag, boolean positiveValue, boolean regionDefaultValue, boolean memberDefaultValue, boolean membersIgnoreFlag, boolean nonMemberDefaultValue, boolean nonMembersIgnoreFlag) {
        return instance._check(player, location, flag, positiveValue, regionDefaultValue, memberDefaultValue, membersIgnoreFlag, nonMemberDefaultValue, nonMembersIgnoreFlag);
    }

    public synchronized static boolean check(Player player, Location location, String flag, boolean positiveValue, boolean regionDefaultValue, boolean memberDefaultValue, boolean membersIgnoreFlag) {
        return instance._check(player, location, flag, positiveValue, regionDefaultValue, memberDefaultValue, membersIgnoreFlag,  false, true);
    }

    /**
     * Gets the highest defined value for a given flag in a given set of regions
     * @param flag the flag to check for
     * @param queue the regions to work with
     * @param clazz the flag type to work with
     * @param <T> the return type
     * @return a value of type T, or null if none found
     */
    public synchronized static <T> T getFlag(@Nonnull String flag, @Nonnull PriorityQueue queue, @Nonnull Class<? extends Flag<T>> clazz) {
        return instance._getFlag(flag, queue, clazz);
    }
    /**
     * Gets the highest defined value for a given flag in a given set of regions
     * @param flag the flag to check for
     * @param queue the regions to work with
     * @param defValue the default value to work with
     * @param clazz the flag type to work with
     * @param <T> the return type
     * @return a value of type T, or defValue if none found
     */
    public synchronized static <T> T getFlag(@Nonnull String flag, @Nonnull PriorityQueue queue, @Nonnull Class<? extends Flag<T>> clazz, T defValue) {
        return instance._getFlag(flag, queue, clazz, defValue);
    }

    protected abstract <T> T _getFlag(@Nonnull String flag, @Nonnull PriorityQueue queue, @Nonnull Class<? extends Flag<T>> clazz, T defValue);

    protected abstract <T> T _getFlag(@Nonnull String flag, @Nonnull PriorityQueue queue, @Nonnull Class<? extends Flag<T>> clazz);

    protected abstract boolean _check(Player player, Location location, String flag, boolean positiveValue, boolean regionDefaultValue, boolean memberDefaultValue, boolean membersIgnoreFlag, boolean nonMemberDefaultValue, boolean nonMembersIgnoreFlag);
    
    public synchronized static boolean isSet(Location location, String flag) {
        return instance._isSet(location, flag);
    }
    
    protected abstract boolean _isSet(Location location, String flag);
    
    protected abstract ShapedRegion _create(com.sk89q.worldedit.regions.Region selection, AccessToken owner, String name) throws Exception;


    public static ShapedRegion create(com.sk89q.worldedit.regions.Region selection, AccessToken owner, String name) throws Exception {
        return instance._create(selection, owner, name);
    }

    protected abstract void _reload();

    public static void reload() {
        instance._reload();
    }


    protected abstract boolean _destroyRegion(String name);

    public static boolean destroyRegion(String name) {
        return instance._destroyRegion(name);
    }

    protected abstract void _save() throws Exception;

    public static void save() throws Exception {
        instance._save();
    }

    protected abstract Region _getRegion(String name);

    public static Region getRegion(String name) {
        return instance._getRegion(name);
    }

    public static boolean exists(String name) {
        return (getRegion(name) != null);
    }

    protected abstract PriorityQueue _getRegions(World world);

    public static PriorityQueue getRegions(World world) {
        return instance._getRegions(world);
    }

    protected abstract Region _getOneRegionAtLocation(Location loc);

    public static Region getOneRegionAtLocation(Location loc) {
        return instance._getOneRegionAtLocation(loc);
    }

    protected abstract PriorityQueue _getRegionsAtLocation(Location loc);

    public static PriorityQueue getRegionsAtLocation(Location loc) {
        return instance._getRegionsAtLocation(loc);
    }

    protected abstract PriorityQueue _getRegions();

    public static PriorityQueue getRegions() {
        return instance._getRegions();
    }

    public static boolean getBooleanDecision(String flag, boolean defValue, Location location) {
        Region region = RegionRegistry.getHighestPriorityRegionWithFlagAt(location, flag);
        return region == null ? defValue : region.getFlag(flag, BooleanFlag.class);
    }

    public static Region getHighestPriorityRegionAt(Location location) {
        Region region = null;
        PriorityQueue regions = RegionRegistry.getRegions();
        Region current;
        while ((current = regions.dequeue()) != null) {
            if (current.contains(location)) {
                region = current;
            }
        }
        return region;
    }

    public static Region getHighestPriorityRegionWithFlagAt(Location location, String flag) {
        Region region = null;
        PriorityQueue regions = RegionRegistry.getRegions();
        Region current;
        while ((current = regions.dequeue()) != null) {
            if (current.contains(location) && current.hasFlag(flag)) {
                region = current;
            }
        }
        return region;
    }
}