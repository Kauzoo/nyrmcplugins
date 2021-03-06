package dev.nyr.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Nullable;

public class SettingsWriter
{
	private static final String printSignature = "[nyrmcplugin]@SettingsWriter: ";

	public enum SettingType
	{
		QUICKSTACK, QUICKSTACK_PLAYER, SELFDAMMAGE, SELFDAMMAGE_PLAYER, PLUGINSETTINGS, QUICKSTACK_EXCLUDES
	}

	/***
	 * 
	 * @param type
	 * @param filename
	 * @return <code>true</code> if file was created or <code>false</code> if file
	 *         already existed
	 * @throws Exception
	 */
	public static boolean CreateSettingFile(SettingType type, String filename) throws Exception
	{
		try
		{
			String path;
			switch (type)
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
				path = RequirementsCreator.FolderStructure.getSelfDammagePlayerFolder();
				break;
			default:
				System.out.println(printSignature + "CreateSettingFile: type was not recognized");
				throw new Exception(printSignature + "CreateSettingFile: type was not recognized");
			}
			File setting = new File(path + File.separator + filename + ".txt");
			if (setting.createNewFile())
			{
				System.out.println(printSignature + "Created " + filename + " @ " + path);
				return true;
			} else
			{
				return false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/***
	 * 
	 * @param type
	 * @param key
	 * @param value
	 * @param delim
	 * @param playerName
	 * @throws Exception
	 */
	public static void ChangeSetting(SettingType type, String key, String value, String delim,
			@Nullable String playerName) throws Exception
	{
		String path = CreatePath(type, playerName, "ChangeSetting:");
		try
		{
			File settingContainer = new File(path);
			if (settingContainer.exists())
			{
				Scanner scanner = new Scanner(settingContainer);
				String fileContent = "";
				while (scanner.hasNext())
				{
					String line = scanner.next();
					if (line.startsWith(key))
					{
						line = key + delim + value;
					}
					fileContent += line + "\n";
				}
				scanner.close();
				FileWriter writer = new FileWriter(settingContainer);
				writer.write(fileContent);
				writer.close();
			} else
			{
				throw new FileNotFoundException(
						printSignature + "ChangeSetting: file not exist" + settingContainer.getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/***
	 * 
	 * @param type
	 * @param key
	 * @param value
	 * @param delim
	 * @param fileName
	 * @throws Exception
	 */
	public static void AddSetting(SettingType type, String key, String value, String delim, @Nullable String fileName)
			throws Exception
	{
		String path = CreatePath(type, fileName, "AddSetting:");
		try
		{
			File settingContainer = new File(path);
			if (settingContainer.exists())
			{
				FileWriter writer = new FileWriter(settingContainer, true);
				writer.write(key + delim + value + "\n");
				writer.close();
			} else
			{
				throw new FileNotFoundException(
						printSignature + "AddSetting: file not exist" + settingContainer.getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/***
	 * 
	 * @param type
	 * @param key
	 * @param fileName
	 * @throws Exception
	 */
	public static void RemoveSetting(SettingType type, String key, @Nullable String fileName) throws Exception
	{
		String path = CreatePath(type, fileName, "RemoveSetting:");
		try
		{
			File settingContainer = new File(path);
			if (settingContainer.exists())
			{

				Scanner scanner = new Scanner(settingContainer);
				String fileContent = "";
				while (scanner.hasNext())
				{
					String line = scanner.next();
					if (line.startsWith(key))
					{
						line = "";
					}
					fileContent += line + "\n";
				}
				scanner.close();
				FileWriter writer = new FileWriter(settingContainer);
				writer.write(fileContent);
				writer.close();
			} else
			{
				throw new FileNotFoundException(
						printSignature + "RemoveSetting: file not exist" + settingContainer.getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/***
	 * 
	 * @param type
	 * @param key
	 * @param delim
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String ReadSetting(SettingType type, String key, String delim, @Nullable String fileName)
			throws Exception
	{
		String path = CreatePath(type, fileName, "ReadSetting:");
		try
		{
			File settingContainer = new File(path);
			if (settingContainer.exists())
			{
				Scanner scanner = new Scanner(settingContainer);
				String outValue = "";
				while (scanner.hasNext())
				{
					String line = scanner.next();
					if (line.startsWith(key + delim))
					{
						String[] keyValuePair = line.split(delim);
						outValue = (keyValuePair.length >= 2) ? keyValuePair[1] : "";
						break;
					}
				}
				scanner.close();
				return outValue;
			} else
			{
				throw new FileNotFoundException(
						printSignature + "ChangeSetting: file not exist" + settingContainer.getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/***
	 * 
	 * @param type
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List<String> ReadFile(SettingType type, @Nullable String fileName) throws Exception
	{
		String path = CreatePath(type, fileName, "ReadFile:");
		try
		{
			File settingContainer = new File(path);
			if (settingContainer.exists())
			{
				Scanner scanner = new Scanner(settingContainer);
				List<String> outValueList = new ArrayList<>();
				while (scanner.hasNext())
				{
					outValueList.add(scanner.next());
				}
				scanner.close();
				return outValueList;
			} else
			{
				throw new FileNotFoundException(
						printSignature + "ReadFile: file not exist" + settingContainer.getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/***
	 * 
	 * @param type
	 * @param mode
	 * @param input
	 * @param fileName
	 * @throws Exception
	 */
	public static void WriteFile(SettingType type, boolean appendMode, String[] input, @Nullable String fileName)
			throws Exception
	{
		String path = CreatePath(type, fileName, "WriteFile:");
		try
		{
			File settingContainer = new File(path);
			if (settingContainer.exists())
			{
				FileWriter writer = new FileWriter(settingContainer, appendMode);
				String value = "";
				for (String s : input)
				{
					value += s + "\n";
				}
				writer.write(value);
				writer.close();
			} else
			{
				throw new FileNotFoundException(
						printSignature + "WriteFile: file not exist" + settingContainer.getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static boolean CheckFileExistence(SettingType type, @Nullable String fileName)
	{
		String path = CreatePath(type, fileName, "CheckFileExistence:");
		try
		{
			File file = new File(path);
			return file.exists();
		}
		catch (Exception e)
		{
			System.out.println(printSignature + "Something went wrong during file existence check");
			e.printStackTrace();
			return false;
		}
	}

	/***
	 * Helper method
	 * 
	 * @param type
	 */
	private static String CreatePath(SettingType type, @Nullable String fileName, String methodIdentifier)
	{
		try
		{
			switch (type)
			{
			case QUICKSTACK:
				return RequirementsCreator.FolderStructure.getQuickStackSettingsPath();
			case QUICKSTACK_PLAYER:
				if (fileName == null)
				{
					throw new IllegalArgumentException(printSignature + methodIdentifier + " fileName was null");
				}
				if (!fileName.endsWith(".txt"))
				{
					fileName += ".txt";
				}
				return RequirementsCreator.FolderStructure.getQuickStackPlayerFolder() + File.separator + fileName;
			case SELFDAMMAGE:
				return RequirementsCreator.FolderStructure.getSelfDammageSettings();
			case SELFDAMMAGE_PLAYER:
				if (fileName == null)
				{
					throw new IllegalArgumentException(printSignature + methodIdentifier + " fileName was null");
				}
				if (!fileName.endsWith(".txt"))
				{
					fileName += ".txt";
				}
				return RequirementsCreator.FolderStructure.getSelfDammagePlayerFolder() + File.separator + fileName;
			case PLUGINSETTINGS:
				return RequirementsCreator.FolderStructure.getPluginSettings();
			case QUICKSTACK_EXCLUDES:
				return RequirementsCreator.FolderStructure.getQuickStackExcludes();
			default:
				throw new Exception(printSignature + methodIdentifier + " type was not recognized");
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "";
		} catch (Exception e)
		{
			System.out.println(printSignature + methodIdentifier + "Something went wrong in CreatePath");
			e.printStackTrace();
			return "";
		}
	}
}
