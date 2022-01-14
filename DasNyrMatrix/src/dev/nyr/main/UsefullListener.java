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
	private static String enableQuickStackString = "-q"; 
	private static String enableDebugFlag = "-debug";
	private static String xValueFlag = "-x";
	private static String yValueFlag = "-y";
	private static String zValueFlag = "-z";
	private static String sideValueFlag = "-a";
	private static String valueFlagDelim = ":";
	private static String helpFlag = "-help";
	
	/**
	 * Quickstack Settings
	 */
	private static final int xSearchRadiusDefault = 10; // specifies half the side length of the search box in X-Direction
	private static final int ySearchRadiusDefault = 10; // specifies half the side length of the search box in y-Direction
	private static final int zSearchRadiusDefault = 10; // specifies half the side length of the search box in z-Direction
	
	/**
	 * Quickstack Iteration Memory
	 */
	
	@EventHandler
	public void handlePlayerDammageEvent(EntityDamageByEntityEvent event)
	{
		System.out.println("A EntityDamageByEntityEvent was triggerd");
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
	public void onTriggerQuickstack(PlayerChatEvent playerChatEvent)
	{
		String inputString = playerChatEvent.getMessage();
		List<String> message = Arrays.asList(inputString.split(" "));
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
						Bukkit.broadcastMessage("Failed to parse Integer on a-Flag");
						e.printStackTrace();
					}
					
				}
				if(s.startsWith(xValueFlag + valueFlagDelim))
				{
					try
					{
						xSearchRadius = Math.abs(Integer.parseInt(s.split(valueFlagDelim)[1]));
					}
					catch(Exception e)
					{
						Bukkit.broadcastMessage("Failed to parse Integer on x-Flag");
						e.printStackTrace();
					}
				}
				if(s.startsWith(yValueFlag + valueFlagDelim))
				{
					try
					{
						ySearchRadius = Math.abs(Integer.parseInt(s.split(valueFlagDelim)[1]));
					}
					catch(Exception e)
					{
						Bukkit.broadcastMessage("Failed to parse Integer on y-Flag");
						e.printStackTrace();
					}
				}
				if(s.startsWith(zValueFlag + valueFlagDelim))
				{
					try
					{
						zSearchRadius = Math.abs(Integer.parseInt(s.split(valueFlagDelim)[1]));
					}
					catch(Exception e)
					{
						Bukkit.broadcastMessage("Failed to parse Integer on z-Flag");
						e.printStackTrace();
					}
				}
				if(s.equals(enableDebugFlag))
				{
					debugFlag = true;
				}
			}
			this.lookForChests(playerChatEvent.getPlayer(), xSearchRadius, ySearchRadius, zSearchRadius, debugFlag);
		}
	}
	
	private void lookForChests(Player player, int xSearchRadius, int ySearchRadius, int zSearchRadius, boolean debug)
	{
		Bukkit.broadcastMessage("Searching for chests in your area");
		System.out.println("Searching for chests in your area");
		int[] positionVector = { player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ() };
		Bukkit.broadcastMessage("Using position " + "(" + positionVector[0] + "|" + positionVector[1] + "|" + positionVector[2] + ")");
		System.out.println("Using position " + "(" + positionVector[0] + "|" + positionVector[1] + "|" + positionVector[2] + ")");
		Bukkit.broadcastMessage("Using SideLength X:" + xSearchRadius * 2 + " Y:" + ySearchRadius * 2 + " Z:" + zSearchRadius * 2);
		System.out.println("Using SideLength X:" + xSearchRadius * 2 + " Y:" + ySearchRadius * 2 + " Z:" + zSearchRadius * 2);
		
		// x-Loop first
		for(int i = positionVector[0] - xSearchRadius; i < positionVector[0] + xSearchRadius; i++)
		{
			for(int j = positionVector[1] - ySearchRadius; j < positionVector[1] + ySearchRadius; j++)
			{
				for(int k = positionVector[2] - zSearchRadius; k < positionVector[2] + zSearchRadius; k++)
				{
					Block block = new Location(player.getWorld(), i, j, k).getBlock();
					if(debug)
					{
						Bukkit.broadcastMessage("Looking for block @ (" + i + "|" + j + "|" + k + ") Block was " + block.getType().toString());
					}
					if(block.getType() == Material.CHEST)
					{
						Bukkit.broadcastMessage("Chest detected @ (" + i + "|" + j + "|" + k + ")");
						System.out.println("Chest detected @ (" + i + "|" + j + "|" + k + ")");
						this.handleQuickStack(player, (Chest) block.getState(), debug);
					}
				}
			}
		}
	}
	
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
					Bukkit.broadcastMessage("item overflow");
				}
			}
		}
	}
	
}
