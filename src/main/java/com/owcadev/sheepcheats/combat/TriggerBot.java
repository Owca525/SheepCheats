package com.owcadev.sheepcheats.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Random;

public class TriggerBot {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private boolean triggerbotlock;

    private Thread triggerbotThread;

    private void bot() {
        for (int i = 0; i < new Random().nextInt(6) + 22; i++) {
            try {
                if (!triggerbotlock) {
                    break;
                }
                mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                Thread.sleep(new Random().nextInt(11) + 30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void triggerbot(RenderGameOverlayEvent.Pre event, boolean triggerbot) {
        if (triggerbotThread != null) {
            if (!triggerbotThread.isAlive()) {
                triggerbotlock = false;
            }
        }

        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {} else {
            triggerbotlock = false;
        }

        if (triggerbot && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (!triggerbotlock && mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                triggerbotlock = true;

                triggerbotThread = new Thread(this::bot);
                triggerbotThread.start();
            }
        }
    }
}
