package net.myr.createmechanicalcompanion.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class IllagerWorkshopMapTrade implements VillagerTrades.ItemListing{


    @Override
    public @Nullable MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        if (!(pTrader.level() instanceof ServerLevel world)){
            return null;
        }

        ResourceLocation structureId = ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "illager_workshop");
        TagKey<Structure> structKey = TagKey.create(Registries.STRUCTURE, structureId);

        BlockPos traderPos = pTrader.blockPosition();
        BlockPos target = world.findNearestMapStructure(
                structKey,
                traderPos,
                10000,
                false
        );

        if (target == null) return null;


        ItemStack map = MapItem.create(world, target.getX(), target.getZ(), (byte)2, true, true);
        MapItem.renderBiomePreviewMap(world, map);
        MapItemSavedData.addTargetDecoration(map, target, "+", MapDecorationTypes.TARGET_X);
        map.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME, Component.translatable("item.createmechanicalcompanion.illager_workshop_map"));

        return new MerchantOffer(
                new ItemCost(Items.EMERALD, 10),
                Optional.of(new ItemCost(Items.COMPASS)),
                map, 1, 12, 0.2f
        );
    }
}
