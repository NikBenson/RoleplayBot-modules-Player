package com.github.NikBenson.RoleplayBot.modules.player;

public interface PlayerEventListener {
	default void onPlayerCreate(Player player) {}
}
