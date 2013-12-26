package net.richardsprojects.welcomepro;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerListener implements Listener {
	
  private static WelcomePro plugin;
	
	public ServerListener(WelcomePro plugin) {
		ServerListener.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	private void playerJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		
		//Remove the join message
		e.setJoinMessage("");
		
		//Display the join message for all the players except the one who joined
		for (Player player2 : plugin.getServer().getOnlinePlayers()){
			String playerName = player.getName();
			if (!(player2.getName().equals(playerName)))
			{
				player2.sendMessage(ChatColor.YELLOW + "" + player.getName() + " has joined the game.");
			}
		}
		
		if(player.hasPermission("WelcomePro.welcome")) {
			WelcomePro.playersThatShouldNotSeeChat.add(player);
			player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "" + WelcomePro.serverName + ":");
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.YELLOW + "    Welcome back, " + ChatColor.BLUE + "" + player.getName() + "!");
			player.sendMessage("");
			if(!(WelcomePro.motd_line1.equals(""))) {
				player.sendMessage("    MOTD: " + WelcomePro.motd_line1);
				player.sendMessage("    " + WelcomePro.motd_line2);
				player.sendMessage("    " + WelcomePro.motd_line3);
				player.sendMessage("");
			}
			else
			{
				player.sendMessage("    There is no Message of the Day");
				player.sendMessage("");
				player.sendMessage("");
				player.sendMessage("");
			}
			player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.STRIKETHROUGH + "=====================================================");
			WelcomePro.playersThatShouldNotSeeChat.remove(player);
		}
	}
	
	
	//Commented out - due to the feature not being implemented yet.
	/*@EventHandler (priority = EventPriority.HIGH)
	private void playerLeave(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		
		e.setQuitMessage(ChatColor.BLUE + "[Server] " + ChatColor.YELLOW + "" + player.getName() + "" + ChatColor.BLUE +  "" + " has gone offline.");
	}*/
	
	@EventHandler (priority = EventPriority.HIGH)
	private void playerChat(AsyncPlayerChatEvent e)
	{
		//Remove any player from seeing chat who is in the list
		for(Player player: WelcomePro.playersThatShouldNotSeeChat)
		{
			e.getRecipients().remove(player);
		}
	}
	
	
}
