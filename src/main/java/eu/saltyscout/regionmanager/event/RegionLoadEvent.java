package eu.saltyscout.regionmanager.event;

import eu.saltyscout.regionmanager.region.Region;
import org.bukkit.event.HandlerList;


/**
 * This type of Event is called when a owner is loaded by the RegionRegistry.
 */
public class RegionLoadEvent extends RegionEvent {
    private static HandlerList handlers = new HandlerList();
    
    public RegionLoadEvent(Region region) {
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
