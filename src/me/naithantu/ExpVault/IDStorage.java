package me.naithantu.ExpVault;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class IDStorage {
	ExpVault plugin;
	File file;
	String fileName = "storage.yml";
	FileConfiguration config;

	public IDStorage(ExpVault plugin) {
		this.plugin = plugin;
		getConfig();
	}

	public void reloadConfig() {
		if (file == null) {
			file = new File(plugin.getDataFolder(), fileName);
		}
		config = YamlConfiguration.loadConfiguration(file);

		// Look for defaults in the jar
		InputStream defConfigStream = plugin.getResource(fileName);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
	}
	
	public FileConfiguration getConfig() {
		if (config == null) {
			this.reloadConfig();
		}
		return config;
	}

	public void saveConfig() {
		if (config == null || file == null) {
			return;
		}
		try {
			getConfig().save(file);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + config, ex);
		}
	}
}
