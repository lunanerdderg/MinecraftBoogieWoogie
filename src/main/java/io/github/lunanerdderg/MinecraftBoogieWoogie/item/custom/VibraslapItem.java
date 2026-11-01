package io.github.lunanerdderg.MinecraftBoogieWoogie.item.custom;

//import net.minecraft.world.InteractionResult;
import io.github.lunanerdderg.MinecraftBoogieWoogie.sound.ModSounds;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
//import net.minecraft.world.item.context.UseOnContext;

public class VibraslapItem extends Item {
    public VibraslapItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, player.getBoundingBox().inflate(32, 32, 32));
        Vec3 eyeLookAngle = player.getLookAngle();
        Vec3 eyePosition = player.getEyePosition();
        for (LivingEntity targetEntity : entities) {
            if (targetEntity != null) {
                Vec3 targetPosition = targetEntity.getPosition(1.0f);
                Vec3 playerEntityVec = targetPosition.subtract(eyePosition);
                double angle = Math.acos(eyeLookAngle.dot(playerEntityVec) / (eyeLookAngle.length() * playerEntityVec.length()));
                if (angle < 0.2D) {
                    this.swapEntities(player, targetEntity);
                }
            }
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, new ItemStack(this));
    }

    public void swapEntities(Entity player, Entity entity) {
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
