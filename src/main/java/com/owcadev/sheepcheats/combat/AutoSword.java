package com.owcadev.sheepcheats.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class AutoSword {
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
}
