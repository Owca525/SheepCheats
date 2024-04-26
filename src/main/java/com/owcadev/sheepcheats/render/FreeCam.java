package com.owcadev.sheepcheats.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;


public class FreeCam {
    private BlockPos savedPosition;

    private EntityPlayerSP player;
    private boolean freecamEnabled;

    public void toggleFreecam(boolean freecam) {
        freecamEnabled = freecam;
        if (freecamEnabled) {
            Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
            Minecraft.getMinecraft().player.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
        } else {
            player = Minecraft.getMinecraft().player;
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
        }
        freecamEnabled = !freecamEnabled;
    }
}
