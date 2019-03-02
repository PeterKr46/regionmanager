package eu.saltyscout.regionmanager.region;

/**
 * Created by Peter on 04-Apr-17.
 */
public enum RegionType {
    CUBOID(true), CYLINDER(true), ELLIPSOID(true), POLYGONAL(true), CONVEX(true), UNKNOWN(false), GLOBAL(false), BOOLE(true);

    private boolean shaped;
    RegionType(boolean shaped) {
        this.shaped = shaped;
    }

    public boolean isShaped() {
        return shaped;
    }
}
