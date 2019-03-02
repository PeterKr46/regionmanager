package eu.saltyscout.regionmanager.region;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.RegionSelector;
import org.bukkit.Location;

import java.util.Iterator;

/**
 * Created by Peter on 04-Apr-17.
 */
public interface ShapedRegion extends Region {

    /**
     * Redefines the shape of this Region as the given WorldEdit region
     * @return a boolean - true if successful.
     */
    boolean redefine(com.sk89q.worldedit.regions.Region shape);

    /**
     * Gets a clone of the shape associated with this region - if there is any
     * @return a new WorldEdit region, or null.
     */
    com.sk89q.worldedit.regions.Region getShape();

    /**
     * Gets the width of this region based upon the bounds given by WorldEdit.
     * @return a positive integer.
     */
    int getWidth();

    /**
     * Gets the height of this region based upon the bounds given by WorldEdit.
     * @return a positive integer.
     */
    int getHeight();

    /**
     * Gets the length of this region based upon the bounds given by WorldEdit.
     * @return a positive integer.
     */
    int getLength();

    /**
     * Gets the minimum point of this region based upon the bounds given by WorldEdit.
     * @return a WorldEdit BlockVector3.
     */
    BlockVector3 getMin();

    /**
     * Gets the maximum point of this region based upon the bounds given by WorldEdit.
     * @return a WorldEdit BlockVector3.
     */
    BlockVector3 getMax();

    /**
     * Gets WorldEdit selector for this region
     * @return a WorldEdit RegionSelector.
     */
    RegionSelector createRegionSelector();

    /**
     * Gets a location relative to the minimum point of this region
     * @return a Bukkit Location.
     */
    Location getRelative(int x, int y, int z);

    /**
     * Gets an iterator for this region based upon the shape given by WorldEdit.
     * @return a WorldEdit BlockVector3 iterator.
     */
    Iterator<BlockVector3> iterator();
}
