package eu.saltyscout.regionmanager.event;

import eu.saltyscout.regionmanager.region.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * This type of Event is called when a Player leaves a Region.
 * This Event should only be used to determine the outcome, not as a trigger.
 * Use {@link PlayerChangeRegionsEvent} for triggers.
 */
public class RegionExitEvent extends RegionEvent implements Cancellable{
    private static HandlerList handlers = new HandlerList();

    private Region region;
    private Player player;
    private boolean cancelled = false;

    public RegionExitEvent(Player who, Region region) {
        super(region);
        this.player = who;
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
