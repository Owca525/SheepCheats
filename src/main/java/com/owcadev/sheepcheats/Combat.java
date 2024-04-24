package com.owcadev.sheepcheats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.awt.*;
import java.util.Random;

public class Combat {
    private volatile boolean isClicking = false;
    private Thread clickerThread;
    private final Object lock = new Object();
    private final Object drop = new Object();

    public void autoSword(AttackEntityEvent event){
        EntityPlayer player = event.getEntityPlayer();
        if (player != null) {
            Entity targetEntity = event.getTarget();
            if (targetEntity instanceof EntityLivingBase) {
                EntityLivingBase target = (EntityLivingBase) targetEntity;
                changeSlot(player, target);
            }
        }
    }

    private void changeSlot(EntityPlayer player, EntityLivingBase target) {
        int swordSlot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemSword) {
                swordSlot = i;
                break;
            }
        }

        if (swordSlot != -1) {
            player.inventory.currentItem = swordSlot;
            player.inventory.setInventorySlotContents(swordSlot, player.getHeldItemMainhand());
        }
    }

    public void autoclicker() {
        if (org.lwjgl.input.Mouse.isButtonDown(0)) {
            isClicking = true;
            if (clickerThread == null || !clickerThread.isAlive()) {
                clickerThread = new Thread(this::autoClick);
                clickerThread.start();
            }
        } else {
            isClicking = false;
        }
    }

    private void autoClick() {
        try {
            Robot robot = new Robot();

            while (true) {
                synchronized (lock) {
                    if (!isClicking) {
                        break;
                    }
                    int clicks = new Random().nextInt(6) + 22;
                    int sleep = new Random().nextInt(20);;
                    for (int i = 0; i < clicks; i++) {
                        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(sleep);
                    }
                    Thread.sleep(5);
                }
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
