package me.bluefunny.createtrainworks.forge;

import me.bluefunny.createtrainworks.Createtrainworks;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Createtrainworks.MOD_ID)
public final class CreatetrainworksForge {
    public CreatetrainworksForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Createtrainworks.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Createtrainworks.init();
    }
}
