package com.owcadev.sheepcheats.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class Notifications {

    private static String notificationText = "";
    private static long notificationEndTime = 0;

    public void renderNotification() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.fontRenderer == null) {
            return;
        }

        if (System.currentTimeMillis() < notificationEndTime) {
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int screenWidth = scaledResolution.getScaledWidth();
            int screenHeight = scaledResolution.getScaledHeight();

            int notificationWidth = mc.fontRenderer.getStringWidth(notificationText);
            int notificationHeight = mc.fontRenderer.FONT_HEIGHT;

            int notificationX = screenWidth - notificationWidth - 5;
            int notificationY = screenHeight - 50;

            Gui.drawRect(notificationX - 2, notificationY - 2, notificationX + notificationWidth + 2, notificationY + notificationHeight + 2, 0x90000000);
            mc.fontRenderer.drawStringWithShadow(notificationText, notificationX, notificationY, 0xFFFFFF);
        }
    }

    public void display(String text) {
        notificationText = text;
        notificationEndTime = System.currentTimeMillis() + 3000;
    }
}
