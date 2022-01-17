package dev.nyr.main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.*;
import java.util.Scanner;

import org.bukkit.plugin.PluginManager;

public class main extends JavaPlugin{
    @SuppressWarnings("unused")
    private static main plugin;
    
    public void onEnable() 
    {
        System.out.println("Running nyrmcplugin");

        plugin=this;
        
        RequirementsCreator.CreateRequirements();
        //UsefullListener.printDebugInfo();
        
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new UsefullListener(),this);
        
    }
}