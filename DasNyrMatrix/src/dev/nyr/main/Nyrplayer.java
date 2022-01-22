package dev.nyr.main;

import java.util.UUID;

import org.bukkit.entity.Player;

import dev.nyr.main.SettingsWriter.SettingType;
import dev.nyr.main.UsefullListener.PluginCommandDefaults;
import dev.nyr.main.UsefullListener.QuickStackDefaults;

public class Nyrplayer
{
	// Plugin feature vars
	public boolean isQuickStackEnabled = false;
	public boolean isSelfDammageEnabled = false;
	public String enableQuickStackString = "qst"; 
	public String enableDebugFlag = "-debug";
	public String xValueFlag = "-x";
	public String yValueFlag = "-y";
	public String zValueFlag = "-z";
	public String sideValueFlag = "-a";
	public String addExclusionFlag = "-add";
	public String removeExclusionFlag = "-rem";
	public String valueFlagDelim = ":";
	public String quickStackHelpFlag = "-help";
	public int xSearchRadiusDefault = 10;
	public int ySearchRadiusDefault = 10;
	public int zSearchRadiusDefault = 10;
	public int tsearchRadiusMax = 20;
	
	// Identification
	public UUID uniquePlayerIdentifier;
	public String displayName;
	
	
	/***
	 * 	HELPER VARIABLES
	 */
	private static String printMarker;
	
	
	public Nyrplayer(Player player)
	{
		uniquePlayerIdentifier = player.getUniqueId();
		displayName = player.getDisplayName();
		
		try
		{
			if(SettingsWriter.CreateSettingFile(SettingType.QUICKSTACK_PLAYER, displayName))
			{
				System.out.println("");
			}
			else
			{
				System.out.println("");
			}
		} catch (Exception e)
		{
			System.out.println("");
			e.printStackTrace();
		}
	}

	/**
	 * GETTER SECTION
	 * Getters read in value from files to memory
	 * Use only on object initialization or on forced reload to limit file system operations
	 */
	
	public boolean isEnableQuickStack() {
		boolean value = PluginCommandDefaults.enableQuickStack;
		try 
		{
			value = Boolean.parseBoolean(SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, PluginCommandDefaults.enableQuickStackKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public boolean isEnableSelfDammage() {
		boolean value = PluginCommandDefaults.enableSelfDammage;
		try 
		{
			value = Boolean.parseBoolean(SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, PluginCommandDefaults.enableSelfDammageKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getEnableQuickStackString() {
		String value = QuickStackDefaults.enableQuickStackString;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.enableQuickStackStringKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.enableQuickStackString;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getEnableDebugFlag() {
		String value = QuickStackDefaults.enableDebugFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.enableDebugFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.enableDebugFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getxValueFlag() {
		String value = QuickStackDefaults.xValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.xValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.xValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getyValueFlag() {
		String value = QuickStackDefaults.yValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.yValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.yValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getzValueFlag() {
		String value = QuickStackDefaults.zValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.zValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.zValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getSideValueFlag() {
		String value = QuickStackDefaults.sideValueFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.sideValueFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.sideValueFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getValueFlagDelim() {
		String value = QuickStackDefaults.valueFlagDelim;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.valueFlagDelimKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.valueFlagDelim;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public String getQuickStackHelpFlag() {
		String value = QuickStackDefaults.quickStackHelpFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.quickStackHelpFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.quickStackHelpFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public int getXsearchradiusdefault() {
		int value = QuickStackDefaults.xSearchRadiusDefault;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.xSearchRadiusDefaultKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public int getYsearchradiusdefault() {
		int value = QuickStackDefaults.ySearchRadiusDefault;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.ySearchRadiusDefaultKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public int getZsearchradiusdefault() {
		int value = QuickStackDefaults.zSearchRadiusDefault;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.zSearchRadiusDefaultKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	public int getSearchradiusmax() {
		int value = QuickStackDefaults.searchRadiusMax;
		try 
		{
			value = Integer.parseInt(SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.searchRadiusMaxKey, "=", null));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public String getAddExclusionFlag()
	{
		String value = QuickStackDefaults.addExclusionFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.addExclusionFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.addExclusionFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public String getRemoveExclusionFlag()
	{
		String value = QuickStackDefaults.removeExclusionFlag;
		try 
		{
			value = SettingsWriter.ReadSetting(SettingType.QUICKSTACK_PLAYER, QuickStackDefaults.removeExclusionFlagKey, "=", null);
			value = (!value.equals("")) ? value : QuickStackDefaults.removeExclusionFlag;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}
	
	
}
