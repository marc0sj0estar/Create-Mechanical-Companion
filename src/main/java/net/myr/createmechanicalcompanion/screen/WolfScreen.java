package net.myr.createmechanicalcompanion.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

public class WolfScreen extends AbstractContainerScreen<WolfMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "textures/gui/inventory.png");
    private WolfMenu wolfMenu;

    public WolfScreen(WolfMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        wolfMenu = pMenu;
        this.imageWidth = 176;
        this.imageHeight = 172;
        this.titleLabelX = 10000;
        this.inventoryLabelX = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(BACKGROUND_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        for (int i = 0; i < WolfMenu.slotAmount; i++) {
            if (i < wolfMenu.slots.size()) {
                Slot currentSlot = wolfMenu.slots.get(i);
                if (currentSlot.hasItem()) {
                    pGuiGraphics.blit(BACKGROUND_TEXTURE, x + currentSlot.x - 2, y + currentSlot.y - 2, 190, 20, 20, 20);
                }
            }
        }
        
        int xOffset = 51;
        int yOffset = 61;
        int renderX = (this.width - this.imageWidth) / 2 + xOffset;
        int renderY = (this.height - this.imageHeight) / 2 + yOffset;
        int scale = 25;
        
        InventoryScreen.renderEntityInInventoryFollowsMouse(
                pGuiGraphics, 
                renderX - 25,
                renderY - 50,
                renderX + 25,
                renderY + 10,
                scale, 
                0.0625F,
                (float) pMouseX, 
                (float) pMouseY, 
                this.menu.wolf
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);

        for (int i = 0; i < WolfMenu.slotAmount; i++) {
            Slot currentSlot = menu.slots.get(i);

            if (!currentSlot.hasItem() && isHovering(currentSlot.x, currentSlot.y, 16, 16, x, y)) {
                Component tooltip = switch (i) {
                    case 0 -> Component.translatable(
                            "gui.createmechanicalcompanion.wolf_slot.defensive_tooltip");
                    case 1, 2, 3 -> Component.translatable(
                            "gui.createmechanicalcompanion.wolf_slot.offensive_tooltip");
                    case 4, 5 -> Component.translatable(
                            "gui.createmechanicalcompanion.wolf_slot.movement_tooltip");
                    case 6, 7, 8 -> Component.translatable(
                            "gui.createmechanicalcompanion.wolf_slot.utility_tooltip");
                    default -> Component.empty();
                };


                guiGraphics.renderTooltip(font, tooltip, x, y);
                return;
            }
        }
    }
}
