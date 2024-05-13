package com.owcadev.sheepcheats.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.util.Random;

public class AutoClicker {

    private volatile boolean isClicking = false;
    private Thread clickerThread;
    private final Object lock = new Object();

    public void autoclicker() {
        if (org.lwjgl.input.Mouse.isButtonDown(0)) {
            isClicking = true;
            if (clickerThread == null || !clickerThread.isAlive()) {
                clickerThread = new Thread(this::click);
                clickerThread.start();
            }
        } else {
            isClicking = false;
        }
    }

    private void click() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        try {
            Robot robot = new Robot();

            while (true) {
                synchronized (lock) {
                    if (!isClicking) {
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
                    for (int i = 0; i < new Random().nextInt(5) + 10; i++) {
                        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(new Random().nextInt(40) + 20);
                    }

                    Thread.sleep(1);
                }
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
