package com.karpandsmeargle.adaptive;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdaptiveAudioClientMod implements ClientModInitializer {
    public static final String MOD_ID = "adaptive-audio-mc";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Client mod initialized!");
    }
}
