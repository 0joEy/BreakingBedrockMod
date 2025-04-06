package net.henrycmoss.bb.events;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.block.custom.fluid.BbFluidTypes;
import net.henrycmoss.bb.command.HomeCommand;
import net.henrycmoss.bb.effect.BbEffects;
import net.henrycmoss.bb.effect.HallucinationEffect;
import net.henrycmoss.bb.item.BbItems;
import net.henrycmoss.bb.tools.ShootingTools;
import net.henrycmoss.bb.villager.BbVillagers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import org.checkerframework.checker.units.qual.C;
import oshi.jna.platform.mac.SystemB;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = Bb.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BbForgeEvents {

    private static boolean jumped = false;

    private static Projectile lastPro = null;

    @SubscribeEvent
    public static void registerCommandsEvent(RegisterCommandsEvent event) {
        new HomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }


    @SubscribeEvent
    public static void addBbTrades(VillagerTradesEvent event) {
        if (event.getType() == BbVillagers.METH_COOK.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(BbItems.MARIJUANA.get(), 2), 200, 8, 0.2f
            ));
        }
    }


    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if (!event.getOriginal().level().isClientSide()) {
            event.getEntity().getPersistentData().putIntArray(Bb.MODID + ":homepos", event.getOriginal()
                    .getPersistentData().getIntArray(Bb.MODID + ":homepos"));
        }
    }

    /* @SubscribeEvent
    public static void meteorTouchGround(TickEvent.PlayerTickEvent event) {
        List<FallingBlockEntity> entities = event.player.level().getEntitiesOfClass(FallingBlockEntity.class,
                AABB.ofSize(event.player.position(), 50, 50, 50));
        for (FallingBlockEntity entity : entities) {
            event.player.sendSystemMessage(Component.literal(entity.serializeNBT().get("BlockState").toString()));
            if (entity.serializeNBT().get("BlockState").equals("{Name:'minecraft:redstone_block'}")) {
                event.player.sendSystemMessage(Component.literal(entity.serializeNBT().get("Time").toString()));
                if (entity.serializeNBT().get("OnGround").equals(true)) {
                    event.player.sendSystemMessage(Component.literal("fkfkfkfk"));
                }
            }
        }
    } */

    @SubscribeEvent
    public static void skeletonShoot(EntityEvent event) {
        if(event.getEntity() instanceof Projectile pro) {
            if(pro != lastPro) {
                Level level = pro.level();
                if (pro.getOwner() instanceof Skeleton) {
                    lastPro = pro;
                    PrimedTnt tnt = new PrimedTnt(EntityType.TNT, level);
                    tnt.setPos(pro.position());
                    tnt.setDeltaMovement(pro.getDeltaMovement());
                    level.addFreshEntity(tnt);
                }
            }
        }
    }

    @SubscribeEvent
    public static void tntCannon(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();

        List<PrimedTnt> tnts = level.getEntitiesOfClass(PrimedTnt.class,
                new AABB(player.getX() - 100, player.getY() - 100, player.getZ() - 100,
                        player.getX() + 100, player.getY() + 100, player.getZ() + 100));

        Optional<PrimedTnt> pTnts = tnts.stream().filter((pTnt) -> pTnt.getFuse() < 10 && pTnt.getTags().contains("cannon")).findFirst();

        if(pTnts.isPresent()) {
            float pX = 0;
            float pY = 0;
            for(int i = 0; i < 5; i++) {
                PrimedTnt tnt = new PrimedTnt(EntityType.TNT, level);
                tnt.setFuse(80);
                tnt.setPos(pTnts.get().position());
            tnt.setDeltaMovement(ShootingTools.shootFromRotation((player.getXRot() + pX), (player.getYRot() + pY),
                    0f, 1.5f));
            pX += 10;
            pY += 10;
            level.addFreshEntity(tnt);
            }
        }
    }

    @SubscribeEvent
    public static void angerEntityEvent(LivingChangeTargetEvent event) {
        if ((event.getNewTarget() instanceof ServerPlayer) && event.getNewTarget().hasEffect(BbEffects.HALLUCINATION.get())) {
            HallucinationEffect.setHaunter(event.getEntity());
            LogUtils.getLogger().info("log: " + event.getOriginalTarget());
        } else {
            event.getEntity().setSpeed(0.1f);
            HallucinationEffect.setHaunter(null);
        }
    }



    @SubscribeEvent
    public static void creeperExplode(ExplosionEvent.Start event) {
        if(event.getExplosion().getExploder() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getExplosion().getExploder();
            Level level = creeper.level();
            float pX = 0;
            float pY = 0;
            for(int i = 0; i < 10; i++) {
                PrimedTnt tnt = new PrimedTnt(EntityType.TNT, level);
                tnt.setFuse(30);
                tnt.setPos(creeper.position());
                tnt.setDeltaMovement(ShootingTools.shootFromRotation((creeper.getXRot() + pX), (creeper.getYRot() + pY),
                        0f, 3f));
                pX += 30;
                pY += 30;
                level.addFreshEntity(tnt);
            }
        }
    }

    @SubscribeEvent
    public static void entityJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player) {
            jumped = true;
        }
    }

}
