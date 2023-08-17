package net.henrycmoss.bb.events;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.block.custom.fluid.BbFluidTypes;
import net.henrycmoss.bb.command.HomeCommand;
import net.henrycmoss.bb.effect.BbEffects;
import net.henrycmoss.bb.effect.HallucinationEffect;
import net.henrycmoss.bb.entity.BbEntities;
import net.henrycmoss.bb.item.BbItems;
import net.henrycmoss.bb.villager.BbVillagers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import org.joml.Vector3f;

import java.util.List;

@Mod.EventBusSubscriber(modid = Bb.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BbForgeEvents {

    private static boolean jumped = false;

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

    @SubscribeEvent
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
    public static void entityJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player) {
            jumped = true;
        }
        event.getEntity().sendSystemMessage(Component.literal(BbFluidTypes.BUBBLE_OVERLAY.toString()));
    }

    @SubscribeEvent
    public static void entityTickEvent(LivingEvent.LivingTickEvent event) {
        if(event.getEntity().isInFluidType(BbFluidTypes.ACID_FLUID_TYPE.get())) {
            event.getEntity().addEffect(new MobEffectInstance(BbEffects.ACID_BURN.get(), 250, 1));
        }
    }
}
