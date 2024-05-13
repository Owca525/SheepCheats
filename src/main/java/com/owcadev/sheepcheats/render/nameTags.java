package com.owcadev.sheepcheats.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class nameTags {

    Minecraft mc = Minecraft.getMinecraft();

    public void NameTag() {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        List<Entity> entities = Minecraft.getMinecraft().world.loadedEntityList;

        for (Entity entity : entities) {
            // items
            if (entity instanceof EntityItem) {
                EntityItem entityItem = (EntityItem) entity;
                prepareItemNameTag(entityItem, renderItem);
            }

            // Mob
            if (!(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && !(entity instanceof EntityItem) && !(entity instanceof EntityArmorStand)) {
                EntityLivingBase livingEntity = (EntityLivingBase) entity;
                prepareMobNameTag(livingEntity, renderItem);
            }

            // Players
            if (entity != mc.player && entity instanceof EntityPlayer) {
                EntityLivingBase livingEntity = (EntityLivingBase) entity;
                prepareMobNameTag(livingEntity, renderItem);
            }
        }
    }
    private void prepareMobNameTag(EntityLivingBase entity, RenderItem renderItem) {
        double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().getRenderPartialTicks();
        double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().getRenderPartialTicks();
        double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().getRenderPartialTicks();

        String mobName = entity.getName() + " (" + (int) entity.getHealth() + ")";

        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY + entity.height + 0.5, posZ);
        renderTag(posX,posY,posZ,mobName);
    }

    private void prepareItemNameTag(EntityItem entityItem, RenderItem renderItem) {
        double posX = entityItem.lastTickPosX + (entityItem.posX - entityItem.lastTickPosX) * Minecraft.getMinecraft().getRenderPartialTicks();
        double posY = entityItem.lastTickPosY + (entityItem.posY - entityItem.lastTickPosY) * Minecraft.getMinecraft().getRenderPartialTicks();
        double posZ = entityItem.lastTickPosZ + (entityItem.posZ - entityItem.lastTickPosZ) * Minecraft.getMinecraft().getRenderPartialTicks();

        String itemName = entityItem.getItem().getDisplayName();
        if (entityItem.getItem().getCount() != 1) {
            itemName = entityItem.getItem().getDisplayName() + " (" + entityItem.getItem().getCount() + ")";
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY + 0.5, posZ);
        renderTag(posX,posY,posZ,itemName);
    }

    private void renderTag(double posX, double posY, double posZ, String name) {
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        int stringWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(name);
        int backgroundWidth = stringWidth + 4;
        int backgroundHeight = 8;
        int x = -backgroundWidth / 2;
        int y = 0;
        int z = 0;

        // Background
        GlStateManager.disableTexture2D();
        GlStateManager.color(0.0F, 0.0F, 0.0F, 0.5F);
        GlStateManager.glBegin(GL11.GL_QUADS);
        GlStateManager.glVertex3f(x - 2, y - 2, z);
        GlStateManager.glVertex3f(x - 2, y + backgroundHeight + 2, z);
        GlStateManager.glVertex3f(x + backgroundWidth + 2, y + backgroundHeight + 2, z);
        GlStateManager.glVertex3f(x + backgroundWidth + 2, y - 2, z);
        GlStateManager.glEnd();
        GlStateManager.enableTexture2D();

        // Text
        Minecraft.getMinecraft().fontRenderer.drawString(name, x + 2, y, 0xFFFFFF);

        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
