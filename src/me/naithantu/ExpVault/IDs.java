package me.naithantu.ExpVault;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

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
	
	public HashMap<String, Integer> getPlayerID(){
		return playerID;
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
		playerID.put(player.getName(), value);
		return value;
	}
}
