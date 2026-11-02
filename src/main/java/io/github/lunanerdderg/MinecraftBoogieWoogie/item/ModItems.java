package io.github.lunanerdderg.MinecraftBoogieWoogie.item;

import io.github.lunanerdderg.MinecraftBoogieWoogie.BoogieWoogie;
import io.github.lunanerdderg.MinecraftBoogieWoogie.item.custom.VibraslapItem;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BoogieWoogie.MOD_ID);

    public static final DeferredItem<Item> VIBRASLAP = ITEMS.register("vibraslap", () -> new VibraslapItem(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
