package bone008.bukkit.remotecommand;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * Stripped down version of my Messager util class to fit this simple plugin.
 * 
 * @author Bone008
 */
public class Messager {

	private Messager() {
	}

	public static final String PLUGIN_PREFIX = ChatColor.GRAY + "[RemoteCommand] " + ChatColor.WHITE;

	public static void send(CommandSender s, String msg) {
		send(s, msg, false);
	}

	public static void send(CommandSender s, String msg, boolean error) {
		if (s != null && isPlayerOnline(s)) {
			s.sendMessage(PLUGIN_PREFIX + (error ? ChatColor.RED : "") + msg);
		}
	}

	/**
	 * Returns false if the sender is a player and not online, or true in all other cases.
	 */
	private static boolean isPlayerOnline(CommandSender sender) {
		return !(sender instanceof OfflinePlayer && !((OfflinePlayer) sender).isOnline());
	}
}
