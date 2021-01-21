package com.github.NikBenson.RoleplayBot.modules.player;

import com.github.NikBenson.RoleplayBot.Bot;
import com.github.NikBenson.RoleplayBot.configurations.ConfigurationManager;
import com.github.NikBenson.RoleplayBot.configurations.ConfigurationPaths;
import com.github.NikBenson.RoleplayBot.configurations.JSONConfigured;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PlayerManager extends ListenerAdapter implements JSONConfigured {
	private final Guild GUILD;

	private final List<PlayerEventListener> listeners = new LinkedList<>();

	Map<User, Player> players = new HashMap<>();

	public PlayerManager(Guild guild) {
		GUILD = guild;
		guild.getJDA().addEventListener(this);
	}

	public void registerEventListener(PlayerEventListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	public List<PlayerEventListener> getEventListeners() {
		return listeners;
	}

	public Player getPlayerOrCreate(User user) {
		if(!players.containsKey(user)) {
			Player newPlayer = new Player(user);

			for(PlayerEventListener listener : listeners) {
				listener.onPlayerCreate(newPlayer);
			}

			players.put(user, newPlayer);
		}

		return players.get(user);
	}

	@SubscribeEvent
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		if(event.getGuild() == GUILD) {
			User user = event.getUser();

			if (!user.isBot()) {
				getPlayerOrCreate(user);
			}
		}
	}

	@Override
	public JSONObject getJSON() {
		JSONArray playersJson = new JSONArray();

		for(User user : players.keySet()) {
			Player player = players.get(user);
			JSONObject playerJson = player.getJSON();

			for(PlayerEventListener listener : listeners) {
				listener.onPlayerSave(player, playerJson);
			}

			playersJson.add(playerJson);
		}

		JSONObject json = new JSONObject();

		json.put("players", playersJson);

		return json;
	}

	@Override
	public @NotNull File getConfigPath() {
		return new File(ConfigurationManager.getInstance().getAutogeneratedConfigurationRootPath(GUILD), ConfigurationPaths.Autogenerated.PLAYERS_FILE);
	}

	@Override
	public void loadFromJSON(JSONObject json) {
		JSONArray playersJson = (JSONArray) json.get("players");

		for (Object o : playersJson) {
			JSONObject currentJson = (JSONObject) o;

			long userId = (long) currentJson.get("id");
			Player currentPlayer = new Player(currentJson);

			for(PlayerEventListener listener : listeners) {
				listener.onPlayerLoad(currentPlayer, currentJson);
			}

			players.put(Bot.getJDA().getUserById(userId), currentPlayer);
		}
	}

	@Override
	public Guild getGuild() {
		return GUILD;
	}
}
