package com.github.NikBenson.RoleplayBot.modules.player;

import com.github.NikBenson.RoleplayBot.configurations.ConfigurationManager;
import com.github.NikBenson.RoleplayBot.modules.RoleplayBotModule;
import net.dv8tion.jda.api.entities.Guild;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerModule implements RoleplayBotModule {
	private static PlayerModule instance;

	private Map<Guild, PlayerManager> managers = new HashMap<>();

	public PlayerModule() {
		instance = this;
	}

	@Override
	public boolean isActive(Guild guild) {
		return managers.containsKey(guild);
	}

	@Override
	public void load(Guild guild) {
		if(!managers.containsKey(guild)) {
			PlayerManager playerManager = new PlayerManager(guild);
			ConfigurationManager configurationManager = ConfigurationManager.getInstance();

			configurationManager.registerConfiguration(playerManager);
			try {
				configurationManager.load(playerManager);
			} catch (Exception ignored) {}

			managers.put(guild, playerManager);
		}
	}

	@Override
	public void unload(Guild guild) {
		if(managers.containsKey(guild)) {
			try {
				ConfigurationManager.getInstance().save(managers.get(guild));
			} catch (IOException e) {
				System.out.printf("Could not save Players for %s%n", guild.getId());
			}

			managers.remove(guild);
		}

	}

	public static PlayerManager getPlayerManager(Guild guild) {
		return instance.managers.get(guild);
	}
}
