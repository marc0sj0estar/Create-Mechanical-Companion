package net.myr.createmechanicalcompanion;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.myr.createmechanicalcompanion.entity.IllagerWorkshopMapTrade;

import java.util.LinkedList;
import java.util.List;

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
