package net.myr.createmechanicalcompanion.blocks;

import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.item.CompanionFoundryItem;

import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class ModBlocks {

   public static final BlockEntry<CompanionFoundryBlock> COMPANION_FOUNDRY = CreateMechanicalCompanion.REGISTRATE
           .block("companion_foundry", CompanionFoundryBlock::new)
           .initialProperties(() -> Blocks.STONE)
           .properties(p -> p.noOcclusion())
           .transform(axeOrPickaxe())
           .blockstate((c, p) -> p.simpleBlock(c.getEntry()))
           .onRegister(b -> BlockStressValues.IMPACTS.register(b, () -> 128))
           .item(CompanionFoundryItem::new)
           .build()
           .register();


    public static void register() {
    }
}
