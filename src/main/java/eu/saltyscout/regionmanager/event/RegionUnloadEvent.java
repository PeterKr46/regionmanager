package eu.saltyscout.regionmanager.event;

import eu.saltyscout.regionmanager.region.Region;
import org.bukkit.event.HandlerList;


/**
 * This type of Event is called when a reload is performed and a owner is to be unloaded prior to it.
 */
public class RegionUnloadEvent extends RegionEvent {
    private static HandlerList handlers = new HandlerList();

    public RegionUnloadEvent(Region region) {
        super(region);
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
