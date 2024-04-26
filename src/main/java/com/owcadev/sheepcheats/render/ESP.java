package com.owcadev.sheepcheats.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class ESP {
    public void EntityHitbox(RenderWorldLastEvent event, boolean entityhitbox, boolean playerhitbox, boolean itemhitbox, boolean tileEntityhitbox) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderManager renderManager = mc.getRenderManager();

        double renderPosX = renderManager.viewerPosX;
        double renderPosY = renderManager.viewerPosY;
        double renderPosZ = renderManager.viewerPosZ;

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glTranslated(-renderPosX, -renderPosY, -renderPosZ);

        GL11.glLineWidth(2.0F);
        GL11.glPushMatrix();

        // TileEntity
        for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
            // Bed
            if (tileEntityhitbox && tileEntity instanceof TileEntityBed) {
                GL11.glColor3f(0.5F, 0.0F, 0.0F);
                AxisAlignedBB bed = tileEntity.getRenderBoundingBox();
                renderAABB(bed);
            }

            // ender chest
            if (tileEntityhitbox && tileEntity instanceof TileEntityEnderChest) {
                GL11.glColor3f(0.5F, 0.0F, 0.5F);
                AxisAlignedBB chestBox = tileEntity.getRenderBoundingBox();
                renderAABB(chestBox);
            }

            // chest
            if (tileEntityhitbox && tileEntity instanceof TileEntityChest) {
                GL11.glColor3f(0.8F, 0.4F, 0.0F);
                BlockPos pos = tileEntity.getPos();
                double x = pos.getX();
                double y = pos.getY();
                double z = pos.getZ();

                double chestWidth = 16;
                double chestHeight = 16;

                double minX = x + (16 - chestWidth) / 32;
                double minY = y;
                double minZ = z + (16 - chestWidth) / 32;
                double maxX = minX + chestWidth / 16;
                double maxY = minY + chestHeight / 16;
                double maxZ = minZ + chestWidth / 16;
                AxisAlignedBB adjustedBox = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
                renderAABB(adjustedBox);
            }
        }

        // Normal Entity
        for (Entity entity : mc.world.loadedEntityList) {
            // Mob
            if (!(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && !(entity instanceof EntityItem) && !(entity instanceof EntityArmorStand) && entityhitbox) {
                GL11.glColor3f(0.0F, 1.0F, 0.0F);
                render(entity);
            }

            // Items
            if (entity instanceof EntityItem && itemhitbox) {
                GL11.glColor3f(0.0F, 0.0F, 1.0F);
                render(entity);
            }

            // Players
            if (entity != mc.player && entity instanceof EntityPlayer && playerhitbox) {
                GL11.glColor3f(1.0F, 1.0F, 0.0F);
                render(entity);
            }

            // Arrow
            if (entity instanceof EntityTippedArrow && itemhitbox) {
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
                render(entity);
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        GL11.glPopMatrix();
    }
    // render hitboxes
    private void render(Entity entity){
        AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
        renderAABB(axisAlignedBB);
    }

    private void renderAABB(AxisAlignedBB axisAlignedBB) {
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
    }
}
