package com.owcadev.sheepcheats.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.awt.*;
import java.util.Random;

public class TriggerBot {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private boolean triggerbotlock;

    private Thread triggerbotThread;

    private boolean isPlayerInInventory() {
        Container container = mc.player.openContainer;

        if (container != null && !(container instanceof ContainerPlayer)) {
            return true;
        } else {
            return false;
        }
    }

    private void click() throws AWTException {
        Robot robot = new Robot();
        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
    }

    private void bot() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        for (int i = 0; i < new Random().nextInt(6) + 22; i++) {
            try {
                if (!triggerbotlock) {
                    break;
                }

                if (isPlayerInInventory()) {
                    break;
                }

                /*
                if (Minecraft.getMinecraft().currentScreen == null || Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
                    break;
                }

                if (player != null && player.isDead) {
                   break;
                }
                */
                click();
                Thread.sleep(new Random().nextInt(50) + 50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (AWTException e) {
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

        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {} else {
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
