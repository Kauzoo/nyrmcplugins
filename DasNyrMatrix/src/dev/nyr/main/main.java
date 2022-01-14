package dev.nyr.main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


import org.bukkit.plugin.PluginManager;

public class main extends JavaPlugin{
    @SuppressWarnings("unused")
    private static main plugin;

    public void onEnable() 
    {
        System.out.println("Hallo Welt!");

        plugin=this;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new UsefullListener(),this);
        
    }
}