package com.cosmo.entity.custom;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SolarSmasher extends PathAwareEntity implements GeoEntity {
  private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public SolarSmasher(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
    public static DefaultAttributeContainer.Builder createSolarSmasherAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        goalSelector.add(0, new WanderAroundFarGoal(this,1));
    }
    @Override
    public void tickMovement() {
        super.tickMovement();
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
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this, "walking", 0, state -> {
      return state.isMoving() ? state.setAndContinue(DefaultAnimations.WALK) : PlayState.STOP;
    }));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.geoCache;
  }
}
