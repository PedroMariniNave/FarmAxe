package com.zpedroo.farmaxe;

import com.zpedroo.farmaxe.commands.FarmAxeCmd;
import com.zpedroo.farmaxe.hooks.PlaceholderAPIHook;
import com.zpedroo.farmaxe.listeners.FarmAxeListeners;
import com.zpedroo.farmaxe.listeners.PlayerGeneralListeners;
import com.zpedroo.farmaxe.listeners.PointsListeners;
import com.zpedroo.farmaxe.managers.DataManager;
import com.zpedroo.farmaxe.utils.FileUtils;
import com.zpedroo.farmaxe.utils.cooldown.Cooldown;
import com.zpedroo.farmaxe.utils.formatter.NumberFormatter;
import com.zpedroo.farmaxe.utils.menu.Menus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import static com.zpedroo.farmaxe.utils.config.Settings.ALIASES;
import static com.zpedroo.farmaxe.utils.config.Settings.COMMAND;

public class FarmAxe extends JavaPlugin {

    private static FarmAxe instance;
    public static FarmAxe get() { return instance; }

    public void onEnable() {
        instance = this;
        new FileUtils(this);
        new NumberFormatter(getConfig());
        new DataManager();
        new Cooldown();
        new Menus();

        registerListeners();
        registerHooks();
        registerCommand(COMMAND, ALIASES, new FarmAxeCmd());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerGeneralListeners(), this);
        getServer().getPluginManager().registerEvents(new FarmAxeListeners(), this);
        getServer().getPluginManager().registerEvents(new PointsListeners(), this);
    }

    private void registerHooks() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook(this).register();
        }
    }

    private void registerCommand(String command, List<String> aliases, CommandExecutor executor) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCmd = constructor.newInstance(command, this);
            pluginCmd.setAliases(aliases);
            pluginCmd.setExecutor(executor);

            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            commandMap.register(getName().toLowerCase(), pluginCmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}