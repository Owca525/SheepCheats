package com.owcadev.sheepcheats.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.lwjgl.input.Keyboard;


public class FreeCam {

    private boolean freecamEnabled = false;
    private Vec3d playerPosition;

    public void keysFreeCam(){
        if (freecamEnabled) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                playerPosition = playerPosition.add(player.getLookVec().scale(1));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                playerPosition = playerPosition.subtract(player.getLookVec().scale(1));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                Vec3d leftVec = new Vec3d(-Math.sin(Math.toRadians(player.rotationYaw)), 0, Math.cos(Math.toRadians(player.rotationYaw)));
                playerPosition = playerPosition.add(leftVec.scale(1));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                Vec3d rightVec = new Vec3d(Math.sin(Math.toRadians(player.rotationYaw)), 0, -Math.cos(Math.toRadians(player.rotationYaw)));
                playerPosition = playerPosition.add(rightVec.scale(1));
            }
        }
    }

    public void eventFreeCam(EntityViewRenderEvent.CameraSetup event) {
        if (freecamEnabled) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            player.setPosition(playerPosition.x, playerPosition.y, playerPosition.z);
            event.setPitch(player.rotationPitch);
            event.setYaw(player.rotationYaw);
            event.setRoll(0);
        }
    }

    public void toggleFreecam() {
        freecamEnabled = !freecamEnabled;
        if (freecamEnabled) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            playerPosition = player.getPositionVector();
        }
    }
}
