package net.myr.createmechanicalcompanion.blocks;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;


public class ModBlockEntities {

    public static final BlockEntityEntry<TestBlockEntity> TEST_BLOCK = CreateMechanicalCompanion.REGISTRATE
            .blockEntity("test_block", TestBlockEntity::new)
            .validBlocks(ModBlocks.TEST_BLOCK)
            .register();

    public static void register() {}
}
