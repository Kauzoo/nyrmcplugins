/**
 * 
 */
package dev.nyr.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


/**
 * @author janme
 *
 */

public class UsefullListener implements Listener 
{	
	/**
	 * Command Settings
	 */
	// General
	private static String pluginSettingsString = "nyr";
	private static String pluginEnableQuickStackFlag = "-qst";
	private static String pluginEnableSelfDammageFlag = "-sd";
	private static String pluginOptionsDelim = ":";
	private static String pluginHelpFlag = "-help";
	private boolean enableQuickStack = false;
	private boolean enableSelfDammage = false;
	
	// Quickstack
	private static String enableQuickStackString = "qst"; 
	private static String enableDebugFlag = "-debug";
	private static String xValueFlag = "-x";
	private static String yValueFlag = "-y";
	private static String zValueFlag = "-z";
	private static String sideValueFlag = "-a";
	private static String valueFlagDelim = ":";
	private static String quickStackHelpFlag = "-help";
	
	/**
	 * Quickstack Settings
	 */
	private static final int xSearchRadiusDefault = 10; // specifies half the side length of the search box in X-Direction
	private static final int ySearchRadiusDefault = 10; // specifies half the side length of the search box in y-Direction
	private static final int zSearchRadiusDefault = 10; // specifies half the side length of the search box in z-Direction
	private static final int searchRadiusMax = 20; // specifies max search radius
	
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		player.sendMessage("Server is nyrmcplugin");
		player.sendMessage("Status: SelfDammage:" + enableSelfDammage + " QuickStack:" + enableQuickStack);
		player.sendMessage("WARNING: Exoerimental");
		player.sendMessage("To learn more about QuickStack type: " + enableQuickStackString + " " + quickStackHelpFlag);
		if(player.isOp())
		{
			player.sendMessage("To lean more about nyrmcplugin type: " + pluginSettingsString + " " + pluginHelpFlag);
		}
	}
	
	@EventHandler
	public void onPlayerDammageEvent(EntityDamageByEntityEvent event)
	{
		if(!enableSelfDammage)
		{
			return;
		}
		if(event.getDamager().getType() == EntityType.PLAYER && event.getEntityType() == EntityType.PLAYER)
		{
			if(event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.ENTITY_SWEEP_ATTACK || event.getCause() == DamageCause.PROJECTILE)
			{
				double damageDealt = event.getFinalDamage();
				event.setDamage(0);
				Player player = (Player) event.getDamager();
				player.damage(damageDealt);
				player.sendMessage("Ich hab mich grade f�r " + damageDealt + "geboxt");
			}
		}
	}
	
	/***
	 * Used to check for a player wanting to quickstack
	 * nyrplugin commands are always lead by "-"
	 * In order to try quickstacking type -q
	 * @param playerChatEvent
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void parseChatMessage(PlayerChatEvent playerChatEvent)
	{
		Player player = playerChatEvent.getPlayer();
		String inputString = playerChatEvent.getMessage();
		List<String> message = Arrays.asList(inputString.split(" "));
		/*
		 * Parse message for plugin settings
		 */
		if(playerChatEvent.getPlayer().isOp() && !message.isEmpty() && message.get(0).startsWith(pluginSettingsString))
		{
			for(String s : message)
			{
				if(s.startsWith(pluginEnableQuickStackFlag + pluginOptionsDelim))
				{
					try
					{
						 enableQuickStack = Boolean.parseBoolean(s.split(pluginOptionsDelim)[1]);
						 player.sendMessage("Changed value for enableQuickStack to: " + enableQuickStack);
						 Bukkit.broadcastMessage("QuickStack is now: " + enableQuickStack);
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse boolean on enableQuickStackFlag. Usage nyr -qst:<boolean>.");
						player.sendMessage("Current value for enableQuickStack: " + enableQuickStack);
						e.printStackTrace();
					}
				}
				if(s.startsWith(pluginEnableSelfDammageFlag + pluginOptionsDelim))
				{
					try
					{
						 enableQuickStack = Boolean.parseBoolean(s.split(pluginOptionsDelim)[1]);
						 player.sendMessage("Changed value for enableSelfDammage to: " + enableSelfDammage);
						 Bukkit.broadcastMessage("SelfDammage is now: " + enableSelfDammage);
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse boolean on enableSelfDammageFlag. Usage: nyr -sd:<boolean>.");
						player.sendMessage("Current value for enableSelfDammage: " + enableSelfDammage);
						e.printStackTrace();
					}
				}
				if(s.equals(pluginHelpFlag))
				{
					printPluginHelp(player);
				}
			}
		}
		if(!enableQuickStack)
		{
			return;
		}
		/*
		 * Parse message for quickStack
		 */
		if(!message.isEmpty() && message.get(0).equals(enableQuickStackString))
		{
			int xSearchRadius = xSearchRadiusDefault;
			int ySearchRadius = ySearchRadiusDefault;
			int zSearchRadius = zSearchRadiusDefault;
			boolean debugFlag = false;
			for(String s : message)
			{
				if(s.startsWith(sideValueFlag))
				{
					try
					{
						int i = Math.abs(Integer.parseInt(s.split(valueFlagDelim)[1]));
						
						xSearchRadius = i;
						ySearchRadius = i;
						zSearchRadius = i;
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on a-Flag");
						printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
					
				}
				if(s.startsWith(xValueFlag + valueFlagDelim))
				{
					try
					{
						xSearchRadius = Math.abs(Integer.parseInt(s.split(valueFlagDelim)[1]));
						xSearchRadius = (xSearchRadius <= searchRadiusMax) ? xSearchRadius : searchRadiusMax;
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on x-Flag");
						printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
				}
				if(s.startsWith(yValueFlag + valueFlagDelim))
				{
					try
					{
						ySearchRadius = Math.abs(Integer.parseInt(s.split(valueFlagDelim)[1]));
						ySearchRadius = (ySearchRadius <= searchRadiusMax) ? ySearchRadius : searchRadiusMax;
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on y-Flag");
						printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
				}
				if(s.startsWith(zValueFlag + valueFlagDelim))
				{
					try
					{
						zSearchRadius = Math.abs(Integer.parseInt(s.split(valueFlagDelim)[1]));
						zSearchRadius = (zSearchRadius <= searchRadiusMax) ? zSearchRadius : searchRadiusMax;
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on z-Flag");
						printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
				}
				if(s.equals(enableDebugFlag))
				{
					debugFlag = true;
				}
				if(s.equals(quickStackHelpFlag))
				{
					printQuickStackHelp(player);
					return;
				}
			}
			this.lookForChests(playerChatEvent.getPlayer(), xSearchRadius, ySearchRadius, zSearchRadius, debugFlag);
		}
		
	}
	
	/*
	 * Handles looking for chests
	 */
	private void lookForChests(Player player, int xSearchRadius, int ySearchRadius, int zSearchRadius, boolean debug)
	{
		player.sendMessage("Searching for chests in your area");
		int[] positionVector = { player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ() };
		player.sendMessage("Using position " + "(" + positionVector[0] + "|" + positionVector[1] + "|" + positionVector[2] + ")");
		player.sendMessage("Using SideLength X:" + xSearchRadius * 2 + " Y:" + ySearchRadius * 2 + " Z:" + zSearchRadius * 2);
		
		// x-Loop
		for(int i = positionVector[0] - xSearchRadius; i < positionVector[0] + xSearchRadius; i++)
		{
			// y-Loop
			for(int j = positionVector[1] - ySearchRadius; j < positionVector[1] + ySearchRadius; j++)
			{
				// z-Loop
				for(int k = positionVector[2] - zSearchRadius; k < positionVector[2] + zSearchRadius; k++)
				{
					Block block = new Location(player.getWorld(), i, j, k).getBlock();
					if(debug)
					{
						player.sendMessage("Looking for block @ (" + i + "|" + j + "|" + k + ") Block was " + block.getType().toString());
					}
					if(block.getType() == Material.CHEST)
					{
						if(debug)
						{
							player.sendMessage("Chest detected @ (" + i + "|" + j + "|" + k + ")");
							System.out.println("Chest detected @ (" + i + "|" + j + "|" + k + ")");
						}
						this.handleQuickStack(player, (Chest) block.getState(), debug);
					}
				}
			}
		}
	}
	
	/*
	 * Quickstacks to a chest
	 */
	private void handleQuickStack(Player player, Chest chest, boolean debug)
	{
		Inventory chestInventory = chest.getInventory();
		Inventory playerInventory = player.getInventory();
		for(ItemStack itemStack : playerInventory.getStorageContents())
		{
			if(itemStack != null && chestInventory.contains(itemStack.getType()))
			{
				HashMap<Integer, ItemStack> itemOverflow = chestInventory.addItem(itemStack);
				if(itemOverflow.isEmpty())
				{
					playerInventory.remove(itemStack);
				}
				else if(debug)
				{
					player.sendMessage("item overflow");
				}
			}
		}
	}
	
	/*
	 * Helper
	 */
	private void printQuickStackHelp(Player player)
	{
		player.sendMessage("WARNING: Exoerimental");
		player.sendMessage("Usage: " + enableQuickStackString + "	(Run with default values)");
		player.sendMessage("Usage: " + enableQuickStackString + " " + sideValueFlag + valueFlagDelim + "<int>" + "	(Run with cube side length a)");
		player.sendMessage("Usage: " + enableQuickStackString + " " + xValueFlag + valueFlagDelim + "<int>" + " " + yValueFlag + valueFlagDelim + "<int>" + " " + zValueFlag + valueFlagDelim + "<int>" + "	(Run with custom side length x y z)");
		player.sendMessage("MaxSearchRadius: " + searchRadiusMax);
	}
	
	private String constructQuickStackUsageString()
	{
		return "Usage: " + enableQuickStackString + "	(Run with default values)" + '\n'
				+ "Usage: " + enableQuickStackString + " " + sideValueFlag + valueFlagDelim + "<int>" + "	(Run with cube side length a)" + '\n' 
				+ "Usage: " + enableQuickStackString + " " + xValueFlag + valueFlagDelim + "<int>" + " " + yValueFlag + valueFlagDelim + "<int>" + " " + zValueFlag + valueFlagDelim + "<int>" + "	(Run with custom side length x y z)";
	}
	
	private void printPluginHelp(Player player)
	{
		player.sendMessage("Usage: " + pluginSettingsString + " " + pluginEnableQuickStackFlag + pluginOptionsDelim + "<boolean>");
		player.sendMessage("Usage: " + pluginSettingsString + " " + pluginEnableSelfDammageFlag + pluginOptionsDelim + "<boolean>");
		player.sendMessage("SelfDammage: " + enableSelfDammage);
		player.sendMessage("QuickStack: " + enableQuickStack);
	}
}
