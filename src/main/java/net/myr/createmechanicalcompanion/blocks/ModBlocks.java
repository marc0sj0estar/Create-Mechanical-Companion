package net.myr.createmechanicalcompanion.blocks;

import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {

   public static final BlockEntry<CompanionFoundry> COMPANION_FOUNDRY = CreateMechanicalCompanion.REGISTRATE
           .block("companion_foundry", CompanionFoundry::new)
           .initialProperties(() -> Blocks.STONE)
           .properties(p -> p.noOcclusion())
           .transform(pickaxeOnly())
           .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
           .onRegister(b -> BlockStressValues.IMPACTS.register(b, () -> 128))
           .item()
           .build()
           .register();


    public static void register() {
    }
}
