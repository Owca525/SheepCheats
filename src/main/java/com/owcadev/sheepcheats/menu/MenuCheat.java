package com.owcadev.sheepcheats.menu;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class MenuCheat extends GuiScreen {
    private static final int GUI_WIDTH = 200;
    private static final int GUI_HEIGHT = 150;


    @Override
    public void initGui() {
        int x = (this.width - GUI_WIDTH) / 2;
        int y = (this.height - GUI_HEIGHT) / 2;

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawString(fontRenderer, "SheepCheats", 10, 10, 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
