package eu.saltyscout.regionmanager.event;

import eu.saltyscout.regionmanager.region.Region;
import org.bukkit.event.HandlerList;


/**
 * This type of Event is called when a BasicRegion is created by a player.
 */
public class RegionCreateEvent extends RegionEvent {
    private static HandlerList handlers = new HandlerList();
    
    public RegionCreateEvent(Region region) {
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
