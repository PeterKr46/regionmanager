package eu.saltyscout.regionmanager.region;

import eu.saltyscout.regionmanager.flag.Flag;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

/**
 * Created by Peter on 04-Apr-17.
 */
public interface FlagContainer {

    /**
     * Gets a String identifier for this container
     * @return a String
     */
    String getName();

    /**
     * Adds a flag to this owner.
     * @param flag the {@link Flag} to set.
     */
    void setFlag(@Nonnull String id, @Nonnull Flag flag);
    
    /**
     * Checks if a flag with the given ID is set for this owner.
     * @param flag the ID of the flag to look for.
     * @return true if a flag by that ID exists.
     */
    boolean hasFlag(@Nonnull String flag);
    
    /**
     * Removes the flag with the given ID, if it exists.
     * @param flag the ID of the flag to remove.
     * @return true if the ID was not null and the flag was set previously.
     */
    Flag clearFlag(@Nonnull String flag);
    
    /**
     * Removes all flags.
     */
    void clearFlags();
    
    /**
     * @return the total number of Flags attached to this container
     */
    int numFlags();

    /**
     * Gets all Flag instances set for this owner
     * @return an immutable set containing immutable Flag instances.
     */
    Map<String,Flag> getSetFlags();

    /**
     * Gets all Flag instances set for this owner
     * @return an immutable set containing immutable Flag instances.
     */
    Set<String> getSetFlagIds();

    /**
     * Gets a specific Flag, if it is set.
     * @param flag the id of the flag to get
     * @return an immutable Flag object, or null if not set.
     */
    Flag getFlag(@Nonnull String flag);

    /**
     * Gets a specific Flag, if it is set
     * @param flag the id of the flag to get
     * @param clazz the class of the flag value
     * @param <T> the type of the flag value, as determined by {@param clazz}
     * @return an object of type T, or null if not set
     */
    <T> T getFlag(@Nonnull String flag, @Nonnull Class<? extends Flag<T>> clazz);

    /**
     * Gets a specific flag, or a default value if it is not set
     * @param flag the id of the flag to get
     * @param clazz the class of the flag value
     * @param defValue the default value to return if the flag is not set
     * @param <T> the type of the flag value, as determined by {@param clazz}
     * @return an object of type T
     */
    <T> T getFlag(@Nonnull String flag, @Nonnull Class<? extends Flag<T>> clazz, @Nonnull T defValue);
    
    
}
