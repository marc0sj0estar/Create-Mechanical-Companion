package net.myr.createmechanicalcompanion.screen;

import com.simibubi.create.AllBlocks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.item.ModItems;

public class TestBlockScreen extends AbstractContainerScreen<TestBlockMenu> {

    public static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "textures/gui/test_block.png");

    public TestBlockScreen(TestBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(font, "Progress: " + menu.getProgress(), 8, 20, 0x404040);
        guiGraphics.drawString(font, "Building Step: " + menu.getBuildingStep(), 100, 20, 0x404040);
        guiGraphics.drawString(font, "Fluid Amount: " + menu.getFluidAmount() + "mB / " + menu.getFluidCapacity() + "mB", 8, 30, 0x404040);
        guiGraphics.drawString(font, "Requested Item: ", 8, 40, 0x404040);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

        guiGraphics.blit(BACKGROUND_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);

        ItemStack currentNeededItem = switch (menu.getBuildingStep()) {
            case 0 -> new ItemStack(ModItems.MECHANICAL_WOLF_CHASSIS.get());
            case 1 -> new ItemStack(ModItems.MECHANICAL_WOLF_PROCESSOR.get());
            case 2 -> new ItemStack(AllBlocks.REDSTONE_LINK.asItem());
            case 3 -> new ItemStack(Items.LAVA_BUCKET);
            default -> ItemStack.EMPTY;
        };

        if(!currentNeededItem.isEmpty()) {
            guiGraphics.renderItem(currentNeededItem, leftPos + 8, topPos + 50);
        }
    }
}
