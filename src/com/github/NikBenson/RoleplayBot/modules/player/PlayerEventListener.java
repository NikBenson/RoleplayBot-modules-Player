package com.github.NikBenson.RoleplayBot.modules.player;

import org.json.simple.JSONObject;

public interface PlayerEventListener {
	default void onPlayerCreate(Player player) {}
	default void onPlayerSave(Player player, JSONObject json) {}
	default void onPlayerLoad(Player player, JSONObject json) {}
}
