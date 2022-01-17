package dev.nyr.main;

import java.util.*;

public class MinecraftDataContainer
{
	/***
	 * 
	 * @author nyr
	 * 
	 * Temporary Material Container
	 */
	public class Material
	{
		public static final String wooden = "WOODEN";
		public static final String stone = "STONE";
		public static final String iron = "IRON";
		public static final String gold = "GOLDEN";
		public static final String diamond = "DIAMOND";
		public static final String netherite = "NETHERITE";
		
		public static List<String> getAsList()
		{
			List<String> tools = new ArrayList<String>();
			tools.add(wooden);
			tools.add(stone);
			tools.add(iron);
			tools.add(gold);
			tools.add(diamond);
			tools.add(netherite);
			return tools;
		}
	}
	
	/***
	 * 
	 * @author janme
	 *
	 * Temporary Tool Container
	 */
	public class Tool
	{
		public static final String axe = "AXE";
		public static final String hoe = "HOE";
		public static final String pickaxe = "PICKAXE";
		public static final String shovel = "SHOVEL";
		public static final String sword = "SWORD";
		
		public static List<String> getAsList()
		{
			List<String> tools = new ArrayList<String>();
			tools.add(axe);
			tools.add(hoe);
			tools.add(pickaxe);
			tools.add(shovel);
			tools.add(sword);
			return tools;
		}
	}
	
	/***
	 * 
	 * @author janme
	 * 
	 * Temporary Armor Container
	 */
	public class Armor
	{
		public static final String boots = "BOOTS";
		public static final String chestplate = "CHESTPLATE";
		public static final String helmet = "HELMET";
		public static final String horsearmor = "HORSE_ARMOR";
		public static final String leggins = "LEGGINS";
		
		public static List<String> getAsList()
		{
			List<String> tools = new ArrayList<String>();
			tools.add(boots);
			tools.add(chestplate);
			tools.add(helmet);
			tools.add(horsearmor);
			tools.add(leggins);
			return tools;
		}
	}
	
	public class ToolArmor
	{
		public static List<String> getToolArmorList()
		{
			List<String> toolArmor = new ArrayList<String>();
			for(String s : Material.getAsList())
			{
				for(String t : Tool.getAsList())
				{
					toolArmor.add(s + "_" + t);
				}
				for(String a : Armor.getAsList())
				{
					toolArmor.add(s + "_" + a);
				}
			}
			return toolArmor;
		}
	}
}
