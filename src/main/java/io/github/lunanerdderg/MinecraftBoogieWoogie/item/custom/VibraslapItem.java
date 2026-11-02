package io.github.lunanerdderg.MinecraftBoogieWoogie.item.custom;

import io.github.lunanerdderg.MinecraftBoogieWoogie.Config;
import io.github.lunanerdderg.MinecraftBoogieWoogie.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
//import net.minecraft.world.item.context.UseOnContext;

public class VibraslapItem extends Item {
    private static LivingEntity previousTarget = null;
    private static LivingEntity curTarget = null;

    public VibraslapItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        LivingEntity target = getTarget(player, level);;
        if (target != null) {
            swapEntities(player, target);
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, new ItemStack(this));
    }

    public static void onTick(PlayerTickEvent.Pre event) {
        if (Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
            curTarget = getTarget(event.getEntity(), event.getEntity().level());
            if (curTarget != null) {
                curTarget.setGlowingTag(true);
            }
            if (curTarget == null || !curTarget.equals(previousTarget)) {
                if (previousTarget != null) {
                    previousTarget.setGlowingTag(false);
                }
            }
            previousTarget = getTarget(event.getEntity(), event.getEntity().level());
        }
    }

//    public static void onStartTracking(PlayerEvent.StartTracking event) {
//        if (Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
//            curTarget = getTarget(event.getEntity(), event.getEntity().level());
//            if (curTarget != null) {
//                curTarget.setGlowingTag(true);
//            }
//            if (curTarget == null || !curTarget.equals(previousTarget)) {// || !curTarget.is(previousTarget)) {
//                if (previousTarget != null) {
//                    previousTarget.setGlowingTag(false);
//                }
//            }
//            previousTarget = getTarget(event.getEntity(), event.getEntity().level());
//        }
//    }

//    public static void onStopTracking(PlayerEvent.StopTracking event) {
//        if (Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
//            curTarget = getTarget(event.getEntity(), event.getEntity().level());
//            if (curTarget != null) {
//                curTarget.setGlowingTag(true);
//            }
//            if (curTarget == null || !curTarget.equals(previousTarget)) {
//                if (previousTarget != null) {
//                    previousTarget.setGlowingTag(false);
//                }
//            }
//            previousTarget = getTarget(event.getEntity(), event.getEntity().level());
//        }
//    }
//
//    public static void onFrame(RenderFrameEvent.Pre event) {
//        if (Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
////            if (switchedTargets) {
//                ClientLevel level = Minecraft.getInstance().level;
//                if (level != null /* && level.isClientSide()*/) {
//                    List<AbstractClientPlayer> players = level.players();
//                    for (AbstractClientPlayer player : players) {
//                        if (player != null) {
//                            previousTarget = getTarget(player, player.level());
//                            if (Config.BOOGIEWOOGIE_HIGHLIGHT.getAsBoolean()) {
//                                curTarget = getTarget(player, player.level());
//                                if (curTarget != null) {
//                                    curTarget.setGlowingTag(true);
//                                }
//                                if (curTarget == null || !curTarget.equals(previousTarget)) {
//                                    if (previousTarget != null) {
//                                        previousTarget.setGlowingTag(false);
//                                    }
//
//                                }
//                                previousTarget = getTarget(player, player.level());
//                            }
//                        }
//                    }
//                }
//        }
//    }

    public static LivingEntity getTarget(Player player, Level level) {
        double RANGE = Config.BOOGIEWOOGIE_RANGE.getAsInt();
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, player.getBoundingBox().inflate(RANGE, RANGE, RANGE));
        Vec3 eyeLookAngle = player.getLookAngle();
        Vec3 eyePosition = player.getEyePosition();
        for (LivingEntity targetEntity : entities) {
            if (targetEntity != null && player.hasLineOfSight(targetEntity)) {
                Vec3 targetPosition = targetEntity.getPosition(1.0f);
                Vec3 playerEntityVec = targetPosition.subtract(eyePosition);
                double angle = Math.acos(eyeLookAngle.dot(playerEntityVec) / (eyeLookAngle.length() * playerEntityVec.length()));
                if (eyePosition.distanceTo(targetPosition) >= 2.0D && angle < Config.BOOGIEWOOGIE_SENSITIVITY.getAsDouble()) {
                    return targetEntity;
                }
            }
        }
        return null;
    }

    public void swapEntities(Entity player, Entity entity) {
        if (player != null && entity != null) {
            double entityX = entity.getX();
            double entityY = entity.getY();
            double entityZ = entity.getZ();
            double playerX = player.getX();
            double playerY = player.getY();
            double playerZ = player.getZ();
            entity.absMoveTo(playerX, playerY, playerZ);
            player.absMoveTo(entityX, entityY, entityZ);
            player.playSound(ModSounds.BOOGIEWOOGIE_SOUND.get());
        }
    }
}
