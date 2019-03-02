package eu.saltyscout.regionmanager.region;

import eu.saltyscout.regionmanager.user.AccessToken;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Created by Peter on 04-Apr-17.
 */
public interface OwnerContainer {
    
    /**
     * Attempts to add the given AccessToken as owner
     * @return true, if successful
     */
    boolean addOwner(@Nonnull AccessToken owner);
    
    /**
     * Attempts to remove the given AccessToken as owner
     * @return true, if successful
     */
    boolean removeOwner(@Nonnull AccessToken owner);
    
    /**
     * <i>This does not imply that the instance is contained in {@link OwnerContainer.getOwners()}</i>
     * Attemtps to check if the given AccessToken is an owner
     * @param owner the AccessToken to check for
     * @return true, if they are an owner.
     */
    boolean isOwner(@Nonnull AccessToken owner);
    
    /**
     * @return the total number of Owners attached to this container
     */
    int numOwners();
    
    /**
     * Gets all owners of this owner
     * @return an immutable set of RegionMembers
     */
    Set<AccessToken> getOwners();
    
    
}
