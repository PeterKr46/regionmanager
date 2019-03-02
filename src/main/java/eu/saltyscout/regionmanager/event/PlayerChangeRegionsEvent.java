package eu.saltyscout.regionmanager.event;

import eu.saltyscout.utils.PriorityQueue;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Use this Event to trigger actions when a player enters or leaves a region.
 */
public class PlayerChangeRegionsEvent extends PlayerEvent {
    private static HandlerList handlers = new HandlerList();

    private PriorityQueue regionsLeft, regionsEntered, regions;
    public PlayerChangeRegionsEvent(@Nonnull Player who, @Nonnull PriorityQueue regionsLeft, @Nonnull PriorityQueue regionsEntered, @Nullable PriorityQueue unchanged) {
        super(who);
        checkNotNull(regionsLeft);
        checkNotNull(regionsEntered);
        this.regionsEntered = regionsEntered.clone();
        this.regionsLeft = regionsLeft.clone();
        this.regions = unchanged == null ? new PriorityQueue() : unchanged.clone();
    }

    public PriorityQueue getRegionsEntered() {
        return regionsEntered.clone();
    }

    public PriorityQueue getRegionsLeft() {
        return regionsLeft.clone();
    }

    public PriorityQueue getUnchangedRegions() {
        return regions;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
