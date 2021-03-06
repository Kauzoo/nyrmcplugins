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

import dev.nyr.main.SettingsWriter.SettingType;


/**
 * @author nyr
 *
 */

public class UsefullListener implements Listener 
{
	
	/**
	 * Command Settings
	 */
	// General
	// DEFAULTS
	public class PluginCommandDefaults
	{
		// Values
		public static final String pluginSettingsString = "nyr";
		public static final String pluginEnableQuickStackFlag = "-qst";
		public static final String pluginEnableSelfDammageFlag = "-sd";
		public static final String pluginOptionsDelim = ":";
		public static final String pluginHelpFlag = "-help";
		public static final boolean enableQuickStack = false;
		public static final boolean enableSelfDammage = false;
		// Keys
		public static final String pluginSettingsStringKey = "pluginSettingsString";
		public static final String pluginEnableQuickStackFlagKey = "pluginEnableQuickStackFlag";
		public static final String pluginEnableSelfDammageFlagKey = "pluginEnableSelfDammageFlag";
		public static final String pluginOptionsDelimKey = "pluginOptionsDelim";
		public static final String pluginHelpFlagKey = "pluginHelpFlag";
		public static final String enableQuickStackKey = "enableQuickStack";
		public static final String enableSelfDammageKey = "enableSelfDammage";
		
	}
	
	// Quickstack
	// DEFAULTS
	public class QuickStackDefaults
	{
		// Values
		public static final String enableQuickStackString = "qst"; 
		public static final String enableDebugFlag = "-debug";
		public static final String xValueFlag = "-x";
		public static final String yValueFlag = "-y";
		public static final String zValueFlag = "-z";
		public static final String sideValueFlag = "-a";
		public static final String addExclusionFlag = "-add";
		public static final String removeExclusionFlag = "-rem";
		public static final String valueFlagDelim = ":";
		public static final String quickStackHelpFlag = "-help";
		public static final int xSearchRadiusDefault = 10;
		public static final int ySearchRadiusDefault = 10;
		public static final int zSearchRadiusDefault = 10;
		public static final int searchRadiusMax = 20;
		
		// Keys
		public static final String enableQuickStackStringKey = "enableQuickStackString"; 
		public static final String enableDebugFlagKey = "enableDebugFlag";
		public static final String xValueFlagKey = "xValueFlag";
		public static final String yValueFlagKey = "yValueFlag";
		public static final String zValueFlagKey = "zValueFlag";
		public static final String sideValueFlagKey = "sideValueFlag";
		public static final String addExclusionFlagKey = "addQuickStackExclusion";
		public static final String removeExclusionFlagKey = "removeQuickStackExclusion";
		public static final String valueFlagDelimKey = "valueFlagDelim";
		public static final String quickStackHelpFlagKey = "quickStackHelpFlag";
		public static final String xSearchRadiusDefaultKey = "xSearchRadiusDefault";
		public static final String ySearchRadiusDefaultKey = "ySearchRadiusDefault";
		public static final String zSearchRadiusDefaultKey = "zSearchRadiusDefault";
		public static final String searchRadiusMaxKey = "searchRadiusMax";
		
		// Other
		public static final String quickStackExludeKey = "qstExclude";
	}
	
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		player.sendMessage("Server is running nyrmcplugin v.0.1.0 [TEST BUILD]");
		player.sendMessage("Status: SelfDammage:" + isEnableSelfDammage(SettingType.PLUGINSETTINGS) + " QuickStack:" + isEnableQuickStack(SettingType.PLUGINSETTINGS));
		player.sendMessage("WARNING: Experimental");
		player.sendMessage("To learn more about QuickStack use: " + getEnableQuickStackString(SettingType.PLUGINSETTINGS) + " " + getQuickStackHelpFlag(SettingType.PLUGINSETTINGS));
		if(player.isOp())
		{
			player.sendMessage("To lean more about nyrmcplugin use: " + getPluginSettingsString(SettingType.PLUGINSETTINGS) + " " + getPluginHelpFlag(SettingType.PLUGINSETTINGS));
		}
		try
		{
			SettingsWriter.CreateSettingFile(SettingType.QUICKSTACK_PLAYER, player.getDisplayName());
			SettingsWriter.CreateSettingFile(SettingType.SELFDAMMAGE_PLAYER, player.getDisplayName());
			if(!SettingsWriter.CheckFileExistence(SettingType.QUICKSTACK_PLAYER, player.getDisplayName()))
			{
				RequirementsCreator.InitializePlayerQuickStackExclude(player);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@EventHandler
	public void onPlayerDammageEvent(EntityDamageByEntityEvent event)
	{
		if(!isEnableSelfDammage(SettingType.PLUGINSETTINGS))
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
				player.sendMessage("Stop hitting yourself");
			}
		}
	}
	
	/***
	 * Parses chat messages for nyrmcplugin commands
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
		if(playerChatEvent.getPlayer().isOp() && !message.isEmpty() && message.get(0).startsWith(getPluginSettingsString(SettingType.PLUGINSETTINGS)))
		{
			playerChatEvent.setCancelled(true);
			for(String s : message)
			{
				if(s.startsWith(getPluginEnableQuickStackFlag(SettingType.PLUGINSETTINGS)))
				{
					try
					{
						 SettingsWriter.ChangeSetting(SettingType.PLUGINSETTINGS, PluginCommandDefaults.enableQuickStackKey, s.split(getPluginOptionsDelim(SettingType.PLUGINSETTINGS))[1], "=", null);
						 player.sendMessage("Changed value for enableQuickStack to: " + isEnableQuickStack(SettingType.PLUGINSETTINGS));
						 Bukkit.broadcastMessage("QuickStack is now: " +  isEnableQuickStack(SettingType.PLUGINSETTINGS));
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse boolean on enableQuickStackFlag. Usage nyr -qst:<boolean>.");
						player.sendMessage("Current value for enableQuickStack: " + isEnableQuickStack(SettingType.PLUGINSETTINGS));
						e.printStackTrace();
					}
				}
				if(s.startsWith(getPluginEnableSelfDammageFlag(SettingType.PLUGINSETTINGS) + getPluginOptionsDelim(SettingType.PLUGINSETTINGS)))
				{
					try
					{
						 SettingsWriter.ChangeSetting(SettingType.PLUGINSETTINGS, PluginCommandDefaults.enableSelfDammageKey, s.split(getPluginOptionsDelim(SettingType.PLUGINSETTINGS))[1], "=", null); 
						 player.sendMessage("Changed value for enableSelfDammage to: " + isEnableSelfDammage(SettingType.PLUGINSETTINGS));
						 Bukkit.broadcastMessage("SelfDammage is now: " + isEnableSelfDammage(SettingType.PLUGINSETTINGS));
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse boolean on enableSelfDammageFlag. Usage: nyr -sd:<boolean>.");
						player.sendMessage("Current value for enableSelfDammage: " + isEnableQuickStack(SettingType.PLUGINSETTINGS));
						e.printStackTrace();
					}
				}
				if(s.equals(getPluginHelpFlag(SettingType.PLUGINSETTINGS)))
				{
					printPluginHelp(player);
				}
				if(s.equals("-reset"))
				{
					RequirementsCreator.HardResetRequirements();
				}
			}
		}
		/*
		 * Parse message for quickStack
		 */
		if(!message.isEmpty() && message.get(0).equals(getEnableQuickStackString(SettingType.QUICKSTACK)))
		{
			playerChatEvent.setCancelled(true);
			int xSearchRadius = getXsearchradiusdefault(SettingType.QUICKSTACK);
			int ySearchRadius = getYsearchradiusdefault(SettingType.QUICKSTACK);
			int zSearchRadius = getZsearchradiusdefault(SettingType.QUICKSTACK);
			boolean debugFlag = false;
			for(String s : message)
			{
				if(s.equals(getQuickStackHelpFlag(SettingType.QUICKSTACK)))
				{
					QuickStackCore.printQuickStackHelp(player);
					return;
				}
				// Do not parse arguments except help when quickstack is disabled
				if(!isEnableQuickStack(SettingType.PLUGINSETTINGS))
				{
					return;
				}
				if(s.startsWith(getAddExclusionFlag(SettingType.QUICKSTACK)))
				{
					QuickStackCore.AddQuickStackExclude(SettingType.QUICKSTACK_PLAYER, player);
					return;
				}
				if(s.startsWith(getRemoveExclusionFlag(SettingType.QUICKSTACK)))
				{
					QuickStackCore.RemoveQuickStackExcludeForPlayer(SettingType.QUICKSTACK_PLAYER, player);
					return;
				}
				if(s.startsWith(getSideValueFlag(SettingType.QUICKSTACK)))
				{
					try
					{
						int i = Math.abs(Integer.parseInt(s.split(getValueFlagDelim(SettingType.QUICKSTACK))[1]));
						i = (i <= getSearchradiusmax(SettingType.QUICKSTACK)) ? i : getSearchradiusmax(SettingType.QUICKSTACK);
						xSearchRadius = i;
						ySearchRadius = i;
						zSearchRadius = i;
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on a-Flag");
						QuickStackCore.printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
					
				}
				if(s.startsWith(getxValueFlag(SettingType.QUICKSTACK) + getValueFlagDelim(SettingType.QUICKSTACK)))
				{
					try
					{
						xSearchRadius = Math.abs(Integer.parseInt(s.split(getValueFlagDelim(SettingType.QUICKSTACK))[1]));
						xSearchRadius = (xSearchRadius <= getSearchradiusmax(SettingType.QUICKSTACK)) ? xSearchRadius : getSearchradiusmax(SettingType.QUICKSTACK);
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on x-Flag");
						QuickStackCore.printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
				}
				if(s.startsWith(getyValueFlag(SettingType.QUICKSTACK) + getValueFlagDelim(SettingType.QUICKSTACK)))
				{
					try
					{
						ySearchRadius = Math.abs(Integer.parseInt(s.split(getValueFlagDelim(SettingType.QUICKSTACK))[1]));
						ySearchRadius = (ySearchRadius <= getSearchradiusmax(SettingType.QUICKSTACK)) ? ySearchRadius : getSearchradiusmax(SettingType.QUICKSTACK);
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on y-Flag");
						QuickStackCore.printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
				}
				if(s.startsWith(getzValueFlag(SettingType.QUICKSTACK) + getValueFlagDelim(SettingType.QUICKSTACK)))
				{
					try
					{
						zSearchRadius = Math.abs(Integer.parseInt(s.split(getValueFlagDelim(SettingType.QUICKSTACK))[1]));
						zSearchRadius = (zSearchRadius <= getSearchradiusmax(SettingType.QUICKSTACK)) ? zSearchRadius : getSearchradiusmax(SettingType.QUICKSTACK);
					}
					catch(Exception e)
					{
						player.sendMessage("Failed to parse Integer on z-Flag");
						QuickStackCore.printQuickStackHelp(player);
						e.printStackTrace();
						return;
					}
				}
				if(s.equals(getQuickStackHelpFlag(SettingType.QUICKSTACK)))
				{
					debugFlag = true;
				}
			}
			QuickStackCore.lookForChests(playerChatEvent.getPlayer(), xSearchRadius, ySearchRadius, zSearchRadius, debugFlag);
		}
	}
		
	/**
	 * GETTERS
	 * @return
	 */
	public static String getPluginSettingsString(SettingType type) {
		String value = PluginCommandDefaults.pluginSettingsString;
		try 
		{
			value = SettingsWriter.ReadSetting(type, "pluginSettingString", "=", null);
			value = (!value.equals("")) ? value : PluginCommandDefaults.pluginSettingsString;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getPluginEnableQuickStackFlag(SettingType type) {
		String value = PluginCommandDefaults.pluginEnableQuickStackFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, PluginCommandDefaults.pluginEnableQuickStackFlagKey, "=", null);
			value = (!value.equals("")) ? value : PluginCommandDefaults.pluginEnableQuickStackFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getPluginEnableSelfDammageFlag(SettingType type) {
		String value = PluginCommandDefaults.pluginEnableSelfDammageFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, PluginCommandDefaults.pluginEnableSelfDammageFlagKey, "=", null);
			value = (!value.equals("")) ? value : PluginCommandDefaults.pluginEnableSelfDammageFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getPluginOptionsDelim(SettingType type) {
		String value = PluginCommandDefaults.pluginOptionsDelim;
		try 
		{
			value = SettingsWriter.ReadSetting(type, PluginCommandDefaults.pluginOptionsDelimKey, "=", null);
			value = (!value.equals("")) ? value : PluginCommandDefaults.pluginOptionsDelim;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getPluginHelpFlag(SettingType type) {
		String value = PluginCommandDefaults.pluginHelpFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, PluginCommandDefaults.pluginHelpFlagKey, "=", null);
			value = (!value.equals("")) ? value : PluginCommandDefaults.pluginHelpFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static boolean isEnableQuickStack(SettingType type) {
		boolean value = PluginCommandDefaults.enableQuickStack;
		try 
		{
			value = Boolean.parseBoolean(SettingsWriter.ReadSetting(type, PluginCommandDefaults.enableQuickStackKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static boolean isEnableSelfDammage(SettingType type) {
		boolean value = PluginCommandDefaults.enableSelfDammage;
		try 
		{
			value = Boolean.parseBoolean(SettingsWriter.ReadSetting(type, PluginCommandDefaults.enableSelfDammageKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getEnableQuickStackString(SettingType type) {
		String value = QuickStackDefaults.enableQuickStackString;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.enableQuickStackStringKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.enableQuickStackString;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getEnableDebugFlag(SettingType type) {
		String value = QuickStackDefaults.enableDebugFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.enableDebugFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.enableDebugFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getxValueFlag(SettingType type) {
		String value = QuickStackDefaults.xValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.xValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.xValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getyValueFlag(SettingType type) {
		String value = QuickStackDefaults.yValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.yValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.yValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getzValueFlag(SettingType type) {
		String value = QuickStackDefaults.zValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.zValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.zValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getSideValueFlag(SettingType type) {
		String value = QuickStackDefaults.sideValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.sideValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.sideValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getValueFlagDelim(SettingType type) {
		String value = QuickStackDefaults.valueFlagDelim;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.valueFlagDelimKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.valueFlagDelim;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static String getQuickStackHelpFlag(SettingType type) {
		String value = QuickStackDefaults.quickStackHelpFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.quickStackHelpFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.quickStackHelpFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static int getXsearchradiusdefault(SettingType type) {
		int value = QuickStackDefaults.xSearchRadiusDefault;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(type, QuickStackDefaults.xSearchRadiusDefaultKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static int getYsearchradiusdefault(SettingType type) {
		int value = QuickStackDefaults.ySearchRadiusDefault;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(type, QuickStackDefaults.ySearchRadiusDefaultKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static int getZsearchradiusdefault(SettingType type) {
		int value = QuickStackDefaults.zSearchRadiusDefault;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(type, QuickStackDefaults.zSearchRadiusDefaultKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public static int getSearchradiusmax(SettingType type) {
		int value = QuickStackDefaults.searchRadiusMax;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(type, QuickStackDefaults.searchRadiusMaxKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public static String getAddExclusionFlag(SettingType type)
	{
		String value = QuickStackDefaults.addExclusionFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.addExclusionFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.addExclusionFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public static String getRemoveExclusionFlag(SettingType type)
	{
		String value = QuickStackDefaults.removeExclusionFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(type, QuickStackDefaults.removeExclusionFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.removeExclusionFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public static void printPluginHelp(Player player)
	{
		String delim = getPluginOptionsDelim(SettingType.PLUGINSETTINGS);
		String pluginSetting = getPluginSettingsString(SettingType.PLUGINSETTINGS);
		player.sendMessage("Usage: " + pluginSetting + " " + getPluginEnableQuickStackFlag(SettingType.PLUGINSETTINGS) + delim + "<boolean>");
		player.sendMessage("Usage: " + pluginSetting + " " + getPluginEnableSelfDammageFlag(SettingType.PLUGINSETTINGS) + delim + "<boolean>");
		player.sendMessage("SelfDammage: " + isEnableSelfDammage(SettingType.PLUGINSETTINGS));
		player.sendMessage("QuickStack: " + isEnableQuickStack(SettingType.PLUGINSETTINGS));
	}
	
	
	/***
	 * DEBUG ONLY
	 */
	public static void printDebugInfo()
	{
		System.out.println(getPluginEnableQuickStackFlag(SettingType.PLUGINSETTINGS));
		System.out.println(getPluginEnableSelfDammageFlag(SettingType.PLUGINSETTINGS));
		System.out.println(getPluginHelpFlag(SettingType.PLUGINSETTINGS));
		System.out.println(getPluginOptionsDelim(SettingType.PLUGINSETTINGS));
		System.out.println(getPluginOptionsDelim(SettingType.PLUGINSETTINGS));
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
}
