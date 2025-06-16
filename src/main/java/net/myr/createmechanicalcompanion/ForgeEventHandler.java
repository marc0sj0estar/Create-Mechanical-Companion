package net.myr.createmechanicalcompanion;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid = CreateMechanicalCompanion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ForgeEventHandler {
    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CARTOGRAPHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.computeIfAbsent(2, lvl -> new LinkedList<>()).add(new IllagerWorkshopMapTrade());
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        event.getRareTrades().add(new IllagerWorkshopMapTrade());
    }
}
