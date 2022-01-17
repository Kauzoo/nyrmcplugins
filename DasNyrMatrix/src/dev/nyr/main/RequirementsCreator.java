package dev.nyr.main;

import java.io.File;
import java.io.FileWriter;

public class RequirementsCreator 
{

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
    	
    	public static String getSelfDammagePlayerFodler()
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
    }
	
    public static void CreateRequirements()
    {
    	// Try creating relevant directories
    	try
    	{
    		// Try create nyrmcplugin parent folder
    		File nyrmcpluginFolder = new File(FolderStructure.parentFolder);
    		if(nyrmcpluginFolder.exists())
    		{
    			System.out.println("[nyrmcplugin]: Folder exists " + nyrmcpluginFolder.getName());
    			// Check if all relevant Folders exist
    			File nyrmcpluginSettings = new File(FolderStructure.parentFolder + FolderStructure.pluginSettings);
    			File quickStackSettings = new File(FolderStructure.parentFolder + FolderStructure.quickStackSettings);
    			File selfDammageSettings = new File(FolderStructure.parentFolder + FolderStructure.selfDammageSettings);
    			File quickStackFolder = new File(FolderStructure.parentFolder + FolderStructure.quickStackFolder);
    			File selfDammageFolder = new File(FolderStructure.parentFolder + FolderStructure.selfDammageFolder);
    			File quickStackPlayerFolder = new File(FolderStructure.parentFolder + FolderStructure.quickStackPlayerFolder);
    			File selfDammagePlayerFolder = new File(FolderStructure.parentFolder + FolderStructure.selfDammagePlayerFolder);
    			
    			System.out.println((quickStackSettings.exists()) ? "[nyrmcplugin]: File exists: " + FolderStructure.quickStackSettings : "[nyrmcplugin]: File not exists " + FolderStructure.quickStackSettings);
    			System.out.println((selfDammageSettings.exists()) ? "[nyrmcplugin]: File exists: " + FolderStructure.selfDammageSettings : "[nyrmcplugin]: File not exists " + FolderStructure.selfDammageSettings);
    			System.out.println((quickStackFolder.exists()) ? "[nyrmcplugin]: File exists: " + FolderStructure.quickStackFolder : "[nyrmcplugin]: File not exists " + FolderStructure.quickStackFolder);
    			System.out.println((selfDammageFolder.exists()) ? "[nyrmcplugin]: File exists: " + FolderStructure.selfDammageFolder : "[nyrmcplugin]: File not exists " + FolderStructure.selfDammageFolder);
    			System.out.println((quickStackPlayerFolder.exists()) ? "[nyrmcplugin]: File exists: " + FolderStructure.quickStackPlayerFolder : "[nyrmcplugin]: File not exists " + FolderStructure.quickStackPlayerFolder);
    			System.out.println((selfDammagePlayerFolder.exists()) ? "[nyrmcplugin]: File exists: " + FolderStructure.selfDammagePlayerFolder : "[nyrmcplugin]: File not exists " + FolderStructure.selfDammagePlayerFolder);
    			System.out.println("[nyrmcplugin]: If something stops working delete parent folder.");
    		}
    		else if(nyrmcpluginFolder.mkdir())
    		{
    			System.out.println("[nyrmcplugin]: Created folder " + nyrmcpluginFolder.getName());
    			
    			// Try create nyrmcplugin-settings file
    			File nyrmcpluginSettings = new File(nyrmcpluginFolder.getAbsolutePath() + FolderStructure.pluginSettings);
    			System.out.println((nyrmcpluginSettings.createNewFile()) ? "[nyrmcplugin]: Created file " + nyrmcpluginSettings.getName() : "[nyrmcplugin]: Failed to create file " + nyrmcpluginSettings.getName());
    			// Initialize PluginSettings
    			InitializePluginSettings(nyrmcpluginSettings);
    			System.out.println("[nyrmcplugin]: Initialized " + nyrmcpluginSettings.getName());
    			
    			// Try create quickstack and selfdammage subfolder
    			File quickStackFolder = new File(nyrmcpluginFolder.getAbsolutePath() + FolderStructure.quickStackFolder);
    			File selfDammageFolder = new File(nyrmcpluginFolder.getAbsolutePath() + FolderStructure.selfDammageFolder);
    			if(quickStackFolder.mkdir() && selfDammageFolder.mkdir())
    			{
    				System.out.println("[nyrmcplugin]: Created folder " + selfDammageFolder.getName());
    				System.out.println("[nyrmcplugin]: Created folder " + quickStackFolder.getName());
    				
    				// Try create quickstack-settings and selfdammage-settings
    				File quickStackSettings = new File(quickStackFolder.getAbsolutePath() + FolderStructure.quickStackSettings);
        			File selfDammageSettings = new File(selfDammageFolder.getAbsolutePath() + FolderStructure.selfDammageSettings);
    				System.out.println((quickStackSettings.createNewFile()) ? "[nyrmcplugin]: Created file " + quickStackSettings.getName() : "[nyrmcplugin]: Failed to create file " + quickStackSettings.getName());
    				System.out.println((selfDammageSettings.createNewFile()) ? "[nyrmcplugin]: Created file " + selfDammageSettings.getName() : "[nyrmcplugin]: Failed to create file " + selfDammageSettings.getName());
    				// Initialize quickstack settings
    				InitializeQuickStackSettings(quickStackSettings);
    				System.out.println("[nyrmcplugin]: Initialized " + quickStackSettings.getName());
    				
    				
    				// Try create players folder
    				File quickStackPlayerFolder = new File(quickStackFolder.getAbsolutePath() + FolderStructure.quickStackPlayerFolder);
    				File selfDammagePlayerFolder = new File(selfDammageFolder.getAbsolutePath() + FolderStructure.selfDammagePlayerFolder);
    				System.out.println((quickStackPlayerFolder.mkdir()) ? "[nyrmcplugin]: Created folder " + quickStackPlayerFolder.getName() : "[nyrmcplugin]: Failed to create file " + quickStackPlayerFolder.getName());
    				System.out.println((selfDammagePlayerFolder.mkdir()) ? "[nyrmcplugin]: Created folder " + selfDammagePlayerFolder.getName() : "[nyrmcplugin]: Failed to create file " + selfDammagePlayerFolder.getName());
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("[nyrmcplugin]: Something went wrong during CreateRequirements");
    		e.printStackTrace();
    	}
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
		String quickStackHelpFlag = UsefullListener.QuickStackDefaults.quickStackHelpFlagKey + "=" + UsefullListener.QuickStackDefaults.quickStackHelpFlag;
		String xSearchRadiusDefault = UsefullListener.QuickStackDefaults.xSearchRadiusDefaultKey + "=" + UsefullListener.QuickStackDefaults.xSearchRadiusDefault;
		String ySearchRadiusDefault = UsefullListener.QuickStackDefaults.ySearchRadiusDefaultKey + "=" + UsefullListener.QuickStackDefaults.ySearchRadiusDefault;
		String zSearchRadiusDefault = UsefullListener.QuickStackDefaults.zSearchRadiusDefaultKey + "=" + UsefullListener.QuickStackDefaults.zSearchRadiusDefault;
		String searchRadiusMax = UsefullListener.QuickStackDefaults.searchRadiusMaxKey + "=" + UsefullListener.QuickStackDefaults.searchRadiusMax;
		
		String fullFile = enableQuickStackString + "\n" + enableDebugFlag + "\n" + xValueFlag + "\n" + yValueFlag + "\n" + zValueFlag + "\n" + sideValueFlag + "\n"
				+ valueFlagDelim + "\n" + quickStackHelpFlag + "\n" + xSearchRadiusDefault + "\n" + ySearchRadiusDefault + "\n" + zSearchRadiusDefault + "\n"
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
}
