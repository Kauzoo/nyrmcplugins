/**
 * 
 */
package dev.nyr.utilities;

import java.io.*;
import java.nio.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
 * @author nyr
 * 
 * Small abstract logging class
 *
 */
public abstract class Logger
{
	final Path logsFolderPath;
	String printMarker = "";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	Logger(Path logsFolderPathIn)
	{
		this.logsFolderPath = logsFolderPathIn;
		
	}
	
	Logger(Path logsFolderPathIn, String printMarker)
	{
		this.logsFolderPath = logsFolderPathIn;
	}
	
	Logger(String logsFolderPathIn) 
	{
		this.logsFolderPath = Path.of(logsFolderPathIn);
	}
	
	
	
	public boolean log(String message)
	{
		return true;
	}
	
	public boolean logWarning(String message)
	{
		return true;
	}
	
	public boolean logError(String message)
	{
		return true;
	}

}
