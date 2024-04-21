package com.owcadev.sheepcheats;

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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

public class esp {
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

        for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
            if (tileEntityhitbox && tileEntity instanceof TileEntityBed) {
                GL11.glColor3f(1.0f, 0.0f, 0.0f);
                AxisAlignedBB bed = tileEntity.getRenderBoundingBox();
                renderAABB(bed);
            }

            if (tileEntityhitbox && tileEntity instanceof TileEntityEnderChest) {
                GL11.glColor3f(0.5F, 0.0F, 0.5F);
                //AxisAlignedBB chestBox = tileEntity.getRenderBoundingBox();
                //renderAABB(chestBox);

                double minX = tileEntity.getPos().getX();
                double minY = tileEntity.getPos().getY();
                double minZ = tileEntity.getPos().getZ();
                double maxX = minX + 1;
                double maxY = minY + 1;
                double maxZ = minZ + 1;
                AxisAlignedBB adjustedBox = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
                renderAABB(adjustedBox);
            }

            if (tileEntityhitbox && tileEntity instanceof TileEntityChest) {
                GL11.glColor3f(0.8F, 0.4F, 0.0F);
                double minX = tileEntity.getPos().getX();
                double minY = tileEntity.getPos().getY();
                double minZ = tileEntity.getPos().getZ();
                double maxX = minX + 1;
                double maxY = minY + 1;
                double maxZ = minZ + 1;
                AxisAlignedBB adjustedBox = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
                renderAABB(adjustedBox);
            }
        }

        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && !(entity instanceof EntityItem) && !(entity instanceof EntityArmorStand) && entityhitbox) {
                GL11.glColor3f(0.0F, 1.0F, 0.0F);
                render(entity);
            }

            if (entity instanceof EntityItem && itemhitbox) {
                GL11.glColor3f(0.0F, 0.0F, 1.0F);
                render(entity);
            }

            if (entity != mc.player && entity instanceof EntityPlayer && playerhitbox) {
                GL11.glColor3f(1.0F, 1.0F, 0.0F);
                render(entity);
            }

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
