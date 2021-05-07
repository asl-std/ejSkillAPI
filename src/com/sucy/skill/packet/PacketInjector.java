package com.sucy.skill.packet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.rit.sucy.reflect.Reflection;
import com.sucy.skill.SkillAPI;

import io.netty.channel.Channel;

/**
 * SkillAPI © 2018 com.sucy.skill.PacketInjector
 */
public class PacketInjector {
	private Field playerCon;
	private Field network;
	private Method handle;
	private Field k;
	private Field dropField;

	private SkillAPI skillAPI;

	/**
	 * Sets up the injector, grabbing necessary reflection data
	 */
	public PacketInjector(final SkillAPI skillAPI) {
		this.skillAPI = skillAPI;

		try {
			final String nms = Reflection.getNMSPackage();
			playerCon = Class.forName(nms + "EntityPlayer").getField("playerConnection");

			final Class<?> playerConnection = Class.forName(nms + "PlayerConnection");
			network = playerConnection.getField("networkManager");

			final Class<?> networkManager = Class.forName(nms + "NetworkManager");
			try {
				k = networkManager.getField("channel");
			} catch (final Exception ex) {
				k = networkManager.getDeclaredField("i");
				k.setAccessible(true);
			}

			handle = Class.forName(Reflection.getCraftPackage() + "entity.CraftPlayer").getMethod("getHandle");
		} catch (final Throwable t) {
			error();
			t.printStackTrace();
		}

		try {
			dropField = Reflection.getNMSClass("PacketPlayInBlockDig").getDeclaredField("c");
			dropField.setAccessible(true);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isWorking() {
		return handle != null;
	}

	private void error() {
		skillAPI.getLogger().warning("Failed to set up packet listener - some click combos may not behave properly");
	}

	/**
	 * Injects an interceptor to the player's network manager
	 *
	 * @param p player to add to
	 */
	public void addPlayer(Player p) {
		if (handle == null)
			return;

		try {
			final Channel ch = getChannel(p);
			if (ch.pipeline().get("PacketInjector") == null) {
				final PacketHandler h = new PacketHandler(p, dropField);
				ch.pipeline().addBefore("packet_handler", "PacketInjector", h);
			}
		} catch (final Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Removes an interruptor from a player's network manager
	 *
	 * @param p player to remove from
	 */
	public void removePlayer(Player p) {
		if (handle == null)
			return;

		try {
			final Channel ch = getChannel(p);
			if (ch.pipeline().get("PacketInjector") != null)
				ch.pipeline().remove("PacketInjector");

		} catch (final Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Gets the channel used by a player's network manager
	 *
	 * @return retrieved channel
	 */
	private Channel getChannel(final Player player) throws IllegalAccessException, InvocationTargetException {
		return (Channel) k.get(network.get(playerCon.get(handle.invoke(player))));
	}
}
