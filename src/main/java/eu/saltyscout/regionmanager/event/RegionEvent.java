package eu.saltyscout.regionmanager.event;

import eu.saltyscout.regionmanager.region.Region;
import org.bukkit.event.Event;

/**
 * Created by Peter on 04-Dec-16.
 */
public abstract class RegionEvent extends Event {
    private final Region region;
    public RegionEvent(Region region) {
        this.region = region;
    }
    
    public Region getRegion() {
        return region;
    }
}
