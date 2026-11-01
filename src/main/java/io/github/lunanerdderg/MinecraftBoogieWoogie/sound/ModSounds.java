package io.github.lunanerdderg.MinecraftBoogieWoogie.sound;

import io.github.lunanerdderg.MinecraftBoogieWoogie.BoogieWoogie;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BoogieWoogie.MOD_ID);

    public static final Supplier<SoundEvent> BOOGIEWOOGIE_SOUND = registerSoundEvent("boogiewoogie_sound");

//    public static final Supplier<SoundEvent> MAGIC_BLOCK_BREAK = registerSoundEvent("magic_block_break");
//
//    public static final DeferredSoundType MAGIC_BLOCK_SOUNDS = new DeferredSoundType(1f, 1f,
//            ModSounds.MAGIC_BLOCK_BREAK);


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BoogieWoogie.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
