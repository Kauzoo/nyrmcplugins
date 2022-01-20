package dev.nyr.main;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;

import dev.nyr.main.SettingsWriter.SettingType;

public class RequirementsCreator 
{
	private static final String printSignature = "[nyrmcplugin]: ";
	private static final String fileCreationPrint = "File created: ";
	private static final String fileExistPrint = "File exists: ";
	
	public class FolderStructure
    {
    	public static final String parentFolder = "nyrmcplugin";
    	public static final String pluginSettings = File.separator + "nyrmcplugin-settings.txt";
    	public static final String quickStackFolder = File.separator + "quickstack";
    	public static final String selfDammageFolder = File.separator + "selfdammage";
    	public static final String quickStackSettings = File.separator + "quickstack-settings.txt";
    	public static final String selfDammageSettings = File.separator + "selfdammage-settings.txt";
    	public static final String quickStackPlayerFolder = File.separator + "players";
    	public static final String selfDammagePlayerFolder = File.separator + "players";
    	public static final String quickStackExcludes = File.separator + "quickstack-default-excludes.txt";
    	
    	public static String getQuickStackSettingsPath()
    	{
    		return parentFolder + quickStackFolder + quickStackSettings;
    	}
    	
    	public static String getSelfDammageSettings()
    	{
    		return parentFolder + selfDammageFolder + selfDammageSettings;
    	}
    	
    	public static String getQuickStackPlayerFolder()
    	{
    		return parentFolder + quickStackFolder + quickStackPlayerFolder;
    	}
    	
    	public static String getSelfDammagePlayerFolder()
    	{
    		return parentFolder + selfDammageFolder + selfDammagePlayerFolder;
    	}
    	
    	public static String getQuickStackFolder()
    	{
    		return parentFolder + quickStackFolder;
    	}
    	
    	public static String getSelfDammageFolder()
    	{
    		return parentFolder + selfDammageFolder;
    	}
    	
    	public static String getPluginSettings()
    	{
    		return parentFolder + pluginSettings;
    	}
    	
    	public static String getQuickStackExcludes()
    	{
    		return parentFolder + quickStackFolder + quickStackExcludes;
    	}
    }
	
	public RequirementsCreator()
	{
		
	}
	
    public static void CreateRequirements()
    {
    	System.out.println(printSignature + "Creating Requirements");
    	// Try creating relevant directories and files
    	try
    	{
    		File nyrmcpluginFolder = new File(FolderStructure.parentFolder);
    		File quickStackFolder = new File(FolderStructure.getQuickStackFolder());
    		File selfDammageFolder = new File(FolderStructure.getSelfDammageFolder());
    		File nyrmcpluginSettings = new File(FolderStructure.getPluginSettings());
    		File quickStackSettings = new File(FolderStructure.getQuickStackSettingsPath());
    		File selfDammageSettings = new File(FolderStructure.getSelfDammageSettings());
    		File quickStackPlayerFolder = new File(FolderStructure.getQuickStackPlayerFolder());
    		File selfDammagePlayerFolder = new File(FolderStructure.getSelfDammagePlayerFolder());
    		File quickStackExcludes = new File(FolderStructure.getQuickStackExcludes());
    		    		
			// Create Files
			System.out.println((nyrmcpluginFolder.mkdir()) ? printSignature + fileCreationPrint + nyrmcpluginFolder.getName() : printSignature + fileExistPrint + nyrmcpluginFolder.getName());
			System.out.println((quickStackFolder.mkdir()) ? printSignature + fileCreationPrint + quickStackFolder.getName() : printSignature + fileExistPrint + quickStackFolder.getName());
			System.out.println((selfDammageFolder.mkdir()) ? printSignature + fileCreationPrint + selfDammageFolder.getName() : printSignature + fileExistPrint + selfDammageFolder.getName());
			//System.out.println((nyrmcpluginSettings.createNewFile()) ? printSignature + fileCreationPrint + nyrmcpluginSettings.getName() : printSignature + fileExistPrint + nyrmcpluginSettings.getName());
			//System.out.println((quickStackSettings.createNewFile()) ? printSignature + fileCreationPrint + quickStackSettings.getName() : printSignature + fileExistPrint + quickStackSettings.getName());
			System.out.println((selfDammageSettings.createNewFile()) ? printSignature + fileCreationPrint + selfDammageSettings.getName() : printSignature + fileExistPrint + selfDammageSettings.getName());
			System.out.println((quickStackPlayerFolder.mkdir()) ? printSignature + fileCreationPrint + quickStackPlayerFolder.getName() : printSignature + fileExistPrint + quickStackPlayerFolder.getName());
			System.out.println((selfDammagePlayerFolder.mkdir()) ? printSignature + fileCreationPrint + selfDammagePlayerFolder.getName() : printSignature + fileExistPrint + selfDammagePlayerFolder.getName());
			//System.out.println((quickStackExcludes.createNewFile()) ? printSignature + fileCreationPrint + quickStackExcludes.getName() : printSignature + fileExistPrint + quickStackExcludes.getName());
			
			if(nyrmcpluginSettings.createNewFile())
			{
				System.out.println(printSignature + fileCreationPrint + nyrmcpluginSettings.getName());
				InitializePluginSettings(nyrmcpluginSettings);
				System.out.println(printSignature + "Initialized " + nyrmcpluginSettings.getName());
			}
			else
			{
				System.out.println(printSignature + fileExistPrint + nyrmcpluginSettings.getName());
			}
			
			if(quickStackSettings.createNewFile())
			{
				System.out.println(printSignature + fileCreationPrint + quickStackSettings.getName());
				InitializeQuickStackSettings(quickStackSettings);
				System.out.println(printSignature + "Initialized " + quickStackSettings.getName());
			}
			else
			{
				System.out.println(printSignature + fileExistPrint + quickStackSettings.getName());
			}
			
			if(quickStackExcludes.createNewFile())
			{
				System.out.println(printSignature + fileCreationPrint + quickStackExcludes.getName());
				InitializeQuickStackExclude(quickStackExcludes);
				System.out.println(printSignature + "Initialized " + quickStackSettings.getName());
			}
			else
			{
				System.out.println(printSignature + fileExistPrint + quickStackSettings.getName());
			}
    	}
    	catch(Exception e)
    	{
    		System.out.println(printSignature + "Something went wrong during CreateRequirements");
    		e.printStackTrace();
    	}
    }
    
    /***
     * Deletes all required folders and files
     * Might lead to problems if done while running
     */
    public static void HardResetRequirements()
    {
    	try
    	{
    		File nyrmcpluginFolder = new File(FolderStructure.parentFolder);
    		
    		System.out.println(printSignature + "Resetting requirements");
    		if(nyrmcpluginFolder.exists())
    		{
    			if(nyrmcpluginFolder.delete())
        		{
        			System.out.println(printSignature + "Deleted " + nyrmcpluginFolder.getName());
        			CreateRequirements();
        		}
        		else
        		{
        			System.out.println(printSignature + "Failed to delete " + nyrmcpluginFolder.getName());
        			CreateRequirements();
        		}
    		}
    		else
    		{
    			System.out.println(printSignature + "Folder did not exist " + nyrmcpluginFolder.getName());
    			CreateRequirements();
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println(printSignature + "Something went wrong while attempting HardResetRequirements");
    		e.printStackTrace();
    	}
    }
    
    public static boolean ForceGlobalSettingReinitialiazation(@Nullable Player player)
    {
    	if(TryReinitializePluginSettings(player) && TryReinitializeQuickStackSettings(player) && TryReinitializeQuickStackExcludeSettings(player))
    	{
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Successfully reset all Files");
    		}
    		System.out.println(printSignature + "Successfully reset all Files");
    		return true;
    	}
    	if(!player.equals(null))
		{
			player.sendMessage(printSignature + "Failed to reset all Files");
		}
		System.out.println(printSignature + "Failed to reset all Files");
    	return false;
    }
    
    public static boolean TryReinitializePluginSettings(@Nullable Player player)
    {
    	try
    	{
    		
    		File file = new File(FolderStructure.getPluginSettings());
    		System.out.println("Trying to reinitialize: " + file.getName());
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Trying to reinitialize: " + file.getName());
    		}
    		InitializePluginSettings(file);
    		System.out.println(printSignature + "Initialized " + file.getName());
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Initialized " + file.getName());
    		}
    		return true;
    	}
    	catch (Exception e)
    	{
    		System.out.println(printSignature + "Something went wrong during TryReinitializePluginSettings");
    		if(!player.equals(null))
    		{
    			player.sendMessage();
    		}
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public static boolean TryReinitializeQuickStackSettings(@Nullable Player player)
    {
    	try
    	{
    		
    		File file = new File(FolderStructure.getQuickStackSettingsPath());
    		System.out.println("Trying to reinitialize: " + file.getName());
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Trying to reinitialize: " + file.getName());
    		}
    		InitializePluginSettings(file);
    		System.out.println(printSignature + "Initialized " + file.getName());
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Initialized " + file.getName());
    		}
    		return true;
    	}
    	catch (Exception e)
    	{
    		System.out.println(printSignature + "Something went wrong during TryReinitializeQuickStackSettings");
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Something went wrong during TryReinitializeQuickStackSettings");
    		}
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public static boolean TryReinitializeQuickStackExcludeSettings(@Nullable Player player)
    {
    	try
    	{
    		
    		File file = new File(FolderStructure.getQuickStackSettingsPath());
    		System.out.println("Trying to reinitialize: " + file.getName());
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Trying to reinitialize: " + file.getName());
    		}
    		InitializePluginSettings(file);
    		System.out.println(printSignature + "Initialized " + file.getName());
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Initialized " + file.getName());
    		}
    		return true;
    	}
    	catch (Exception e)
    	{
    		System.out.println(printSignature + "Something went wrong during TryReinitializeQuickStackExcludeSettings");
    		if(!player.equals(null))
    		{
    			player.sendMessage(printSignature + "Something went wrong during TryReinitializeQuickStackExcludeSettings");
    		}
    		e.printStackTrace();
    		return false;
    	}
    }
    
    /***
     * Deletes all required folders and files but attempts to save user files
     */
    public void SoftResetRequirements()
    {
    	return;
    }
    
    private static void InitializePluginSettings(File file)
    {
    	// KeyValuePairs
    	String pluginSettingsStrings =  UsefullListener.PluginCommandDefaults.pluginSettingsStringKey + "=" + UsefullListener.PluginCommandDefaults.pluginSettingsString;
    	String pluginEnableQuickStackFlag =  UsefullListener.PluginCommandDefaults.pluginEnableQuickStackFlagKey + "=" + UsefullListener.PluginCommandDefaults.pluginEnableQuickStackFlag;
    	String pluginEnableSelfDammageFlag =  UsefullListener.PluginCommandDefaults.pluginEnableSelfDammageFlagKey + "=" + UsefullListener.PluginCommandDefaults.pluginEnableSelfDammageFlag;
    	String pluginOptionsDelim =  UsefullListener.PluginCommandDefaults.pluginOptionsDelimKey + "=" + UsefullListener.PluginCommandDefaults.pluginOptionsDelim;
    	String pluginHelpFlag = UsefullListener.PluginCommandDefaults.pluginHelpFlagKey + "=" + UsefullListener.PluginCommandDefaults.pluginHelpFlag;
    	String enableQuickStack =  UsefullListener.PluginCommandDefaults.enableQuickStackKey + "=" + UsefullListener.PluginCommandDefaults.enableQuickStack;
    	String enableSelfDammage =  UsefullListener.PluginCommandDefaults.enableSelfDammageKey + "=" + UsefullListener.PluginCommandDefaults.enableSelfDammage;
    	
    	String fullFile = pluginSettingsStrings + "\n" + pluginEnableQuickStackFlag + "\n" + pluginEnableSelfDammageFlag + "\n"
    					+ pluginOptionsDelim + "\n" + pluginHelpFlag + "\n" + enableQuickStack + "\n" + enableSelfDammage;
    	try
    	{
    		FileWriter writer = new FileWriter(file);
    		writer.write(fullFile);
    		writer.close();
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private static void InitializeQuickStackSettings(File file)
    {
    	// KeyValuePairs
    	String enableQuickStackString =  UsefullListener.QuickStackDefaults.enableQuickStackStringKey + "=" + UsefullListener.QuickStackDefaults.enableQuickStackString; 
		String enableDebugFlag = UsefullListener.QuickStackDefaults.enableDebugFlagKey + "=" + UsefullListener.QuickStackDefaults.enableDebugFlag;
		String xValueFlag = UsefullListener.QuickStackDefaults.xValueFlagKey + "=" + UsefullListener.QuickStackDefaults.xValueFlag;
		String yValueFlag = UsefullListener.QuickStackDefaults.yValueFlagKey + "=" + UsefullListener.QuickStackDefaults.yValueFlag;
		String zValueFlag = UsefullListener.QuickStackDefaults.zValueFlagKey + "=" + UsefullListener.QuickStackDefaults.zValueFlag;
		String sideValueFlag = UsefullListener.QuickStackDefaults.sideValueFlagKey + "=" + UsefullListener.QuickStackDefaults.sideValueFlag;
		String valueFlagDelim = UsefullListener.QuickStackDefaults.valueFlagDelimKey + "=" + UsefullListener.QuickStackDefaults.valueFlagDelim;
		String addExclusionFlag = UsefullListener.QuickStackDefaults.addExclusionFlagKey + "=" + UsefullListener.QuickStackDefaults.addExclusionFlag;
		String removeExclusionFlag = UsefullListener.QuickStackDefaults.removeExclusionFlagKey + "=" + UsefullListener.QuickStackDefaults.removeExclusionFlag;
		String quickStackHelpFlag = UsefullListener.QuickStackDefaults.quickStackHelpFlagKey + "=" + UsefullListener.QuickStackDefaults.quickStackHelpFlag;
		String xSearchRadiusDefault = UsefullListener.QuickStackDefaults.xSearchRadiusDefaultKey + "=" + UsefullListener.QuickStackDefaults.xSearchRadiusDefault;
		String ySearchRadiusDefault = UsefullListener.QuickStackDefaults.ySearchRadiusDefaultKey + "=" + UsefullListener.QuickStackDefaults.ySearchRadiusDefault;
		String zSearchRadiusDefault = UsefullListener.QuickStackDefaults.zSearchRadiusDefaultKey + "=" + UsefullListener.QuickStackDefaults.zSearchRadiusDefault;
		String searchRadiusMax = UsefullListener.QuickStackDefaults.searchRadiusMaxKey + "=" + UsefullListener.QuickStackDefaults.searchRadiusMax;
		
		String fullFile = enableQuickStackString + "\n" + enableDebugFlag + "\n" + xValueFlag + "\n" + yValueFlag + "\n" + zValueFlag + "\n" + sideValueFlag + "\n"
				+ valueFlagDelim + "\n" + addExclusionFlag + "\n" + removeExclusionFlag + "\n" + quickStackHelpFlag + "\n" + xSearchRadiusDefault + "\n" + ySearchRadiusDefault + "\n" + zSearchRadiusDefault + "\n"
				+ searchRadiusMax;
		try
    	{
    		FileWriter writer = new FileWriter(file);
    		writer.write(fullFile);
    		writer.close();
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /***
     * 
     */
    private static void InitializeQuickStackExclude(File file)
    {
    	String fullContent = "";
    	for(String s : MinecraftDataContainer.ToolArmor.getToolArmorList())
    	{
    		fullContent += UsefullListener.QuickStackDefaults.quickStackExludeKey + "=" + s + "\n";
    	}
    	try
    	{
    		FileWriter writer = new FileWriter(file);
    		writer.write(fullContent);
    		writer.close();
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public static void InitializePlayerQuickStackExclude(Player player)
    {
    	try
		{
			List<String> excludes = SettingsWriter.ReadFile(SettingType.QUICKSTACK_EXCLUDES, null);
			SettingsWriter.WriteFile(SettingType.QUICKSTACK_PLAYER, false, (String[]) excludes.toArray(new String[excludes.size()]), player.getDisplayName());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
