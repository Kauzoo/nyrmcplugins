package dev.nyr.main;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.nio.*;
import java.nio.file.FileSystem;

import org.bukkit.entity.Player;

import dev.nyr.main.SettingsWriter.SettingType;

public class RequirementsCreator 
{
	private static final String printSignature = "[nyrmcplugin]: ";
	private static final String fileCreationPrint = "File created: ";
	private static final String fileExistPrint = "File exists: ";
	
	private File nyrmcpluginFolder = new File(FolderStructure.parentFolder);
	private File quickStackFolder = new File(FolderStructure.getQuickStackFolder());
	private File selfDammageFolder = new File(FolderStructure.getSelfDammageFolder());
	private File nyrmcpluginSettings = new File(FolderStructure.getPluginSettings());
	private File quickStackSettings = new File(FolderStructure.getQuickStackSettingsPath());
	private File selfDammageSettings = new File(FolderStructure.getSelfDammageSettings());
	private File quickStackPlayerFolder = new File(FolderStructure.getQuickStackPlayerFolder());
	private File selfDammagePlayerFolder = new File(FolderStructure.getSelfDammagePlayerFolder());
	private File quickStackExcludes = new File(FolderStructure.getQuickStackExcludes());
	
	
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
	
    public void CreateRequirements()
    {
    	System.out.println(printSignature + "Creating Requirements");
    	// Try creating relevant directories and files
    	try
    	{
			// Create Files
			System.out.println((nyrmcpluginFolder.createNewFile()) ? printSignature + fileCreationPrint + nyrmcpluginFolder.getName() : printSignature + fileExistPrint + nyrmcpluginFolder.getName());
			System.out.println((quickStackFolder.createNewFile()) ? printSignature + fileCreationPrint + quickStackFolder.getName() : printSignature + fileExistPrint + quickStackFolder.getName());
			System.out.println((selfDammageFolder.createNewFile()) ? printSignature + fileCreationPrint + selfDammageFolder.getName() : printSignature + fileExistPrint + selfDammageFolder.getName());
			System.out.println((nyrmcpluginSettings.createNewFile()) ? printSignature + fileCreationPrint + nyrmcpluginSettings.getName() : printSignature + fileExistPrint + nyrmcpluginSettings.getName());
			System.out.println((quickStackSettings.createNewFile()) ? printSignature + fileCreationPrint + quickStackSettings.getName() : printSignature + fileExistPrint + quickStackSettings.getName());
			System.out.println((selfDammageSettings.createNewFile()) ? printSignature + fileCreationPrint + selfDammageSettings.getName() : printSignature + fileExistPrint + selfDammageSettings.getName());
			System.out.println((quickStackPlayerFolder.createNewFile()) ? printSignature + fileCreationPrint + quickStackPlayerFolder.getName() : printSignature + fileExistPrint + quickStackPlayerFolder.getName());
			System.out.println((selfDammagePlayerFolder.createNewFile()) ? printSignature + fileCreationPrint + selfDammagePlayerFolder.getName() : printSignature + fileExistPrint + selfDammagePlayerFolder.getName());
			System.out.println((quickStackExcludes.createNewFile()) ? printSignature + fileCreationPrint + quickStackExcludes.getName() : printSignature + fileExistPrint + quickStackExcludes.getName());
			
			// Initialize file content
			InitializePluginSettings(nyrmcpluginSettings);
			InitializeQuickStackSettings(quickStackSettings);
			InitializeQuickStackExclude(quickStackExcludes);
			System.out.println(printSignature + "Initialized " + nyrmcpluginSettings.getName());
			System.out.println(printSignature + "Initialized " + quickStackSettings.getName());
			System.out.println(printSignature + "Initialized " + quickStackSettings.getName());
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
    public void HardResetRequirements()
    {
    	try
    	{
    		System.out.println(printSignature + "Resetting requirements");
    		if(nyrmcpluginFolder.exists())
    		{
    			if(nyrmcpluginFolder.delete())
        		{
        			System.out.println(printSignature + "Deleted " + nyrmcpluginFolder.getName());
        			this.CreateRequirements();
        		}
        		else
        		{
        			System.out.println(printSignature + "Failed to delete " + nyrmcpluginFolder.getName());
        			this.CreateRequirements();
        		}
    		}
    		else
    		{
    			System.out.println(printSignature + "Folder did not exist " + nyrmcpluginFolder.getName());
    			this.CreateRequirements();
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println(printSignature + "Something went wrong while attempting HardResetRequirements");
    		e.printStackTrace();
    	}
    }
    
    /***
     * Deletes all required folders and files but attempts to save user files
     */
    public void SoftResetRequirements()
    {
    	return;
    }
    
    public static void InitializePluginSettings(File file)
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
    
    public static void InitializeQuickStackSettings(File file)
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
    public static void InitializeQuickStackExclude(File file)
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
