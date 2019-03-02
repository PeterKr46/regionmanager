package eu.saltyscout.regionmanager.region;

import eu.saltyscout.regionmanager.user.AccessToken;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Created by Peter on 04-Apr-17.
 */
public interface MemberContainer {
    
    /**
     * Attempts to add the given AccessToken as user
     * @return true, if successful
     */
    boolean addMember(@Nonnull AccessToken owner);
    
    /**
     * Attempts to remove the given AccessToken as user
     * @return true, if successful
     */
    boolean removeMember(@Nonnull AccessToken owner);
    
    /**
     * <i>This does not imply that the instance is contained in {@link MemberContainer.getMembers()}</i>
     * Attemtps to check if the given AccessToken is a user
     * @param owner the AccessToken to check for
     * @return true, if they are an user.
     */
    boolean isMember(@Nonnull AccessToken owner);
    
    /**
     * @return the total number of Members attached to this container
     */
    int numMembers();
    
    /**
     * Gets all members of this owner
     * @return an immutable set of RegionMembers
     */
    Set<AccessToken> getMembers();
}
