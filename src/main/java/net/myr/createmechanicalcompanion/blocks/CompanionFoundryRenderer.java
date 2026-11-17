package net.myr.createmechanicalcompanion.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class CompanionFoundryRenderer extends KineticBlockEntityRenderer<CompanionFoundryEntity> {

    public CompanionFoundryRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CompanionFoundryEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                be.getBlockState(),
                ms,
                buffer,
                light,
                overlay
        );

        Direction.Axis axis = be.getBlockState()
                .getValue(CompanionFoundryBlock.HORIZONTAL_FACING)
                .getAxis();

        SuperByteBuffer shaft = CachedBuffers.block(
                KineticBlockEntityRenderer.shaft(axis)
        );

        KineticBlockEntityRenderer.renderRotatingBuffer(
                be,
                shaft,
                ms,
                buffer.getBuffer(RenderType.solid()),
                light
        );
    }
}
