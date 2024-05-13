package com.owcadev.sheepcheats.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class Xray {
    // TODO Fix XRAY
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    public static boolean xrayEnabled = false;
    private static final RenderItem renderItem = minecraft.getRenderItem();

    public void renderXRayBlocks() {
        Block[] xrayBlocks = {
                Blocks.DIAMOND_ORE,
                Blocks.EMERALD_ORE,
                Blocks.GOLD_ORE,
        };

        for (Block block : xrayBlocks) {
            renderBlock(block);
        }
    }

    private void renderBlock(Block block) {
        RenderHelper.enableStandardItemLighting();
        renderItem.renderItem(new ItemStack(block), net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.NONE);
        RenderHelper.disableStandardItemLighting();
    }
}
