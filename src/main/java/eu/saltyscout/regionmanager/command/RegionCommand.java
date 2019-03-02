package eu.saltyscout.regionmanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 17.11.2016.
 */
public abstract class RegionCommand implements TabCompleter {
    abstract boolean onCommand(CommandSender sender, String[] args);

    /**
     * Determines whether or not a command must be run as a player.
     *
     * @return true if this command must be run by a player.
     */
    abstract boolean isPlayerOnly();

    /**
     * Gives the command name.
     *
     * @return the command name.
     */
    abstract String getName();

    /**
     * Gives the permission required to run this command.
     *
     * @return the required permission node as String.
     */
    abstract String getPermission();

    /**
     * Gives a description explaining how to use this command.
     *
     * @return the description as String.
     */
    abstract String getDescription();

    /**
     * Gives a short summary of usage format.
     *
     * @return the description as String.
     */
    abstract String getUsage();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>(0);
    }


    private RegionCommandExecutor executor;

    /**
     * Gives a reference to the parent object/executor
     *
     * @return the parent instance.
     */
    final RegionCommandExecutor getExecutor() {
        return executor;
    }

    /**
     * Set a reference to the parent object/executor
     */
    final void setExecutor(RegionCommandExecutor regionCommandExecutor) {
        this.executor = regionCommandExecutor;
    }
}
