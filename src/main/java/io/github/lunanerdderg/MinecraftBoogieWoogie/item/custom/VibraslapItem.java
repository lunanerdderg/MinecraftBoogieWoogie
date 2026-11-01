package io.github.lunanerdderg.MinecraftBoogieWoogie.item.custom;

//import net.minecraft.world.InteractionResult;
import io.github.lunanerdderg.MinecraftBoogieWoogie.Config;
import io.github.lunanerdderg.MinecraftBoogieWoogie.sound.ModSounds;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.fml.loading.log4j.ForgeHighlight;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
//import net.minecraft.world.item.context.UseOnContext;

public class VibraslapItem extends Item {
    private static LivingEntity target = null;

    public VibraslapItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (!Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
            updateTarget(player, level);
        }
        if (target != null) {
            swapEntities(player, target);
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, new ItemStack(this));
    }

    public static void onTick(RenderPlayerEvent.Pre event) {
        if (Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
            if (target != null) {
                target.setGlowingTag(false);
            }
            updateTarget(event.getEntity(), event.getEntity().level());
            if (target != null) {
                target.setGlowingTag(true);
            }
        }
    }

//    public static void onFrame(RenderFrameEvent event) {
//        if (Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
//            if (target != null) {
//                target.setGlowingTag(false);
//            }
//            if (target != null) {
//                target.setGlowingTag(true);
//            }
//        }
//    }

    public static void updateTarget(Player player, Level level) {
        double RANGE = Config.BOOGIEWOOGIE_RANGE.getAsInt();
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, player.getBoundingBox().inflate(RANGE, RANGE, RANGE));
        Vec3 eyeLookAngle = player.getLookAngle();
        Vec3 eyePosition = player.getEyePosition();
        for (LivingEntity targetEntity : entities) {
            if (targetEntity != null && player.hasLineOfSight(targetEntity)) {
                Vec3 targetPosition = targetEntity.getPosition(1.0f);
                Vec3 playerEntityVec = targetPosition.subtract(eyePosition);
                double angle = Math.acos(eyeLookAngle.dot(playerEntityVec) / (eyeLookAngle.length() * playerEntityVec.length()));
                if (eyePosition.distanceTo(targetPosition) >= 6.0D && angle < 0.1D) {
                    target = targetEntity;
                    return;
//                    return targetEntity;
                }
            }
        }
        target = null;
//        return null;
    }

    public static void swapEntities(Entity player, Entity entity) {
        double entityX = entity.getX();
        double entityY = entity.getY();
        double entityZ = entity.getZ();
        double playerX = player.getX();
        double playerY = player.getY();
        double playerZ = player.getZ();
        entity.moveTo(playerX, playerY, playerZ);
        player.moveTo(entityX, entityY, entityZ);
        player.playSound(ModSounds.BOOGIEWOOGIE_SOUND.get());
//        return true;
    }

//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        Entity entity = context.get
//    }
}
