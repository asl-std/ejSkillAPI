/**
 * SkillAPI
 * com.sucy.skill.dynamic.TempEntity
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
package com.sucy.skill.dynamic;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.api.particle.target.EffectTarget;
import com.sucy.skill.api.particle.target.EntityTarget;
import com.sucy.skill.api.particle.target.FixedTarget;
import com.sucy.skill.api.util.Nearby;

/**
 * Temporary dummy entity used for targeting a location in the dynamic system
 */
public class TempEntity implements LivingEntity {

	private EffectTarget target;

	/**
	 * Sets up a new dummy entity
	 *
	 * @param loc location to represent
	 */
	public TempEntity(Location loc) {
		this(new FixedTarget(loc));
	}

	public TempEntity(final EffectTarget target) {
		this.target = target;
	}

	@Override
	public double getEyeHeight() {
		return 0.2;
	}

	@Override
	public double getEyeHeight(boolean b) {
		return 0.2;
	}

	@Override
	public Location getEyeLocation() {
		return getLocation().add(0, 1, 0);
	}

	public List<Block> getLineOfSight(HashSet<Byte> hashSet, int i) {
		return null;
	}

	@Override
	public List<Block> getLineOfSight(Set<Material> set, int i) {
		return null;
	}

	public Block getTargetBlock(HashSet<Byte> hashSet, int i) {
		return null;
	}

	@Override
	public Block getTargetBlock(Set<Material> set, int i) {
		return null;
	}

	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> hashSet, int i) {
		return null;
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(Set<Material> set, int i) {
		return null;
	}

	@Override
	public @Nullable Block getTargetBlockExact(int maxDistance) {
		return null;
	}

	@Override
	public @Nullable Block getTargetBlockExact(int maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
		return null;
	}

	@Override
	public @Nullable RayTraceResult rayTraceBlocks(double maxDistance) {
		return null;
	}

	@Override
	public @Nullable RayTraceResult rayTraceBlocks(double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
		return null;
	}

	public Egg throwEgg() {
		return null;
	}

	public Snowball throwSnowball() {
		return null;
	}

	public Arrow shootArrow() {
		return null;
	}

	@Override
	public int getRemainingAir() {
		return 0;
	}

	@Override
	public void setRemainingAir(int i) {

	}

	@Override
	public int getMaximumAir() {
		return 0;
	}

	@Override
	public void setMaximumAir(int i) {

	}

	public int getArrowCooldown() {
		return 0;
	}

	public void setArrowCooldown(int ticks) {
	}

	public int getArrowsInBody() {
		return 0;
	}

	public void setArrowsInBody(int count) {
	}

	@Override
	public int getMaximumNoDamageTicks() {
		return 0;
	}

	@Override
	public void setMaximumNoDamageTicks(int i) {
	}

	@Override
	public double getLastDamage() {
		return 0;
	}

	public int _INVALID_getLastDamage() {
		return 0;
	}

	@Override
	public void setLastDamage(double v) {
	}

	public void _INVALID_setLastDamage(int i) {
	}

	@Override
	public int getNoDamageTicks() {
		return 0;
	}

	@Override
	public void setNoDamageTicks(int i) {
	}

	@Override
	public Player getKiller() {
		return null;
	}

	@Override
	public boolean addPotionEffect(PotionEffect potionEffect) {
		return false;
	}

	@Override
	public boolean addPotionEffect(PotionEffect potionEffect, boolean b) {
		return false;
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> collection) {
		return false;
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType potionEffectType) {
		return false;
	}

	@Override
	public PotionEffect getPotionEffect(PotionEffectType potionEffectType) {
		return null;
	}

	@Override
	public void removePotionEffect(PotionEffectType potionEffectType) {

	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		return ImmutableList.of();
	}

	@Override
	public boolean hasLineOfSight(Entity entity) {
		return false;
	}

	@Override
	public boolean getRemoveWhenFarAway() {
		return false;
	}

	@Override
	public void setRemoveWhenFarAway(boolean b) {

	}

	@Override
	public EntityEquipment getEquipment() {
		return null;
	}

	@Override
	public void setCanPickupItems(boolean b) {

	}

	@Override
	public boolean getCanPickupItems() {
		return false;
	}

	@Override
	public void setCustomName(String s) {

	}

	@Override
	public String getCustomName() {
		return null;
	}

	@Override
	public void setCustomNameVisible(boolean b) {

	}

	@Override
	public boolean isCustomNameVisible() {
		return false;
	}

	@Override
	public void setGlowing(boolean b) {

	}

	@Override
	public boolean isGlowing() {
		return false;
	}

	@Override
	public void setInvulnerable(boolean b) {

	}

	@Override
	public boolean isInvulnerable() {
		return false;
	}

	@Override
	public boolean isSilent() {
		return false;
	}

	@Override
	public void setSilent(boolean b) {

	}

	@Override
	public boolean hasGravity() {
		return false;
	}

	@Override
	public void setGravity(boolean b) {

	}

	@Override
	public int getPortalCooldown() {
		return 0;
	}

	@Override
	public void setPortalCooldown(int i) {

	}

	@Override
	public Set<String> getScoreboardTags() {
		return null;
	}

	@Override
	public boolean addScoreboardTag(String s) {
		return false;
	}

	@Override
	public boolean removeScoreboardTag(String s) {
		return false;
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		return null;
	}

	@Override
	public @NotNull BlockFace getFacing() {
		return null;
	}

	@Override
	public @NotNull Pose getPose() {
		return null;
	}

	@Override
	public @NotNull Spigot spigot() {
		return null;
	}

	@Override
	public boolean isLeashed() {
		return false;
	}

	@Override
	public Entity getLeashHolder() throws IllegalStateException {
		return null;
	}

	@Override
	public boolean setLeashHolder(Entity entity) {
		return false;
	}

	@Override
	public boolean isGliding() {
		return false;
	}

	@Override
	public void setGliding(boolean b) {

	}

	@Override
	public boolean isSwimming() {
		return false;
	}

	@Override
	public void setSwimming(final boolean b) {
	}

	@Override
	public boolean isRiptiding() {
		return false;
	}

	@Override
	public boolean isSleeping() {
		return false;
	}

	@Override
	public void setAI(boolean b) {
	}

	@Override
	public boolean hasAI() {
		return false;
	}

	@Override
	public void attack(@NotNull Entity target) {

	}

	@Override
	public void swingMainHand() {

	}

	@Override
	public void swingOffHand() {

	}

	@Override
	public void setCollidable(boolean b) {

	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public @NotNull Set<UUID> getCollidableExemptions() {
		return null;
	}

	@Override
	public <T> @Nullable T getMemory(@NotNull MemoryKey<T> memoryKey) {
		return null;
	}

	@Override
	public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue) {
	}

	// TODO public @NotNull EntityCategory getCategory() { return
	// EntityCategory.NONE; }

	public void setInvisible(boolean invisible) {
	}

	public boolean isInvisible() {
		return true;
	}

	@Override
	public void damage(double v) {
	}

	public void _INVALID_damage(int i) {
	}

	@Override
	public void damage(double v, Entity entity) {
	}

	public void _INVALID_damage(int i, Entity entity) {
	}

	@Override
	public double getHealth() {
		return 1;
	}

	public int _INVALID_getHealth() {
		return 0;
	}

	@Override
	public void setHealth(double v) {
	}

	@Override
	public double getAbsorptionAmount() {
		return 0;
	}

	@Override
	public void setAbsorptionAmount(double v) {
	}

	public void _INVALID_setHealth(int i) {
	}

	@Override
	public double getMaxHealth() {
		return 1;
	}

	public int _INVALID_getMaxHealth() {
		return 0;
	}

	@Override
	public void setMaxHealth(double v) {
	}

	public void _INVALID_setMaxHealth(int i) {
	}

	@Override
	public void resetMaxHealth() {
	}

	@Override
	public Location getLocation() {
		return target.getLocation().clone();
	}

	@Override
	public Location getLocation(final Location location) {
		final Location loc = target.getLocation();
		location.setX(loc.getX());
		location.setY(loc.getY());
		location.setZ(loc.getZ());
		location.setWorld(loc.getWorld());
		location.setYaw(loc.getYaw());
		location.setPitch(loc.getPitch());
		return location;
	}

	@Override
	public void setVelocity(Vector vector) {
	}

	@Override
	public Vector getVelocity() {
		return new Vector(0, 0, 0);
	}

	@Override
	public double getHeight() {
		return 0;
	}

	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public @NotNull BoundingBox getBoundingBox() {
		return null;
	}

	@Override
	public boolean isOnGround() {
		return true;
	}

	public boolean isInWater() {
		return false;
	}

	@Override
	public World getWorld() {
		return target.getLocation().getWorld();
	}

	@Override
	public void setRotation(float yaw, float pitch) {
	}

	@Override
	public boolean teleport(Location location) {
		target = new FixedTarget(location);
		return true;
	}

	@Override
	public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
		target = new FixedTarget(location);
		return true;
	}

	@Override
	public boolean teleport(Entity entity) {
		target = new EntityTarget(entity);
		return true;
	}

	@Override
	public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause teleportCause) {
		target = new EntityTarget(entity);
		return true;
	}

	@Override
	public List<Entity> getNearbyEntities(double x, double y, double z) {
		return Nearby.getNearby(target.getLocation(), x);
	}

	@Override
	public int getEntityId() {
		return 0;
	}

	@Override
	public int getFireTicks() {
		return 0;
	}

	@Override
	public int getMaxFireTicks() {
		return 0;
	}

	@Override
	public void setFireTicks(int i) {
	}

	@Override
	public void remove() {
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void sendMessage(String s) {
	}

	@Override
	public void sendMessage(String[] strings) {
	}

	public void sendMessage(@Nullable UUID sender, @NotNull String message) {
	}

	public void sendMessage(@Nullable UUID sender, @NotNull String[] messages) {
	}

	@Override
	public Server getServer() {
		return Bukkit.getServer();
	}

	@Override
	public boolean isPersistent() {
		return false;
	}

	@Override
	public void setPersistent(boolean persistent) {
	}

	@Override
	public String getName() {
		return "Location";
	}

	@Override
	public Entity getPassenger() {
		return null;
	}

	@Override
	public boolean setPassenger(Entity entity) {
		return false;
	}

	@Override
	public List<Entity> getPassengers() {
		return null;
	}

	@Override
	public boolean addPassenger(final Entity entity) {
		return false;
	}

	@Override
	public boolean removePassenger(final Entity entity) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean eject() {
		return false;
	}

	@Override
	public float getFallDistance() {
		return 0;
	}

	@Override
	public void setFallDistance(float v) {

	}

	@Override
	public void setLastDamageCause(EntityDamageEvent entityDamageEvent) {
	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		return null;
	}

	@Override
	public UUID getUniqueId() {
		return null;
	}

	@Override
	public int getTicksLived() {
		return 0;
	}

	@Override
	public void setTicksLived(int i) {
	}

	@Override
	public void playEffect(EntityEffect entityEffect) {
	}

	@Override
	public EntityType getType() {
		return EntityType.CHICKEN;
	}

	@Override
	public boolean isInsideVehicle() {
		return false;
	}

	@Override
	public boolean leaveVehicle() {
		return false;
	}

	@Override
	public Entity getVehicle() {
		return null;
	}

	@Override
	public void setMetadata(String s, MetadataValue metadataValue) {
	}

	@Override
	public List<MetadataValue> getMetadata(String s) {
		return null;
	}

	@Override
	public boolean hasMetadata(String s) {
		return false;
	}

	@Override
	public void removeMetadata(String s, Plugin plugin) {
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> aClass) {
		return null;
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> aClass, Vector vector) {
		return null;
	}

	@Override
	public boolean isPermissionSet(String s) {
		return false;
	}

	@Override
	public boolean isPermissionSet(Permission permission) {
		return false;
	}

	@Override
	public boolean hasPermission(String s) {
		return false;
	}

	@Override
	public boolean hasPermission(Permission permission) {
		return false;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int i) {
		return null;
	}

	@Override
	public void removeAttachment(PermissionAttachment permissionAttachment) {
	}

	@Override
	public void recalculatePermissions() {
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return null;
	}

	@Override
	public boolean isOp() {
		return false;
	}

	@Override
	public void setOp(boolean b) {
	}

	@Override
	public AttributeInstance getAttribute(Attribute attribute) {
		return null;
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer() {
		return null;
	}
}
