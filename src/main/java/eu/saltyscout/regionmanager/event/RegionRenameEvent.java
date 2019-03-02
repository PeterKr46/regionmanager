package eu.saltyscout.regionmanager.event;

import eu.saltyscout.regionmanager.region.Region;
import org.bukkit.event.HandlerList;


/**
 * This type of Event is called when a BasicRegion is renamed.
 */
public class RegionRenameEvent extends RegionEvent {
    private static HandlerList handlers = new HandlerList();
    private final String newName;
    
    public RegionRenameEvent(Region region, String newName) {
        super(region);
        this.newName = newName;
    }
    
    public String getNewName() {
        return newName;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
