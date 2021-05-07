/**
 * SkillAPI
 * com.sucy.skill.hook.PluginChecker
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Steven Sucy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software") to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.sucy.skill.hook;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.PluginManager;

import com.sucy.skill.listener.SkillAPIListener;

import lombok.Getter;

/**
 * Handler for checking whether or not hooked plugins are present and active
 * before using related code.
 */
public class PluginChecker extends SkillAPIListener {
	@Getter private static boolean vaultActive;
	@Getter private static boolean disguiseActive;
	@Getter private static boolean noCheatActive;
	@Getter private static boolean rpgInventoryActive;
	@Getter private static boolean placeholderAPIActive;
	@Getter private static boolean bungeeActive;
	@Getter private static boolean mythicMobsActive;
	@Getter private static boolean worldGuardActive;
	@Getter private static boolean partiesActive;

	@Override
	public void init() {
		final PluginManager pluginManager = Bukkit.getPluginManager();

		vaultActive = pluginManager.isPluginEnabled("Vault") && VaultHook.isValid();
		disguiseActive = pluginManager.isPluginEnabled("LibsDisguises");
		noCheatActive = pluginManager.isPluginEnabled("NoCheatPlus");
		rpgInventoryActive = pluginManager.isPluginEnabled("RPGInventory");
		placeholderAPIActive = pluginManager.isPluginEnabled("PlaceholderAPI");
		try {
			Class.forName("net.md_5.bungee.Util");
			bungeeActive = true;
		} catch (final Exception ex) {
			bungeeActive = false;
		}
		mythicMobsActive = pluginManager.isPluginEnabled("MythicMobs");
		worldGuardActive = pluginManager.isPluginEnabled("WorldGuard");
		partiesActive = pluginManager.isPluginEnabled("Parties");
	}

	@EventHandler
	public void onPluginEnable(PluginEnableEvent event) {
		switch (event.getPlugin().getName()) {
		case "Vault":
			vaultActive = true;
			break;
		case "LibsDisguises":
			disguiseActive = true;
			break;
		case "NoCheatPlus":
			noCheatActive = true;
			break;
		case "RPGInventory":
			rpgInventoryActive = true;
			break;
		case "PlaceholderAPI":
			placeholderAPIActive = true;
			break;
		case "MythicMobs":
			mythicMobsActive = true;
			break;
		case "WorldGuard":
			worldGuardActive = true;
			break;
		case "Parties":
			partiesActive = true;
			break;
		}
	}

	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		switch (event.getPlugin().getName()) {
		case "Vault":
			vaultActive = false;
			break;
		case "LibsDisguises":
			disguiseActive = false;
			break;
		case "NoCheatPlus":
			noCheatActive = false;
			break;
		case "RPGInventory":
			rpgInventoryActive = false;
			break;
		case "PlaceholderAPI":
			placeholderAPIActive = true;
			break;
		case "MythicMobs":
			mythicMobsActive = false;
			break;
		case "WorldGuard":
			worldGuardActive = false;
			break;
		case "Parties":
			partiesActive = false;
			break;
		}
	}
}
