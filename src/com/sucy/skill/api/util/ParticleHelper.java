/**
 * SkillAPI
 * com.sucy.skill.api.util.ParticleHelper
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
package com.sucy.skill.api.util;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.ImmutableSet;
import com.rit.sucy.reflect.Particle;
import com.rit.sucy.version.VersionManager;
import com.sucy.skill.api.Settings;
import com.sucy.skill.api.enums.Direction;
import com.sucy.skill.api.particle.SpigotParticles;
import com.sucy.skill.log.Logger;

/**
 * Helper class for playing particles via config strings in various ways.
 */
public class ParticleHelper {
	/**
	 * Settings key for the arrangement type of particles
	 */
	public static final String ARRANGEMENT_KEY = "arrangement";

	/**
	 * Number of particles
	 */
	public static final String PARTICLES_KEY = "particles";

	/**
	 * The level to use for scaling values
	 */
	public static final String LEVEL = "level";

	/**
	 * Settings key for the type of particle
	 */
	public static final String PARTICLE_KEY = "particle";

	/**
	 * Settings key for the material used by the particle (for block crack, icon
	 * crack, and block dust)
	 */
	public static final String MATERIAL_KEY = "material";

	/**
	 * Settings key for the material data used by the particle (for block crack,
	 * icon crack, and block dust)
	 */
	public static final String TYPE_KEY = "type";

	/**
	 * Settings key for the radius of the particle arrangement
	 */
	public static final String RADIUS_KEY = "radius";

	/**
	 * Settings key for the amount of particles to play
	 */
	public static final String AMOUNT_KEY = "amount";

	/**
	 * Settings key for the particle arrangement direction (circles only)
	 */
	public static final String DIRECTION_KEY = "direction";

	/**
	 * Settings key for the Bukkit effects' data (default 0)
	 */
	public static final String DATA_KEY = "data";

	/**
	 * Settings key for the reflection particles' visible radius (default 25)
	 */
	public static final String VISIBLE_RADIUS_KEY = "visible-radius";

	/**
	 * Settings key for the reflection particles' X-offset (default 0)
	 */
	public static final String DX_KEY = "dx";

	/**
	 * Settings key for the reflection particles' Y-offset (default 0)
	 */
	public static final String DY_KEY = "dy";

	/**
	 * Settings key for the reflection particles' Z-offset (default 0)
	 */
	public static final String DZ_KEY = "dz";

	/**
	 * Settings key for the reflection particles' "speed" value (default 1)
	 */
	public static final String SPEED_KEY = "speed";

	private static final Random random = new Random();

	/**
	 * Plays an entity effect at the given location
	 *
	 * @param loc    location to play the effect
	 * @param effect entity effect to play
	 */
	public static void play(Location loc, EntityEffect effect) {
		final Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
		wolf.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 100));
		wolf.playEffect(effect);
		wolf.remove();
	}

	/**
	 * Plays particles about the given location using the given settings
	 *
	 * @param loc      location to center the effect around
	 * @param settings data to play the particles with
	 */
	public static void play(Location loc, Settings settings) {
		final String particle = settings.getString(PARTICLE_KEY, "invalid");
		if (settings.has(ARRANGEMENT_KEY)) {
			final int level = settings.getInt(LEVEL, 1);
			final double radius = settings.getAttr(RADIUS_KEY, level, 3.0);
			final int amount = (int) settings.getAttr(PARTICLES_KEY, level, 10);

			final String arrangement = settings.getString(ARRANGEMENT_KEY).toLowerCase();
			if (arrangement.equals("circle")) {
				Direction dir = null;
				if (settings.has(DIRECTION_KEY)) {
					try {
						dir = Direction.valueOf(settings.getString(DIRECTION_KEY));
					} catch (final Exception ex) {
						/* Use default value */ }
				}
				if (dir == null) {
					dir = Direction.XZ;
				}

				fillCircle(loc, particle, settings, radius, amount, dir);
			} else if (arrangement.equals("sphere")) {
				fillSphere(loc, particle, settings, radius, amount);
			} else if (arrangement.equals("hemisphere")) {
				fillHemisphere(loc, particle, settings, radius, amount);
			}
		} else {
			play(loc, particle, settings);
		}
	}

	/**
	 * Plays a particle at the given location based on the string
	 *
	 * @param loc      location to play the effect
	 * @param particle particle to play
	 * @param settings data to play the particle with
	 */
	public static void play(Location loc, String particle, Settings settings) {
		particle = particle.toLowerCase().replace("_", " ");
		final int rad = settings.getInt(VISIBLE_RADIUS_KEY, 25);
		final float dx = (float) settings.getDouble(DX_KEY, 0.0);
		final float dy = (float) settings.getDouble(DY_KEY, 0.0);
		final float dz = (float) settings.getDouble(DZ_KEY, 0.0);
		final int amount = settings.getInt(AMOUNT_KEY, 1);
		final float speed = (float) settings.getDouble(SPEED_KEY, 1.0);
		final Material mat = Material.valueOf(settings.getString(MATERIAL_KEY, "DIRT").toUpperCase().replace(" ", "_"));
		final int type = settings.getInt(TYPE_KEY, 0);
		final int data = settings.getInt(DATA_KEY, 0);

		try {
			// Normal bukkit effects
			if (BUKKIT_EFFECTS.containsKey(particle)) {
				loc.getWorld().playEffect(loc, BUKKIT_EFFECTS.get(particle), data);
			}

			// Entity effects
			else if (ENTITY_EFFECTS.contains(particle)) {
				play(loc, EntityEffect.valueOf(particle.toUpperCase().replace(' ', '_')));
			}

			// v1.13 particles
			else if (VersionManager.isVersionAtLeast(11300)) {
				SpigotParticles.play(loc, particle, dx, dy, dz, amount, speed, rad, mat, type);
			}

			// Reflection particles
			else if (REFLECT_PARTICLES.containsKey(particle)) {
				Particle.play(REFLECT_PARTICLES.get(particle), loc, rad, dx, dy, dz, speed, amount);
			}

			// Block break particle
			else if (particle.equals("block crack")) {
				Particle.playBlockCrack(mat, (short) settings.getInt(TYPE_KEY, 0), loc, rad, speed);
			}

			// Icon break particle
			else if (particle.equals("icon crack")) {
				Particle.playIconCrack(mat, (short) settings.getInt(TYPE_KEY, 0), loc, rad, speed);
			}

			// 1.9+ particles
			else {
				Particle.play(particle, loc, rad, dx, dy, dz, speed, amount);
			}
		} catch (final Exception ex) {
			Logger.invalid(ex.getCause().getMessage());
		}
	}

	/**
	 * Plays several of a particle type randomly within a circle
	 *
	 * @param loc      center location of the circle
	 * @param particle particle to play
	 * @param settings data to play the particle with
	 * @param radius   radius of the circle
	 * @param amount   amount of particles to play
	 */
	public static void fillCircle(Location loc, String particle, Settings settings, double radius, int amount,
			Direction direction) {
		final Location temp = loc.clone();
		final double rSquared = radius * radius;
		final double twoRadius = radius * 2;
		int index = 0;

		// Play the particles
		while (index < amount) {
			if (direction == Direction.XY || direction == Direction.XZ) {
				temp.setX(loc.getX() + random.nextDouble() * twoRadius - radius);
			}
			if (direction == Direction.XY || direction == Direction.YZ) {
				temp.setY(loc.getY() + random.nextDouble() * twoRadius - radius);
			}
			if (direction == Direction.XZ || direction == Direction.YZ) {
				temp.setZ(loc.getZ() + random.nextDouble() * twoRadius - radius);
			}

			if (temp.distanceSquared(loc) > rSquared) {
				continue;
			}

			play(temp, particle, settings);
			index++;
		}
	}

	/**
	 * Randomly plays particle effects within the sphere
	 *
	 * @param loc      location to center the effect around
	 * @param particle the string value for the particle
	 * @param settings data to play the particle with
	 * @param radius   radius of the sphere
	 * @param amount   amount of particles to use
	 */
	public static void fillSphere(Location loc, String particle, Settings settings, double radius, int amount) {
		final Location temp = loc.clone();
		final double rSquared = radius * radius;
		final double twoRadius = radius * 2;
		int index = 0;

		// Play the particles
		while (index < amount) {
			temp.setX(loc.getX() + random.nextDouble() * twoRadius - radius);
			temp.setY(loc.getY() + random.nextDouble() * twoRadius - radius);
			temp.setZ(loc.getZ() + random.nextDouble() * twoRadius - radius);

			if (temp.distanceSquared(loc) > rSquared) {
				continue;
			}

			play(temp, particle, settings);
			index++;
		}
	}

	/**
	 * Randomly plays particle effects within the hemisphere
	 *
	 * @param loc      location to center the effect around
	 * @param particle the string value for the particle
	 * @param settings data to play the particle with
	 * @param radius   radius of the sphere
	 * @param amount   amount of particles to use
	 */
	public static void fillHemisphere(Location loc, String particle, Settings settings, double radius, int amount) {
		final Location temp = loc.clone();
		final double rSquared = radius * radius;
		final double twoRadius = radius * 2;
		int index = 0;

		// Play the particles
		while (index < amount) {
			temp.setX(loc.getX() + random.nextDouble() * twoRadius - radius);
			temp.setY(loc.getY() + random.nextDouble() * radius);
			temp.setZ(loc.getZ() + random.nextDouble() * twoRadius - radius);

			if (temp.distanceSquared(loc) > rSquared) {
				continue;
			}

			play(temp, particle, settings);
			index++;
		}
	}

	private static final HashMap<String, Effect> BUKKIT_EFFECTS = new HashMap<>();

	private static final Set<String> ENTITY_EFFECTS = ImmutableSet.of("death", "hurt", "sheep eat", "wolf hearts",
			"wolf shake", "wolf smoke");

	private static final HashMap<String, String> REFLECT_PARTICLES = new HashMap<>();

	static {
		BUKKIT_EFFECTS.put("smoke", Effect.SMOKE);
		BUKKIT_EFFECTS.put("ender signal", Effect.ENDER_SIGNAL);
		BUKKIT_EFFECTS.put("mobspawner flames", Effect.MOBSPAWNER_FLAMES);
		BUKKIT_EFFECTS.put("potion break", Effect.POTION_BREAK);

		REFLECT_PARTICLES.put("angry villager", "angryVillager");
		REFLECT_PARTICLES.put("bubble", "bubble");
		REFLECT_PARTICLES.put("cloud", "cloud");
		REFLECT_PARTICLES.put("crit", "crit");
		REFLECT_PARTICLES.put("death suspend", "deathSuspend");
		REFLECT_PARTICLES.put("drip lava", "dripLava");
		REFLECT_PARTICLES.put("drip water", "dripWater");
		REFLECT_PARTICLES.put("enchantment table", "enchantmenttable");
		REFLECT_PARTICLES.put("explode", "explode");
		REFLECT_PARTICLES.put("firework spark", "fireworksSpark");
		REFLECT_PARTICLES.put("flame", "flame");
		REFLECT_PARTICLES.put("footstep", "footstep");
		REFLECT_PARTICLES.put("happy villager", "happyVillager");
		REFLECT_PARTICLES.put("heart", "heart");
		REFLECT_PARTICLES.put("huge explosion", "hugeexplosion");
		REFLECT_PARTICLES.put("instant spell", "instantSpell");
		REFLECT_PARTICLES.put("large explode", "largeexplode");
		REFLECT_PARTICLES.put("large smoke", "largesmoke");
		REFLECT_PARTICLES.put("lava", "lava");
		REFLECT_PARTICLES.put("magic crit", "magicCrit");
		REFLECT_PARTICLES.put("mob spell", "mobSpell");
		REFLECT_PARTICLES.put("mob spell ambient", "mobSpellAmbient");
		REFLECT_PARTICLES.put("note", "note");
		REFLECT_PARTICLES.put("portal", "portal");
		REFLECT_PARTICLES.put("red dust", "reddust");
		REFLECT_PARTICLES.put("slime", "slime");
		REFLECT_PARTICLES.put("snowball poof", "snowballpoof");
		REFLECT_PARTICLES.put("snow shovel", "snowshovel");
		REFLECT_PARTICLES.put("spell", "spell");
		REFLECT_PARTICLES.put("splash", "splash");
		REFLECT_PARTICLES.put("suspend", "suspend");
		REFLECT_PARTICLES.put("town aura", "townaura");
		REFLECT_PARTICLES.put("witch magic", "witchMagic");
	}
}
