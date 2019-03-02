package eu.saltyscout.regionmanager.user;

import javax.annotation.Nonnull;

/**
 * Created by Peter on 04.04.2017.
 */
public abstract class AccessTokenFactory {

    protected static final AccessToken SERVER = new AccessToken() {
        @Override
        public String getName() {
            return "- none -";
        }

        @Override
        public String dump() {
            return "_SERVER";
        }

        @Override
        public boolean isEqual(AccessToken other) {
            return other == this;
        }

        @Override
        public boolean inherits(AccessToken other) {
            return other == this;
        }
    
        @Override
        public AccessToken clone() {
            throw new UnsupportedOperationException();
        }
    
    };

    protected static AccessTokenFactory instance;

    public static AccessToken getServerMember() {
        return instance._getServerMember();
    }

    public synchronized static AccessToken wrap(@Nonnull Object o) {
        return instance._wrap(o);
    }

    /**
     * @return The SERVER Member/default Member.
     */
    protected abstract AccessToken _getServerMember();

    /**
     * Wraps an object as a valid AccessToken instance
     * @param o the object to wrap
     * @return a AccessToken
     */
    protected abstract AccessToken _wrap(@Nonnull Object o);
}
