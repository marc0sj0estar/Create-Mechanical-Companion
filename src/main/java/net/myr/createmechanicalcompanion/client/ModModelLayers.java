package net.myr.createmechanicalcompanion.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

public class ModModelLayers {
    public static final ModelLayerLocation CUSTOM_WOLF = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "custom_wolf"), "main");
}
