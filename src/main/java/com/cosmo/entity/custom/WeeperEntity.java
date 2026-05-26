package com.cosmo.entity.custom;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.init.ItemInit;
import com.cosmo.util.ShiftedComponent;
import com.cosmo.world.dimension.DimensionInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class WeeperEntity extends PathAwareEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState ChaseingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Tracked data
    protected static final TrackedData<Optional<UUID>> TARGET_UUID = DataTracker.registerData(WeeperEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Boolean> CHASING =
            DataTracker.registerData(WeeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // Owner / taming tracking (manual - PathAwareEntity has no built-in taming)
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(WeeperEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(WeeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public WeeperEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        setStepHeight(1);
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TARGET_UUID, Optional.empty());
        this.dataTracker.startTracking(CHASING,false);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(SITTING, false);
    }

    // ---------------------
    // NBT Persistence (owner, sitting, forced target)
    // ---------------------
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getForcedTargetUuid() != null) {
            nbt.putUuid("ForcedTarget", this.getForcedTargetUuid());
        }

        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
            nbt.putBoolean("IsTamed", true);
        } else {
            nbt.putBoolean("IsTamed", false);
        }
        nbt.putBoolean("Sitting", this.isSitting());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        // Forced target
        if (nbt.containsUuid("ForcedTarget")) {
            UUID forced = nbt.getUuid("ForcedTarget");
            this.setForcedTargetUuid(forced);
        } else if (nbt.contains("ForcedTarget")) {
            String s = nbt.getString("ForcedTarget");
            UUID forced = ServerConfigHandler.getPlayerUuidByName(this.getServer(), s);
            if (forced != null) this.setForcedTargetUuid(forced);
        }

        // Owner / tamed
        if (nbt.containsUuid("Owner")) {
            UUID owner = nbt.getUuid("Owner");
            this.setOwnerUuid(owner);
            // Mark persistent so it won't despawn when tamed
            this.setPersistent();
        } else if (nbt.contains("Owner")) {
            String ownerName = nbt.getString("Owner");
            UUID ownerUuid = ServerConfigHandler.getPlayerUuidByName(this.getServer(), ownerName);
            if (ownerUuid != null) {
                this.setOwnerUuid(ownerUuid);
                this.setPersistent();
            }
        }

        if (nbt.contains("Sitting")) {
            this.setSitting(nbt.getBoolean("Sitting"));
        }
    }

    // ---------------------
    // Forced target tracking (unchanged)
    // ---------------------
    @Nullable
    public UUID getForcedTargetUuid() {
        return (UUID) ((Optional) this.dataTracker.get(TARGET_UUID)).orElse((Object) null);
    }

    public boolean hasForcedTarget() {
        return getForcedTarget() != null;
    }

    public void setForcedTargetUuid(@Nullable UUID uuid) {
        this.dataTracker.set(TARGET_UUID, Optional.ofNullable(uuid));
    }

    public void setForcedTarget(PlayerEntity player) {
        this.setForcedTargetUuid(player.getUuid());
    }

    @Nullable
    public LivingEntity getForcedTarget() {
        UUID uUID = this.getForcedTargetUuid();
        return uUID == null ? null : this.getWorld().getPlayerByUuid(uUID);
    }

    // ---------------------
    // Manual taming/owner helpers (since we extend PathAwareEntity)
    // ---------------------
    @Nullable
    public UUID getOwnerUuid() {
        return (UUID) ((Optional) this.dataTracker.get(OWNER_UUID)).orElse((Object) null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
        if (uuid != null) {
            // Ensure persistent when tamed/owned
            this.setPersistent();
        }
    }

    @Nullable
    public PlayerEntity getOwner() {
        UUID uuid = this.getOwnerUuid();
        if (uuid == null) return null;
        World world = this.getWorld();
        if (world == null) return null;
        return world.getPlayerByUuid(uuid);
    }

    public void setOwner(PlayerEntity player) {
        if (player != null) {
            setOwnerUuid(player.getUuid());
        } else {
            setOwnerUuid(null);
        }
    }

    public boolean isOwner(PlayerEntity player) {
        if (player == null) return false;
        UUID ownerUuid = this.getOwnerUuid();
        return ownerUuid != null && ownerUuid.equals(player.getUuid());
    }

    // Sitting
    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSitting(boolean sit) {
        this.dataTracker.set(SITTING, sit);
        if (sit) {
            this.getNavigation().stop();
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
            this.getOwner().sendMessage(this.getDamageTracker().getDeathMessage());
        }

        super.onDeath(damageSource);
    }
    // ---------------------
    // Interaction: owner toggles sit (no taming item)
    // ---------------------
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (isOwner(player)) {
            setSitting(!isSitting());
            this.jumping = false;
            this.getNavigation().stop();
            this.setTarget(null);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
    }

    // ---------------------
    // pushAway logic preserved
    // ---------------------
    @Override
    public void pushAway(Entity entity) {
        if (ShiftedComponent.canSeePlayer(this,entity)) super.pushAway(entity);
    }

    @Override
    public boolean canHit() {
        // Prevents the player from "targeting" the entity with cursor
        LivingEntity owner = getOwner();
        LivingEntity target = getForcedTarget();
        if (getWorld().isClient) {
            if (MinecraftClient.getInstance().player != null) {
                if (MinecraftClient.getInstance().world != null) {
                    if (!MinecraftClient.getInstance().world.getRegistryKey()
                            .equals(DimensionInit.SHADOW_REALM_LEVEL_KEY)) {
                        return CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()
                                || MinecraftClient.getInstance().player == target
                                || MinecraftClient.getInstance().player == owner;
                    }
                }
            }
        }
        return true;
    }

    protected static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) > 8;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (ShiftedComponent.canSeePlayer(this,entity)) super.pushAwayFrom(entity);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (target instanceof PlayerEntity player){
            if (isOwner(player)) {
                super.setTarget(null);
                return;
            }
        }
        if (hasForcedTarget()) {
            super.setTarget(getForcedTarget());
            this.dataTracker.set(CHASING, true);
        } else {
            super.setTarget(target);
            this.dataTracker.set(CHASING, target != null && target.isAlive());
        }
    }

    public boolean isChasing() {
        return this.dataTracker.get(CHASING);
    }

    private void setupAnimationStates() {
        if (getTarget() != null) {
            this.idleAnimationState.stop();
            this.ChaseingAnimationState.start(this.age);
        } else {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 40;
                this.idleAnimationState.start(this.age);
            } else {
                --this.idleAnimationTimeout;
            }
            this.ChaseingAnimationState.stop();
        }
    }

    protected void updateLimbs(float limbDistance) {
        float f;
        if (this.getPose() == EntityPose.STANDING) {
            f = Math.min(limbDistance * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.limbAnimator.updateLimbs(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    @Override
    public boolean isInvisibleTo(PlayerEntity player) {
        if (player.getWorld() != null) {
            if (!player.getWorld().getRegistryKey()
                    .equals(DimensionInit.SHADOW_REALM_LEVEL_KEY)) {
                if (!CosmicVeilComponents.SHIFTED.get(player).isShifted() && !isOwner(player) && !isForcedTarget(player)) {
                    return true;
                }
            }
        }
        return super.isInvisibleTo(player);
    }
    @Override
    protected void fall(double fallDistance, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        // Intentionally blank (prevents fall damage)
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SitGoalWrapper(this));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 100.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(1, new RevengeGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0f, 0.0f));
        this.goalSelector.add(0, new WeeperFollowOwnerGoal(this, 3, 10f, 2f, true));
        this.initCustomGoals();
    }

    // We use a small sit-goal wrapper to match previous SitGoal behaviour for TameableEntity
    public static class SitGoalWrapper extends Goal {
        private final WeeperEntity mob;

        public SitGoalWrapper(WeeperEntity mob) {
            this.mob = mob;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return this.mob.isSitting();
        }

        @Override
        public void start() {
            this.mob.getNavigation().stop();
        }

        @Override
        public boolean shouldContinue() {
            return this.mob.isSitting();
        }
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 3, false));
        this.goalSelector.add(1, new AttackWithOwnerGoalWeeper(this));
        this.targetSelector.add(2, new WeeperActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createWeeperAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (getWorld().isClient()) {
            if (MinecraftClient.getInstance().player == null) return;
            if (CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()) {
                BlockSoundGroup blockSoundGroup = state.getSoundGroup();
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), blockSoundGroup.getStepSound(), SoundCategory.AMBIENT, blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch(), true);
            }
        }
    }

    @Override
    public void playAmbientSound() {
        if (getWorld().isClient()) {
            if (MinecraftClient.getInstance().player == null) return;
            if (CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()) {
                SoundEvent soundEvent = this.getAmbientSound();
                if (soundEvent != null) {
                    this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), soundEvent, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch(), true);
                }
            }
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    protected void playHurtSound(DamageSource source) {
        if (getWorld().isClient()) {
            if (MinecraftClient.getInstance().player == null) return;
            if (CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()) {
                SoundEvent soundEvent = this.getHurtSound(source);
                if (soundEvent != null) {
                    this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), soundEvent, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch(), true);
                }
            }
        }
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    @Override
    protected void playSwimSound() {
        if (getWorld().isClient()) {
            if (MinecraftClient.getInstance().player == null) return;
            if (CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()) {
                SoundEvent soundEvent = this.getSwimSound();
                if (soundEvent != null) {
                    this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), soundEvent, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch(), true);
                }
            }
        }
    }

    public boolean hasOwner() {
        return getOwnerUuid() != null;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity player) {
            if(player.getMainHandStack().isOf(ItemInit.MonarchsSword)) {
                if (this.isOwner(player)) {
                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.HEART, this.getX(), this.getY(), this.getZ(),10, 0, 0, 0,0);
                    }
                    this.heal(4f);
                    return false;
                }
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if (getOwner() != null && !getOwner().isAlive()) {
            return false;
        } else if (getOwner() != null) {
            return super.canTarget(target);
        } else if (target == getForcedTarget()) {
            return true;
        }
        if (target instanceof PlayerEntity player) {
            if (player.getWorld() != null) {
                if (!player.getWorld().getRegistryKey().equals(DimensionInit.SHADOW_REALM_LEVEL_KEY)) {
                    if (CosmicVeilComponents.SHIFTED.get(player).shouldSeeThrough() && !isOwner(player)) {
                        return this.getWorld().getDifficulty() != Difficulty.PEACEFUL
                                && target.canTakeDamage();
                    } else {
                        return false;
                    }
                }
            }
        }
        return super.canTarget(target);
    }

    public static boolean canSpawnInDark(EntityType<? extends WeeperEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random);
    }

    public static boolean isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensionType = world.getDimension();
            int i = dimensionType.monsterSpawnBlockLightLimit();
            if (i < 15 && world.getLightLevel(LightType.BLOCK, pos) > i) {
                return false;
            } else {
                int j = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLightLevel(pos);
                return j <= dimensionType.monsterSpawnLightTest().get(random);
            }
        }
    }

    public boolean isForcedTarget(LivingEntity entity) {
        return entity == this.getForcedTarget();
    }
    // ---------------------
    // AttackWithOwnerGoalWeeper
    // ---------------------
    public static class AttackWithOwnerGoalWeeper extends TrackTargetGoal {
        private final WeeperEntity tameable;
        private LivingEntity attacking;
        private int lastAttackTime;

        public AttackWithOwnerGoalWeeper(WeeperEntity tameable) {
            super(tameable, false);
            this.tameable = tameable;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        public boolean canStart() {
            if (this.tameable.getOwner() != null && !this.tameable.isSitting()) {
                LivingEntity livingEntity = this.tameable.getOwner();
                if (livingEntity == null) {
                    return false;
                } else {
                    this.attacking = livingEntity.getAttacking();
                    int i = livingEntity.getLastAttackTime();
                    return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT);
                }
            } else {
                return false;
            }
        }

        public void start() {
            this.mob.setTarget(this.attacking);
            LivingEntity livingEntity = this.tameable.getOwner();
            if (livingEntity != null) {
                this.lastAttackTime = livingEntity.getLastAttackTime();
            }
            super.start();
        }
    }

    // ---------------------
    // mobTick - handle forced target player disconnect/dimension changes
    // ---------------------
    @Override
    protected void mobTick() {
        if (getForcedTarget() instanceof ServerPlayerEntity player) {
            ServerWorld playerWorld = player.getServerWorld();

            // 1️⃣ Player disconnected
            if (!player.networkHandler.isConnectionOpen()) {
                this.discard();
                return;
            }

            // 2️⃣ Player teleported or dimension changed unexpectedly
            if (playerWorld != this.getWorld()) {
                this.discard();
                return;
            }
        }
        if (hasForcedTarget()) {
            if (!getForcedTarget().isAlive()) {
                this.discard();
                return;
            }
        }
        super.mobTick();
    }

    // ---------------------
    // Active target goal (preserved)
    // ---------------------
    public static class WeeperActiveTargetGoal<T extends LivingEntity> extends TrackTargetGoal {
        private static final int DEFAULT_RECIPROCAL_CHANCE = 10;
        protected final Class<T> targetClass;
        protected final int reciprocalChance;
        @Nullable
        protected LivingEntity targetEntity;
        protected TargetPredicate targetPredicate;

        public WeeperActiveTargetGoal(WeeperEntity mob, Class<T> targetClass, boolean checkVisibility) {
            this(mob, targetClass, 10, checkVisibility, false, (Predicate) null);
        }

        public WeeperActiveTargetGoal(WeeperEntity mob, Class<T> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate) {
            this(mob, targetClass, 10, checkVisibility, false, targetPredicate);
        }

        public WeeperActiveTargetGoal(WeeperEntity mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
            this(mob, targetClass, 10, checkVisibility, checkCanNavigate, (Predicate) null);
        }

        public WeeperActiveTargetGoal(WeeperEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
            super(mob, checkVisibility, checkCanNavigate);
            this.targetClass = targetClass;
            this.reciprocalChance = toGoalTicks(reciprocalChance);
            this.setControls(EnumSet.of(Control.TARGET));
            this.targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(targetPredicate);
        }

        public boolean canStart() {
            if (mob instanceof WeeperEntity weeper) {
                if (weeper.hasOwner()) {
                    return false;
                }
            }
            if (this.reciprocalChance > 0 && this.mob.getRandom().nextInt(this.reciprocalChance) != 0) {
                return false;
            } else {
                this.findClosestTarget();
                return this.targetEntity != null;
            }
        }

        protected Box getSearchBox(double distance) {
            return this.mob.getBoundingBox().expand(distance, (double) 4.0F, distance);
        }

        protected void findClosestTarget() {
            if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
                if (mob instanceof WeeperEntity weeper) {
                    if (weeper.hasForcedTarget()) {
                        this.targetEntity = weeper.getForcedTarget();
                    }
                }
                this.targetEntity = this.mob.getWorld().getClosestEntity(this.mob.getWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()), (livingEntity) -> true), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            } else {
                this.targetEntity = this.mob.getWorld().getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            }
        }

        public void start() {
            this.mob.setTarget(this.targetEntity);
            super.start();
        }

        public void setTargetEntity(@Nullable LivingEntity targetEntity) {
            this.targetEntity = targetEntity;
        }
    }

    // ---------------------
    // Follow owner goal (preserved)
    // ---------------------
    public static class WeeperFollowOwnerGoal extends Goal {
        public static final int TELEPORT_DISTANCE = 12;
        private static final int HORIZONTAL_RANGE = 2;
        private static final int HORIZONTAL_VARIATION = 3;
        private static final int VERTICAL_VARIATION = 1;
        private final WeeperEntity tameable;
        private LivingEntity owner;
        private final WorldView world;
        private final double speed;
        private final EntityNavigation navigation;
        private int updateCountdownTicks;
        private final float maxDistance;
        private final float minDistance;
        private float oldWaterPathfindingPenalty;
        private final boolean leavesAllowed;

        public WeeperFollowOwnerGoal(WeeperEntity tameable, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
            this.tameable = tameable;
            this.world = tameable.getWorld();
            this.speed = speed;
            this.navigation = tameable.getNavigation();
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.leavesAllowed = leavesAllowed;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
            if (!(tameable.getNavigation() instanceof MobNavigation) && !(tameable.getNavigation() instanceof BirdNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.tameable.getOwner();
            if (livingEntity == null) {
                return false;
            } else if (livingEntity.isSpectator()) {
                return false;
            } else if (this.cannotFollow()) {
                return false;
            } else if (this.tameable.squaredDistanceTo(livingEntity) < (double) (this.minDistance * this.minDistance)) {
                return false;
            } else {
                this.owner = livingEntity;
                return true;
            }
        }

        public boolean shouldContinue() {
            if (this.navigation.isIdle()) {
                return false;
            } else if (this.cannotFollow()) {
                return false;
            } else {
                return !(this.tameable.squaredDistanceTo(this.owner) <= (double) (this.maxDistance * this.maxDistance));
            }
        }

        private boolean cannotFollow() {
            return this.tameable.isSitting() || this.tameable.hasVehicle() || this.tameable.isLeashed();
        }

        public void start() {
            this.updateCountdownTicks = 0;
            this.oldWaterPathfindingPenalty = this.tameable.getPathfindingPenalty(PathNodeType.WATER);
            this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        }

        public void stop() {
            this.owner = null;
            this.navigation.stop();
            this.tameable.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
        }

        public void tick() {
            this.tameable.getLookControl().lookAt(this.owner, 10.0F, (float) this.tameable.getMaxLookPitchChange());
            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = this.getTickCount(10);
                if (this.tameable.squaredDistanceTo(this.owner) >= (double) 144.0F) {
                    this.tryTeleport();
                } else {
                    this.navigation.startMovingTo(this.owner, this.speed);
                }
            }
        }

        private void tryTeleport() {
            BlockPos blockPos = this.owner.getBlockPos();
            for (int i = 0; i < 10; ++i) {
                int j = this.getRandomInt(-3, 3);
                int k = this.getRandomInt(-1, 1);
                int l = this.getRandomInt(-3, 3);
                boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
                if (bl) {
                    return;
                }
            }
        }

        private boolean tryTeleportTo(int x, int y, int z) {
            if (Math.abs((double) x - this.owner.getX()) < (double) 2.0F && Math.abs((double) z - this.owner.getZ()) < (double) 2.0F) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
                return false;
            } else {
                this.tameable.refreshPositionAndAngles((double) x + (double) 0.5F, (double) y, (double) z + (double) 0.5F, this.tameable.getYaw(), this.tameable.getPitch());
                this.navigation.stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos pos) {
            PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world, pos.mutableCopy());
            if (pathNodeType != PathNodeType.WALKABLE) {
                return false;
            } else {
                BlockState blockState = this.world.getBlockState(pos.down());
                if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
                    return false;
                } else {
                    BlockPos blockPos = pos.subtract(this.tameable.getBlockPos());
                    return this.world.isSpaceEmpty(this.tameable, this.tameable.getBoundingBox().offset(blockPos));
                }
            }
        }

        private int getRandomInt(int min, int max) {
            return this.tameable.getRandom().nextInt(max - min + 1) + min;
        }
    }

}
