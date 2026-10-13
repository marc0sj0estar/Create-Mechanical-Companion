package net.myr.createmechanicalcompanion.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.blocks.ModBlocks;

public class ModPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return CreateMechanicalCompanion.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(ModBlocks.TEST_BLOCK.getId())
                .addStoryBoard("companion_foundry", ModPonderScenes::companionFoundry);

    }
}
