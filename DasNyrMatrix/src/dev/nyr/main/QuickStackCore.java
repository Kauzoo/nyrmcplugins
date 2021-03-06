package dev.nyr.main;

import java.util.HashMap;
import java.util.List;
import java.util.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.nyr.main.SettingsWriter.SettingType;

public class QuickStackCore
{

	/*
	 * Handles looking for chests
	 */
	public static void lookForChests(Player player, int xSearchRadius, int ySearchRadius, int zSearchRadius,
			boolean debug)
	{
		player.sendMessage("Searching for chests in your area");
		int[] positionVector = { player.getLocation().getBlockX(), player.getLocation().getBlockY(),
				player.getLocation().getBlockZ() };
		player.sendMessage(
				"Using position " + "(" + positionVector[0] + "|" + positionVector[1] + "|" + positionVector[2] + ")");
		player.sendMessage(
				"Using SideLength X:" + xSearchRadius * 2 + " Y:" + ySearchRadius * 2 + " Z:" + zSearchRadius * 2);

		// Try parse exclude list
		List<String> excludeList = new ArrayList<>();
		try
		{
			List<String> excludeListUnparsed = SettingsWriter.ReadFile(SettingType.QUICKSTACK_PLAYER,
					player.getDisplayName());
			for (String s : excludeListUnparsed)
			{
				excludeList.add(s.split("=")[1]);
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// x-Loop
		for (int i = positionVector[0] - xSearchRadius; i < positionVector[0] + xSearchRadius; i++)
		{
			// y-Loop
			for (int j = positionVector[1] - ySearchRadius; j < positionVector[1] + ySearchRadius; j++)
			{
				// z-Loop
				for (int k = positionVector[2] - zSearchRadius; k < positionVector[2] + zSearchRadius; k++)
				{
					Block block = new Location(player.getWorld(), i, j, k).getBlock();
					if (debug)
					{
						player.sendMessage("Looking for block @ (" + i + "|" + j + "|" + k + ") Block was "
								+ block.getType().toString());
					}
					if (block.getType() == Material.CHEST)
					{
						if (debug)
						{
							player.sendMessage("Chest detected @ (" + i + "|" + j + "|" + k + ")");
							System.out.println("Chest detected @ (" + i + "|" + j + "|" + k + ")");
						}
						handleQuickStack(player, (Chest) block.getState(), debug, excludeList);
					}
				}
			}
		}
	}

	/*
	 * Quickstacks to a chest
	 */
	public static void handleQuickStack(Player player, Chest chest, boolean debug, List<String> excludeList)
	{
		Inventory chestInventory = chest.getInventory();
		Inventory playerInventory = player.getInventory();

		try
		{

			for (ItemStack itemStack : playerInventory.getStorageContents())
			{
				if (itemStack != null && chest.getInventory().contains(itemStack.getType())
						&& !excludeList.contains(itemStack.getType().toString()))
				{
					HashMap<Integer, ItemStack> itemOverflow = chestInventory.addItem(itemStack);
					if (itemOverflow.isEmpty())
					{
						playerInventory.remove(itemStack);
					} else if (debug)
					{
						player.sendMessage("item overflow");
					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static List<String> ParseExcludeList(Player player)
	{
		// Try parse exclude list
		List<String> excludeList = new ArrayList<>();
		try
		{
			List<String> excludeListUnparsed = SettingsWriter.ReadFile(SettingType.QUICKSTACK_PLAYER,
					player.getDisplayName());
			for (String s : excludeListUnparsed)
			{
				excludeList.add(s.split("=")[1]);
			}
			return excludeList;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return excludeList;
		}
	}

	@SuppressWarnings("deprecation")
	public static void AddQuickStackExclude(SettingType type, Player player)
	{
		if (player.getItemInHand() != null && !ParseExcludeList(player).contains(player.getItemInHand().getType().toString()))
		{
			try
			{
				SettingsWriter.AddSetting(type, UsefullListener.QuickStackDefaults.quickStackExludeKey,
						player.getItemInHand().getType().toString(), "=", player.getDisplayName());
				player.sendMessage("Added " + player.getItemInHand().getType().toString() + " to your QuickStackExclude");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void RemoveQuickStackExcludeForPlayer(SettingType type, Player player)
	{
		if (player.getItemInHand() != null && ParseExcludeList(player).contains(player.getItemInHand().getType().toString()))
		{
			try
			{
				SettingsWriter.RemoveSetting(type, UsefullListener.QuickStackDefaults.quickStackExludeKey + "="
						+ player.getItemInHand().getType().toString(), player.getDisplayName());
				player.sendMessage("Removed " + player.getItemInHand().getType().toString() + " from your QuickStackExclude");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/*
	 * Helper
	 */
	public static void printQuickStackHelp(Player player)
	{
		String delim = UsefullListener.getValueFlagDelim(SettingType.QUICKSTACK);
		String enableQuickStack = UsefullListener.getEnableQuickStackString(SettingType.QUICKSTACK);
		player.sendMessage("WARNING: Experimental");
		player.sendMessage("Usage: " + enableQuickStack + "	(Run with default values)");
		player.sendMessage("Usage: " + enableQuickStack + " " + UsefullListener.getSideValueFlag(SettingType.QUICKSTACK)
				+ delim + "<int>" + "	(Run with cube side length a)");
		player.sendMessage("Usage: " + enableQuickStack + " " + UsefullListener.getxValueFlag(SettingType.QUICKSTACK)
				+ delim + "<int>" + " " + UsefullListener.getyValueFlag(SettingType.QUICKSTACK) + delim + "<int>" + " "
				+ UsefullListener.getzValueFlag(SettingType.QUICKSTACK) + delim + "<int>"
				+ "	(Run with custom side length x y z)");
		player.sendMessage("MaxSearchRadius: " + UsefullListener.getSearchradiusmax(SettingType.QUICKSTACK));
		player.sendMessage("XDefault: " + UsefullListener.getXsearchradiusdefault(SettingType.QUICKSTACK));
		player.sendMessage("YDefault: " + UsefullListener.getYsearchradiusdefault(SettingType.QUICKSTACK));
		player.sendMessage("ZDefault: " + UsefullListener.getZsearchradiusdefault(SettingType.QUICKSTACK));
		player.sendMessage("By default armor and tools will not be quickstaked. QuickStack exclusions can added or removed");
		player.sendMessage("Usage: " + enableQuickStack + " " +UsefullListener.getAddExclusionFlag(SettingType.QUICKSTACK) + " (Add item in main hand to exclusion list)");
		player.sendMessage("Usage: " + enableQuickStack + " " +UsefullListener.getRemoveExclusionFlag(SettingType.QUICKSTACK) + " (Remove item in main hand from exclusion list)");
	}
}
