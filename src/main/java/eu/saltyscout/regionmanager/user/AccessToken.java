package eu.saltyscout.regionmanager.user;
import javax.annotation.Nullable;

/**
 * Created by Peter on 04.04.2017.
 */
public interface AccessToken {

    /**
     * @return an identifier for this AccessToken
     */
    String getName();

    /**
     * @return a String representing this AccessToken
     */
    String dump();

    /**
     * Checks if this AccessToken is identical to another
     * @param other the other AccessToken
     * @return true if identical
     */
    boolean isEqual(@Nullable AccessToken other);

    /**
     * Checks if this AccessToken inherits from another
     * @param other the other AccessToken
     * @return true if the other is a parent
     */
    boolean inherits(@Nullable AccessToken other);
    
    /**
     * @return a clone of this AccessToken
     */
    AccessToken clone();
    /*
     * Check if this AccessToken is a Member of a MemberAttachable
     * @param attachable the target to check membership of
     * @return true, if this user is a member
     *
    boolean isMember(@Nonnull MemberAttachable attachable);

    /**
     * Check if this AccessToken is an Owner of a OwnerAttachable
     * @param attachable the target to check ownership of
     * @return true, if this user is an owner
     *
    boolean isOwner(@Nonnull OwnerAttachable attachable); */
}
