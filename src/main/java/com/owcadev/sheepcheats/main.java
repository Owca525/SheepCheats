package com.owcadev.sheepcheats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.*;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import net.minecraftforge.event.entity.player.AttackEntityEvent;

@Mod(modid = "SheapCheats", version = "alpha", clientSideOnly = true)
public class main {

    private boolean[] keyStates = new boolean[256];
    private final Minecraft mc = Minecraft.getMinecraft();

    public boolean entityhitbox = false;
    public boolean playerhitbox = true;
    public boolean itemhitbox = true;
    public boolean autosword = true;
    public boolean autoclicker = false;
    public boolean tileEntityhitbox = true;
    public boolean dmgeffectoff = true;

    private boolean removeShake = true;

    private static boolean disableScreenShake = false;

    private static String notificationText = "";
    private static long notificationEndTime = 0;

    private esp renderHitboxes = new esp();
    private combat combat = new combat();

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void preInit(net.minecraftforge.fml.common.event.FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.ClientTickEvent.Phase.START) {
            for (int i = 0; i < keyStates.length; i++) {
                boolean currentState = Keyboard.isKeyDown(i);
                if (currentState != keyStates[i]) {
                    keyStates[i] = currentState;
                    if (currentState) {
                        keybinds(Keyboard.getKeyName(i));
                    }
                }
            }
        }
    }

    public void keybinds(String key) {
        switch (key){
            case "H":
                entityhitbox = !entityhitbox;
                displayNotification("EntityHitbox is: " + entityhitbox, 3000);
                break;
            case "I":
                playerhitbox = !playerhitbox;
                displayNotification("PlayerHitbox is: " + playerhitbox, 3000);
                break;
            case "O":
                autosword = !autosword;
                displayNotification("AutoSword is: " + autosword, 3000);
                break;
            case "J":
                itemhitbox = !itemhitbox;
                displayNotification("ItemHitbox is: " + itemhitbox, 3000);
                break;
            case "K":
                autoclicker = !autoclicker;
                displayNotification("AutoClicker is: " + autoclicker, 3000);
                break;
            case "V":
                tileEntityhitbox = !tileEntityhitbox;
                displayNotification("TileEntityHitbox is: " + tileEntityhitbox, 3000);
            //case "B":
            //    dmgeffectoff = !dmgeffectoff;
            //    displayNotification("GetDamageEffect is: " + dmgeffectoff, 3000);
        }
        //if (Objects.equals(key, "G")) {
        //    combat.clearHotbarExceptTools(mc.player);
        //    displayNotification("Hotbar Cleared", 3000);
        //}
    }
    private void sendMessageToChat(String message) {
        mc.player.connection.sendPacket(new CPacketChatMessage(message));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        renderHitboxes.EntityHitbox(event, entityhitbox, playerhitbox, itemhitbox, tileEntityhitbox);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (autosword) {
            combat.autoSword(event);
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (autoclicker) {
            combat.autoclicker();
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            renderNotification();
        }
    }

    private void renderNotification() {
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

    public static void displayNotification(String text, int durationMillis) {
        notificationText = text;
        notificationEndTime = System.currentTimeMillis() + durationMillis;
    }
}
