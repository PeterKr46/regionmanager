package eu.saltyscout.regionmanager.event;

import eu.saltyscout.regionmanager.region.Region;
import org.bukkit.event.HandlerList;


/**
 * This type of Event is called when a BasicRegion is deleted (not unloaded!) while the plugin is running.
 */
public class RegionDeleteEvent extends RegionEvent {
    private static HandlerList handlers = new HandlerList();
    
    public RegionDeleteEvent(Region region) {
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
