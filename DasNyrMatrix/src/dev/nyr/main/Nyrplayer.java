package dev.nyr.main;

import java.util.UUID;

import org.bukkit.entity.Player;

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
	public int searchRadiusMax = 20;
	
	// Identification
	public UUID uniquePlayerIdentifier;
	public String displayName;
	
	
	
	public Nyrplayer(Player player)
	{
		
	}
	
	
}
