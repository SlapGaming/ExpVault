package me.naithantu.ExpVault;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class IDs {
	ExpVault plugin;
	Configuration idConfig;
	IDStorage idStorage;
	
	public HashMap<String, Integer> playerID = new HashMap<String, Integer>();
	
	public IDs(ExpVault plugin, IDStorage idStorage){
		this.plugin = plugin;
		this.idStorage = idStorage;
		this.idConfig = idStorage.getConfig();
	}
	
	public Integer getPlayerID(Player player){
		return playerID.get(player.getUniqueId().toString());
	}

	public void saveIDS() {
		idConfig.set("playerids", null);
		for (Map.Entry<String, Integer> entry : playerID.entrySet()) {
			idConfig.set("playerids." + entry.getKey(), entry.getValue());
		}
		idStorage.saveConfig();
	}

	public void loadIDS() {
		if (idConfig.getConfigurationSection("playerids") == null)
			return;
		for (String key : idConfig.getConfigurationSection("playerids").getKeys(false)) {
			playerID.put(key, idConfig.getInt("playerids." + key));
		}
	}
	
	public int createID(Player player) {
		int value = playerID.size() + 1;
		playerID.put(player.getUniqueId().toString(), value);
		return value;
	}
}
