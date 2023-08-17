package net.henrycmoss.bb.entity.custom;

import com.mojang.logging.LogUtils;
import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.effect.BbEffects;
import net.henrycmoss.bb.tools.ShootingTools;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.ArrayList;
import java.util.List;

public class HatManEntity extends Monster implements GeoEntity {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private CustomBossEvent bar = new CustomBossEvent(new ResourceLocation(Bb.MODID, "hat_man"),
            Component.literal("Hat Man"));

    public HatManEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        createBar(true, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.MOVEMENT_SPEED, 0.5d)
                .add(Attributes.ATTACK_DAMAGE, 15D)
                .add(Attributes.ATTACK_SPEED, 0.4f)
                .add(Attributes.FOLLOW_RANGE, 500D)
                .add(Attributes.FLYING_SPEED, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, true));
        //this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 10.0f));
        this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
    }

    @Override
    public void tick() {
        super.tick();
        if(getTarget() != null) {
            if(bar != null) {
                bar.addPlayer((ServerPlayer) getTarget());
                bar.setValue((int) this.getHealth());
            }

            ((ServerPlayer) getTarget()).sendSystemMessage(Component.literal("" + this.getHealth() + (int) this.getHealth()));
            if (getTarget().distanceToSqr(this.position()) > 256d) {
                teleportTo(getTarget());
            }
            if(!this.hasLineOfSight(getTarget())) {
                teleportTo(getTarget());
            }
        }
        else bar.removeAllPlayers();
        if(!this.getTarget().hasEffect(BbEffects.BAD_TRIP.get())) {
            bar.removeAllPlayers();
            this.kill();
        }
        LogUtils.getLogger().info("no target");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0,
                this::predicate));
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return super.getTarget();
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private void createBar(boolean visible, BossEvent.BossBarColor color) {
        bar.setVisible(visible);
        bar.setColor(color);
        bar.setMax((int) this.getMaxHealth());
    }

    private void teleportTo(Entity entity) {
        this.setPos(entity.position());
        this.setDeltaMovement(ShootingTools.shootFromRotation(entity.getXRot(), entity.getYRot(), 0, 2f).reverse());
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
        LogUtils.getLogger().info("dead");
        bar.removeAllPlayers();
        this.setTarget(null);
    }
}
