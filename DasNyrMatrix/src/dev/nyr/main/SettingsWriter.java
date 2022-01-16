package dev.nyr.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Nullable;

import org.bukkit.Material;

public class SettingsWriter 
{
	private static final String printSignature = "[nyrmcplugin]@SettingsWriter: ";

	public enum SettingType
	{
		QUICKSTACK, QUICKSTACK_PLAYER, SELFDAMMAGE, SELFDAMMAGE_PLAYER, PLUGINSETTINGS
	}
	
	public enum WriteMode
	{
		OVERWRITE, APPEND
	}
	
	/***
	 * 
	 * @param type
	 * @param filename
	 * @return <code>true</code> if file was created or <code>false</code> if file already existed
	 * @throws Exception 
	 */
	public static boolean CreateSettingFile(SettingType type, String filename) throws Exception
	{
		try
		{
			String path;
			switch(type)
			{
				case QUICKSTACK:
					path = RequirementsCreator.FolderStructure.getQuickStackFolder();
					break;
				case QUICKSTACK_PLAYER:
					path = RequirementsCreator.FolderStructure.getQuickStackPlayerFolder();
					break;
				case SELFDAMMAGE:
					path = RequirementsCreator.FolderStructure.getSelfDammageFolder();
					break;
				case SELFDAMMAGE_PLAYER:
					path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFodler();
					break;
				default:
					System.out.println(printSignature + "CreateSettingFile: type was not recognized");
					throw new Exception(printSignature + "CreateSettingFile: type was not recognized");
			}
			File setting = new File(path + "\\" + filename + ".txt");
			if(setting.createNewFile())
			{
				System.out.println(printSignature + "Created " + filename + " @ " + path);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void ChangeSetting(SettingType type, String key, String value, String delim, @Nullable String playerName) throws Exception
	{
		try
		{
			String path = "";
			switch (type)
			{
				case QUICKSTACK:
					path = RequirementsCreator.FolderStructure.getQuickStackSettingsPath();
					break;
				case QUICKSTACK_PLAYER:
					if(playerName == null)
					{
						throw new IllegalArgumentException(printSignature + "ChangeSetting: playerName was null");
					}
					if(!playerName.endsWith(".txt"))
					{
						playerName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getQuickStackPlayerFolder() + "\\" + playerName;
					break;
				case SELFDAMMAGE:
					path = RequirementsCreator.FolderStructure.getSelfDammageSettings();
					break;
				case SELFDAMMAGE_PLAYER:
					if(playerName == null)
					{
						throw new IllegalArgumentException(printSignature + "ChangeSetting: playerName was null");
					}
					if(!playerName.endsWith(".txt"))
					{
						playerName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFodler() + "\\" + playerName;
					break;
				case PLUGINSETTINGS:
					path = RequirementsCreator.FolderStructure.getPluginSettings();
					break;
				default:
					System.out.println(printSignature + "ChangeSetting: type was not recognized");
					throw new Exception(printSignature + "ChangeSetting: type was not recognized");
			}
			File settingContainer = new File(path);
			if(settingContainer.exists())
			{
				Scanner scanner = new Scanner(settingContainer);
				String fileContent = "";
				while(scanner.hasNext())
				{
					String line = scanner.next();
					if(line.startsWith(key))
					{
						line = key + delim + value + "\n"; 
					}
					System.out.println("In change");
					fileContent += line + "\n";
					System.out.println(fileContent);
				}
				System.out.println("bitte");
				System.out.println(fileContent);
				scanner.close();
				FileWriter writer = new FileWriter(settingContainer);
				writer.write(fileContent);
				writer.close();
			}
			else
			{
				throw new FileNotFoundException(printSignature + "ChangeSetting: file not exist" + settingContainer.getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void AddSetting(SettingType type, String key, String value, String delim, @Nullable String fileName) throws Exception
	{
		try
		{
			String path = "";
			switch (type)
			{
				case QUICKSTACK:
					path = RequirementsCreator.FolderStructure.getQuickStackSettingsPath();
					break;
				case QUICKSTACK_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "AddSetting: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getQuickStackPlayerFolder() + "\\" + fileName;
					break;
				case SELFDAMMAGE:
					path = RequirementsCreator.FolderStructure.getSelfDammageSettings();
					break;
				case SELFDAMMAGE_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "AddSetting: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFodler() + "\\" + fileName;
					break;
				case PLUGINSETTINGS:
					path = RequirementsCreator.FolderStructure.getPluginSettings();
					break;
				default:
					System.out.println(printSignature + "AddSetting: type was not recognized");
					throw new Exception(printSignature + "AddSetting: type was not recognized");
			}
			File settingContainer = new File(path);
			if(settingContainer.exists())
			{
				FileWriter writer = new FileWriter(settingContainer);
				writer.append(key + delim + value + "\n");
				writer.close();
			}
			else
			{
				throw new FileNotFoundException(printSignature + "AddSetting: file not exist" + settingContainer.getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void RemoveSetting(SettingType type, String key, @Nullable String fileName) throws Exception
	{
		try
		{
			String path = "";
			switch (type)
			{
				case QUICKSTACK:
					path = RequirementsCreator.FolderStructure.getQuickStackSettingsPath();
					break;
				case QUICKSTACK_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "RemoveSetting: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getQuickStackPlayerFolder() + "\\" + fileName;
					break;
				case SELFDAMMAGE:
					path = RequirementsCreator.FolderStructure.getSelfDammageSettings();
					break;
				case SELFDAMMAGE_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "RemoveSetting: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFodler() + "\\" + fileName;
					break;
				case PLUGINSETTINGS:
					path = RequirementsCreator.FolderStructure.getPluginSettings();
					break;
				default:
					System.out.println(printSignature + "RemoveSetting: type was not recognized");
					throw new Exception(printSignature + "RemoveSettting: type was not recognized");
			}
			File settingContainer = new File(path);
			if(settingContainer.exists())
			{
				
				Scanner scanner = new Scanner(settingContainer);
				String fileContent = "";
				while(scanner.hasNext())
				{
					String line = scanner.next();
					if(line.startsWith(key))
					{
						line = ""; 
					}
					fileContent += line;
				}
				scanner.close();
				FileWriter writer = new FileWriter(settingContainer);
				writer.write(fileContent);
				writer.close();
			}
			else
			{
				throw new FileNotFoundException(printSignature + "RemoveSetting: file not exist" + settingContainer.getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static String ReadSetting(SettingType type, String key, String delim, @Nullable String fileName) throws Exception
	{
		try
		{
			String path = "";
			switch (type)
			{
				case QUICKSTACK:
					path = RequirementsCreator.FolderStructure.getQuickStackSettingsPath();
					break;
				case QUICKSTACK_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "ReadSetting: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getQuickStackPlayerFolder() + "\\" + fileName;
					break;
				case SELFDAMMAGE:
					path = RequirementsCreator.FolderStructure.getSelfDammageSettings();
					break;
				case SELFDAMMAGE_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "ReadSetting: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFodler() + "\\" + fileName;
					break;
				case PLUGINSETTINGS:
					path = RequirementsCreator.FolderStructure.getPluginSettings();
					break;
				default:
					System.out.println(printSignature + "ReadSetting: type was not recognized");
					throw new Exception(printSignature + "ReadSetting: type was not recognized");
			}
			File settingContainer = new File(path);
			if(settingContainer.exists())
			{
				Scanner scanner = new Scanner(settingContainer);
				String outValue = "";
				while(scanner.hasNext())
				{
					String line = scanner.next();
					if(line.startsWith(key + delim))
					{
						String[] keyValuePair = line.split(delim);
						outValue = (keyValuePair.length >= 2) ? keyValuePair[1] : "";
						break;
					}
				}
				scanner.close();
				return outValue;
			}
			else
			{
				throw new FileNotFoundException(printSignature + "ChangeSetting: file not exist" + settingContainer.getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static List<String> ReadFile(SettingType type, @Nullable String fileName) throws Exception
	{
		try
		{
			String path = "";
			switch (type)
			{
				case QUICKSTACK:
					path = RequirementsCreator.FolderStructure.getQuickStackSettingsPath();
					break;
				case QUICKSTACK_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "ReadFile: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getQuickStackPlayerFolder() + "\\" + fileName;
					break;
				case SELFDAMMAGE:
					path = RequirementsCreator.FolderStructure.getSelfDammageSettings();
					break;
				case SELFDAMMAGE_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "ReadFile: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFodler() + "\\" + fileName;
					break;
				case PLUGINSETTINGS:
					path = RequirementsCreator.FolderStructure.getPluginSettings();
					break;
				default:
					System.out.println(printSignature + "ReadFile: type was not recognized");
					throw new Exception(printSignature + "ReadFile: type was not recognized");
			}
			File settingContainer = new File(path);
			if(settingContainer.exists())
			{
				Scanner scanner = new Scanner(settingContainer);
				List<String> outValueList = new ArrayList<>();
				while(scanner.hasNext())
				{
					outValueList.add(scanner.next());
				}
				scanner.close();
				return outValueList;
			}
			else
			{
				throw new FileNotFoundException(printSignature + "ReadFile: file not exist" + settingContainer.getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void WriteFile(SettingType type, WriteMode mode, String[] input, @Nullable String fileName) throws Exception
	{
		try
		{
			String path = "";
			switch (type)
			{
				case QUICKSTACK:
					path = RequirementsCreator.FolderStructure.getQuickStackSettingsPath();
					break;
				case QUICKSTACK_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "WriteFile: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getQuickStackPlayerFolder() + "\\" + fileName;
					break;
				case SELFDAMMAGE:
					path = RequirementsCreator.FolderStructure.getSelfDammageSettings();
					break;
				case SELFDAMMAGE_PLAYER:
					if(fileName == null)
					{
						throw new IllegalArgumentException(printSignature + "WriteFile: fileName was null");
					}
					if(!fileName.endsWith(".txt"))
					{
						fileName += ".txt";
					}
					path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFodler() + "\\" + fileName;
					break;
				case PLUGINSETTINGS:
					path = RequirementsCreator.FolderStructure.getPluginSettings();
					break;
				default:
					System.out.println(printSignature + "WriteFile: type was not recognized");
					throw new Exception(printSignature + "WriteFile: type was not recognized");
			}
			File settingContainer = new File(path);
			if(settingContainer.exists())
			{
				FileWriter writer = new FileWriter(settingContainer);
				String value = "";
				for(String s : input)
				{
					value += s + "\n";
				}
				if(mode == WriteMode.APPEND)
				{
					writer.append(value);
				}
				else
				{
					writer.write(value);
				}
				writer.close();
			}
			else
			{
				throw new FileNotFoundException(printSignature + "WriteFile: file not exist" + settingContainer.getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
}
