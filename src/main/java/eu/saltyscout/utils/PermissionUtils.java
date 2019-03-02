package eu.saltyscout.utils;


import eu.saltyscout.regionmanager.user.AccessToken;
import org.bukkit.OfflinePlayer;

import java.util.Set;

/**
 * Created by Peter on 04.04.2017.
 */
public abstract class PermissionUtils {
    protected static PermissionUtils instance;

    protected boolean _implies(String a, String b) {
        String[] aPieces = a.split("\\.");
        String[] bPieces = b.split("\\.");
        boolean result = true;
        // Compare the parts contained in both
        for(int i = 0; result && i < aPieces.length && i < bPieces.length; i++) {
            if ((!aPieces[i].equalsIgnoreCase(bPieces[i])) && // Equivalent
                    (!aPieces[i].equals("*"))) { // A is a wildcard
                result = false;
            }
        }
        // Compare the tails, unless there is already a difference
        if(result && aPieces.length != bPieces.length) {
            // If the tail of the A is a wildcard, they are equivalent.
            if(aPieces.length > bPieces.length) {
                // A is longer than B, this implies that A is more specific than B.
                result = false;
            } else if(aPieces.length < bPieces.length) {
                // A is shorter than B, this implies that A is broader than B.
                if(!aPieces[aPieces.length-1].equals("*")) {
                    // If A ends in a wildcard, then A implies B.
                    result = false;
                }
            }
        }
        return result;
    }

    protected abstract boolean _hasPermission(OfflinePlayer player, String perm);

    protected abstract boolean _hasPermission(String group, String perm);

    protected abstract Set<AccessToken> _getGroups(OfflinePlayer player);

    /**
     * Checks if having Permission a implies having Permission b
     * @param a the owned permission
     * @param b the checked permission
     * @return true if a implies b
     */
    public static boolean implies(String a, String b) {
        return instance._implies(a, b);
    }

    /**
     * Checks if a player has a specific permission
     * @param player the Player to check for
     * @param perm the permission to check
     * @return true if the player has the permission
     */
    public static boolean hasPermission(OfflinePlayer player, String perm) {
        return instance._hasPermission(player, perm);
    }

    /**
     * Checks if a player has a specific permission
     * @param group the Group to check for
     * @param perm the permission to check
     * @return true if the player has the permission
     */
    public static boolean hasPermission(String group, String perm) {
        return instance._hasPermission(group, perm);
    }

    /**
     * Gets all known groups a player is categorized in as AccessTokens
     * @param player the player whose groups to get
     * @return an immutable set of AccessTokens.
     */
    public static Set<AccessToken> getGroups(OfflinePlayer player) {
        return instance._getGroups(player);
    }

}
