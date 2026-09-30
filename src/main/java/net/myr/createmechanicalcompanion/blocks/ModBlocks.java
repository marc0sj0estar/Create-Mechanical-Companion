package net.myr.createmechanicalcompanion.blocks;

import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {

   public static final BlockEntry<TestBlock> TEST_BLOCK = CreateMechanicalCompanion.REGISTRATE
           .block("test_block", TestBlock::new)
           .initialProperties(() -> Blocks.STONE)
           .properties(p -> p.mapColor(MapColor.METAL))
           .transform(pickaxeOnly())
           .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(ctx.getName(), prov.modLoc("block/" + ctx.getName()))))
           .onRegister(b -> BlockStressValues.IMPACTS.register(b, () -> 128))
           .item()
           .build()
           .register();

    public static void register() {
    }
}
