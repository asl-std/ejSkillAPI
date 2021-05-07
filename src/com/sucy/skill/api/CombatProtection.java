package com.sucy.skill.api;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * SkillAPI © 2018 com.sucy.skill.api.AllyChecker
 */
public interface CombatProtection {
	boolean canAttack(final Player attacker, final Player defender);

	boolean canAttack(final Player attacker, final LivingEntity defender);

	boolean canAttack(final LivingEntity attacker, final LivingEntity defender);

	static boolean canAttack(LivingEntity attacker, LivingEntity target, boolean passiveAlly) {
		if (attacker == target)
			return false;
		else {
			if (target instanceof Tameable) {
				final Tameable entity = (Tameable) target;
				if (entity.isTamed() && entity.getOwner() instanceof OfflinePlayer) {
					final OfflinePlayer owner = (OfflinePlayer) entity.getOwner();
					if (owner.isOnline())
						return canAttack(attacker, owner.getPlayer(), false);
				}
			}

			if (passiveAlly && target instanceof Animals)
				return false;

			final DefaultCombatProtection.FakeEntityDamageByEntityEvent event = new DefaultCombatProtection.FakeEntityDamageByEntityEvent(
					attacker, target, EntityDamageEvent.DamageCause.CUSTOM, 1.0D);
			Bukkit.getPluginManager().callEvent(event);
			return !event.isCancelled();
		}
	}

}
