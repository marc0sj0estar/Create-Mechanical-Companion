package net.myr.createmechanicalcompanion.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

public class WolfScreen extends AbstractContainerScreen<WolfMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "textures/gui/inventory.png");
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

        for(int i = 0; i < WolfMenu.slotAmount; i++){
            Slot currentSlot = wolfMenu.slots.get(i);
            if(currentSlot.hasItem()){
                pGuiGraphics.blit(BACKGROUND_TEXTURE, x + currentSlot.x - 2, y + currentSlot.y - 2, 190, 20, 20, 20);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        int xOffset = 64;
        int yOffset = 53;
        int x = (this.width - this.imageWidth) / 2 + xOffset;
        int y = (this.height - this.imageHeight) / 2 + yOffset;
        int scale = 30;
        float yMouseOffset = -25;
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x, y, scale, x - mouseX, y - mouseY + yMouseOffset, this.menu.wolf);
    }
}

