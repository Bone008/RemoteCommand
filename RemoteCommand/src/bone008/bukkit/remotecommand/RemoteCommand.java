package bone008.bukkit.remotecommand;


import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class RemoteCommand extends JavaPlugin implements Listener {
	public static final Logger log = Logger.getLogger("Minecraft");

	private static final String PERMISSION_NODE = "remotecommand.use";
	private static final String PERMISSION_NODE_CONSOLE = "remotecommand.console";
	private static final String PERMISSION_NODE_PROTECTED = "remotecommand.protected";


	@Override
	public void onEnable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 2)
			return false;
		
		if(!sender.hasPermission(PERMISSION_NODE)) {
			Messager.send(sender, "You don't have permission to do this!", true);
			return true;
		}
		
		String asWho = args[0];
		boolean asConsole = asWho.equalsIgnoreCase("console");
		
		if(asConsole && !sender.hasPermission(PERMISSION_NODE_CONSOLE)) {
			Messager.send(sender, "You don't have permission to execute commands as console!", true);
			return true;
		}
		
		CommandSender targetContext;
		if(asConsole)
			targetContext = Bukkit.getConsoleSender();
		else
			targetContext = Bukkit.getPlayer(asWho);
		
		if(targetContext == null) {
			Messager.send(sender, "The given player is not online!", true);
			return true;
		}
		
		if(targetContext.hasPermission(PERMISSION_NODE_PROTECTED) && !sender.hasPermission(PERMISSION_NODE_PROTECTED)) {
			Messager.send(sender, "You don't have permission to execute commands as a protected player!", true);
			return true;
		}
		
		
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			if (i > 1)
				sb.append(' ');
			sb.append(args[i]);
		}
		
		String cmdToPerform = sb.toString();
		
		boolean isCommand = !(targetContext instanceof Player);
		if(cmdToPerform.startsWith("/")) {
			cmdToPerform = cmdToPerform.substring(1);
			isCommand = true;
		}
		
		if(isCommand) {
			if(Bukkit.dispatchCommand(targetContext, cmdToPerform))
				Messager.send(sender, ChatColor.GREEN + "The command was executed as "+targetContext.getName()+"!");
			else
				Messager.send(sender, ChatColor.GOLD + "The command to execute was not found.");
		} else {
			assert (targetContext instanceof Player); // nothing else should be possible here
			((Player) targetContext).chat(cmdToPerform);
		}
		
		return true;
	}
}
