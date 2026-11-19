package net.myr.createmechanicalcompanion.blocks;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;


public class ModBlockEntities {

    public static final BlockEntityEntry<CompanionFoundryEntity> COMPANION_FOUNDRY_ENTITY = CreateMechanicalCompanion.REGISTRATE
            .blockEntity("companion_foundry", CompanionFoundryEntity::new)
            .validBlocks(ModBlocks.COMPANION_FOUNDRY)
            .renderer(() -> CompanionFoundryRenderer::new)
            .register();

    public static void register() {}
}
