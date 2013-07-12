package me.naithantu.ExpVault;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Transfer {
	Configuration config;
	String header;
	IDs ids;

	public Transfer(Configuration config, String header, IDs ids) {
		this.config = config;
		this.header = header;
		this.ids = ids;
	}

	public void handleSignPlace(SignChangeEvent event) {
		if (config.getBoolean("transfer.expbank") == true) {
			if (event.getLine(0).equalsIgnoreCase("[expbank]")) {
				event.getPlayer().sendMessage(ChatColor.RED + "You may not create an ExpBank sign. Make an ExpVault sign instead.");
				event.setCancelled(true);
			}
		}
	}

	public void handleSignInteract(PlayerInteractEvent event) {
		if (config.getBoolean("transfer.expbank") == true) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLine(0).equalsIgnoreCase("[expbank]")) {
				String playerName = sign.getLine(1);
				if (playerName != null) {
					if (!event.getPlayer().getName().equals(playerName)) {
						event.getPlayer().sendMessage(ChatColor.RED + " That is not your ExpBank!");
						return;
					}
					Player player = event.getPlayer();
					int levels;
					//Get levels for different versions of ExpBank
					if (sign.getLine(3).isEmpty()) {
						try {
							levels = Integer.parseInt(sign.getLine(2));
						} catch (NumberFormatException e) {
							return;
						}
					} else {
						try {
							levels = Integer.parseInt(sign.getLine(3));
						} catch (NumberFormatException e) {
							return;
						}
					}
					int exp = ExpUtil.getTotalExperience(levels, 0);
					player.sendMessage(header + "Turned your ExpBank sign into an ExpVault sign!");
					createSign(sign, player, exp);
				}
			}
		}
	}

	private void createSign(Sign sign, Player player, int exp) {
		sign.setLine(0, header);
		sign.setLine(1, player.getName());
		sign.setLine(2, "Exp: " + exp);
		if (!ids.getPlayerID().containsKey(player.getName())) {
			sign.setLine(3, Integer.toString(ids.createID(player)));
		} else {
			sign.setLine(3, Integer.toString(ids.getPlayerID().get(player.getName())));
		}
		sign.update();
	}
}
