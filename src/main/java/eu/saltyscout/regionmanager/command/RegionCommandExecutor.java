package eu.saltyscout.regionmanager.command;

import org.bukkit.command.CommandExecutor;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by Peter on 06.04.2017.
 */
public interface RegionCommandExecutor extends CommandExecutor {
    /**
     * Gets the RegionCommand with the given identifier
     * @param command the identifier of the command
     * @return the responsible RegionCommand object
     */
    @Nullable
    RegionCommand getCommand(@Nullable String command);

    /**
     * Gets all RegionCommands registered in this executor
     * @return an immutable set of RegionCommand instances
     */
    Set<RegionCommand> getCommands();
}
