package me.bluefunny.createtrainworks.forge;

import dev.architectury.platform.forge.EventBuses;
import me.bluefunny.createtrainworks.Createtrainworks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Createtrainworks.MOD_ID)
public final class CreatetrainworksForge {
    public CreatetrainworksForge() {
        @SuppressWarnings("removal")
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Createtrainworks.MOD_ID, modEventBus);
        Createtrainworks.init();
    }
}
