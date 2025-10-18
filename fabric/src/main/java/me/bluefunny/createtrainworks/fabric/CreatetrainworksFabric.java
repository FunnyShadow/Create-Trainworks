package me.bluefunny.createtrainworks.fabric;

import me.bluefunny.createtrainworks.Createtrainworks;
import me.bluefunny.createtrainworks.fabric.event.WrenchInteractionHandler;
import net.fabricmc.api.ModInitializer;

public final class CreatetrainworksFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Createtrainworks.init();
        WrenchInteractionHandler.register();
    }
}
