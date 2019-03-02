package eu.saltyscout.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Peter on 20-Nov-16.
 */
public class PlayerNotification {

    public synchronized static void sendSubtitle(Player player, String sub) {
        try {
            new PlayerNotification(null, sub).send(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static void sendSubtitle(Player player, String sub, int stay) {
        try {
            new PlayerNotification(null, sub).setStay(stay).send(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static void sendSubtitle(Player player, String sub, int fadeIn, int stay, int fadeOut) {
        try {
            new PlayerNotification(null, sub).setStay(stay).setFadeIn(fadeIn).setFadeOut(fadeOut).send(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static void sendSubtitle(Player[] players, String sub, int stay) {
        try {
            PlayerNotification notification = new PlayerNotification(null, sub).setStay(stay);
            Arrays.stream(players).forEach(player -> {
                try {
                    notification.send(player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String title, sub;
    private int fadeIn = 2, fadeOut = 2, stay = 20;
    private Class nmsCraftPlayer, nmsPlayerConnection, nmsTitlePacket, nmsEnumTitleAction, nmsChatComponent, nmsChatSerializer , nmsEntityPlayer, nmsPacket;

    public PlayerNotification(String title, String sub) throws Exception {
        this.title = title;
        this.sub = sub;
        nmsCraftPlayer = getCBClass("entity.CraftPlayer");
        nmsPlayerConnection = getNMSClass("PlayerConnection");
        nmsTitlePacket = getNMSClass("PacketPlayOutTitle");
        nmsEnumTitleAction = nmsTitlePacket.getDeclaredClasses()[0];
        nmsChatComponent = getNMSClass("IChatBaseComponent");
        nmsChatSerializer = nmsChatComponent.getDeclaredClasses()[0];
        nmsEntityPlayer = getNMSClass("EntityPlayer");
        nmsPacket = getNMSClass("Packet");
    }

    public PlayerNotification setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public PlayerNotification setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public PlayerNotification setStay(int stay) {
        this.stay = stay;
        int half = (stay+1)/2;
        this.fadeIn = Math.min(half, fadeIn);
        this.fadeOut = Math.min(half, fadeOut);
        return this;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public int getStay() {
        return stay;
    }

    public void sendSafe(Player player) {
        try {
            send(player);
        } catch (Exception e) {
            e.printStackTrace();
            if(title != null) player.sendMessage(title);
            if(sub != null) player.sendMessage(sub);
        }
    }

    public void send(Player player) throws Exception {
        if(isUseSubtitlesEnabled()) {
            Constructor packetConstructor = nmsTitlePacket.getConstructor(nmsEnumTitleAction, nmsChatComponent, int.class, int.class, int.class);
            Method sendMethod = nmsPlayerConnection.getMethod("sendPacket", nmsPacket);

            Object craftPlayer = nmsCraftPlayer.cast(player);
            Object entityPlayer = nmsCraftPlayer.getMethod("getHandle").invoke(craftPlayer);
            Object playerConnection = nmsEntityPlayer.getField("playerConnection").get(entityPlayer);
            Object titleJSON = nmsChatSerializer.getMethod("b", String.class).invoke(null, "{text: '" + (title == null ? "" : title) + "'}");
            Object titlePacket = packetConstructor.newInstance(Enum.valueOf(nmsEnumTitleAction, "TITLE"), titleJSON, fadeIn, stay, fadeOut);
            sendMethod.invoke(playerConnection, titlePacket);
            if (sub != null) {
                Object subtitleJSON = nmsChatSerializer.getMethod("b", String.class).invoke(null, "{text: '" + sub + "'}");
                Object subtitlePacket = packetConstructor.newInstance(Enum.valueOf(nmsEnumTitleAction, "SUBTITLE"), subtitleJSON, fadeIn, stay, fadeOut);
                sendMethod.invoke(playerConnection, subtitlePacket);
            }
            Object timesPacket = packetConstructor.newInstance(Enum.valueOf(nmsEnumTitleAction, "TIMES"), titleJSON, fadeIn, stay, fadeOut);
            sendMethod.invoke(playerConnection, timesPacket);
        } else {
            if(title != null) {
                player.sendMessage(title);
            }
            if(sub != null) {
                player.sendMessage(sub);
            }
        }
    }

    private static boolean USE_SUBTITLES = true;

    public  static boolean isUseSubtitlesEnabled() {
        return USE_SUBTITLES;
    }

    public static void setUseSubtitles(boolean enabled) {
        USE_SUBTITLES = enabled;
    }

    private Class getNMSClass(String name) throws Exception{
        return Class.forName("net.minecraft.server." + getVersion() + "." + name);
    }

    private Class getCBClass(String name) throws Exception{
        return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + name);
    }
    private static String getVersion() {
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();

        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }
}
