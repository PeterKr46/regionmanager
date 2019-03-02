package eu.saltyscout.regionmanager.region;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Peter on 04.04.2017.
 */
public interface Region extends MemberContainer, FlagContainer, OwnerContainer {
    /**
     * @return the name of this owner (unique per world)
     */
    String getName();
    
    /**
     * Attempts to rename this owner
     * @param name the new name
     * @return true, if successful.
     */
    boolean rename(@Nonnull String name);
    
    /**
     * @return the world this owner is located in.
     */
    World getWorld();
    
    /**
     * @return the RegionType of this BasicRegion
     */
    RegionType getType();

    /**
     * Checks if the BasicRegion contains a specific Location.
     * @param loc the Location to check for.
     * @return true if this location is inside of the bounds and in the correct World.
     */
    boolean contains(@Nonnull Location loc);

    /**
     * Checks if the BasicRegion contains a specific set of coordinates.
     * @param world (optional) the World to check in
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @return true if these coordinates are inside of the bounds.
     */
    boolean contains(@Nullable World world, float x, float y, float z);

    /**
     * Checks if the BasicRegion contains a specific set of coordinates.
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @return true if these coordinates are inside of the bounds.
     */
    boolean contains(float x, float y, float z);

    /**
     * @return the priority of this owner. Higher = more important
     */
    int getPriority();

    /**
     * Sets the priority of this owner. Higher = more important
     * @param priority the new priority
     */
    void setPriority(int priority);

    /**
     * Saves all data required for deserialization to a {@link ConfigurationSection}.
     * @param sec the ConfigurationSection to save to.
     */
    ConfigurationSection save(@Nonnull ConfigurationSection sec);

    /**
     * Loads data from a {@link ConfigurationSection}.
     * @param sec the ConfigurationSection to load from.
     */
    void load(@Nonnull ConfigurationSection sec);

}
