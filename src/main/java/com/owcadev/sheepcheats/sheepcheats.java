package com.owcadev.sheepcheats;

import com.owcadev.sheepcheats.combat.AutoClicker;
import com.owcadev.sheepcheats.combat.AutoSword;
import com.owcadev.sheepcheats.combat.TriggerBot;
import com.owcadev.sheepcheats.menu.MenuCheat;
import com.owcadev.sheepcheats.render.ESP;
import com.owcadev.sheepcheats.render.FreeCam;
import com.owcadev.sheepcheats.render.Xray;
import com.owcadev.sheepcheats.render.nameTags;
import com.owcadev.sheepcheats.ui.KeyBinds;
import com.owcadev.sheepcheats.ui.Notifications;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@Mod(modid = "sheepcheats", version = "alpha-3", clientSideOnly = true)
public class sheepcheats {

    private boolean[] keyStates = new boolean[256];
    private static final Minecraft mc = Minecraft.getMinecraft();

    public boolean entityhitbox = false;
    public boolean playerhitbox = true;
    public boolean itemhitbox = true;
    public boolean autosword = true;
    public boolean autoclicker = false;
    public boolean tileEntityhitbox = true;
    public boolean triggerbot = false;
    public boolean freeCam = false;

    private boolean isChatOpen = false;

    private final ESP renderHitboxes = new ESP();
    private final AutoClicker clicker = new AutoClicker();
    private final TriggerBot triggerBot = new TriggerBot();
    private final AutoSword sword = new AutoSword();
    private final Xray xray = new Xray();
    private final FreeCam cam = new FreeCam();
    private final nameTags nametag = new nameTags();
    private final Notifications notification = new Notifications();
    private final KeyBinds key = new KeyBinds();


    @Mod.EventHandler
    public void preInit(net.minecraftforge.fml.common.event.FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        key.ImportKeybinds();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        triggerBot.triggerbot(event, triggerbot);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (autoclicker) {
            clicker.autoclicker();
        }

        renderHitboxes.EntityHitbox(event, entityhitbox, playerhitbox, itemhitbox, tileEntityhitbox);
        if (xray.xrayEnabled) {
            xray.renderXRayBlocks();
        }

        nametag.NameTag();

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderViewEntity(EntityViewRenderEvent.CameraSetup event) {
        cam.eventFreeCam(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onAttack(AttackEntityEvent event) {
        if (autosword) {
            sword.autoSword(event);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            notification.renderNotification();
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() != null && event.getGui().getClass().getName().equals("net.minecraft.client.gui.GuiChat")) {
            isChatOpen = true;
        } else if (isChatOpen) {
            isChatOpen = false;
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        cam.keysFreeCam();
        /*
        if (!isChatOpen && key.menu.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new MenuCheat());
        }
         */
        if (!isChatOpen && key.entityHitbox.isPressed()) {
            entityhitbox = !entityhitbox;
            notification.display("EntityHitbox is: " + checkturn(entityhitbox));
        }
        if (!isChatOpen && key.playerHitbox.isPressed()) {
            playerhitbox = !playerhitbox;
            notification.display("PlayerHitbox is: " + checkturn(playerhitbox));
        }
        if (!isChatOpen && key.autoSword.isPressed()) {
            autosword = !autosword;
            notification.display("AutoSword is: " + checkturn(autosword));
        }
        if (!isChatOpen && key.itemHitbox.isPressed()) {
            itemhitbox = !itemhitbox;
            notification.display("ItemHitbox is: " + checkturn(itemhitbox));
        }
        if (!isChatOpen && key.autoClicker.isPressed()) {
            autoclicker = !autoclicker;
            notification.display("AutoClicker is: " + checkturn(autoclicker));
        }
        if (!isChatOpen && key.triggerBot.isPressed()){
            triggerbot = !triggerbot;
            notification.display("TriggerBot is: " + checkturn(triggerbot));
        }
        if (!isChatOpen && key.freeCam.isPressed()){
            freeCam = !freeCam;
            notification.display("FreeCam is: " + checkturn(freeCam));
            cam.toggleFreecam();
        }
        if (!isChatOpen && key.tileEntityHitbox.isPressed()) {
            tileEntityhitbox = !tileEntityhitbox;
            notification.display("TileEntityHitbox is: " + checkturn(tileEntityhitbox));
        }
        /*
        // TODO Fix crashing minecraft
        if (!isChatOpen && key.xray.isPressed()) {
            xray.xrayEnabled = !xray.xrayEnabled;
            notification.display("X-RAY is: " + checkturn(xray.xrayEnabled));
        }
        */
        if (!isChatOpen && key.showstatusinchat.isPressed()) {
            sendMessageToCLient(
                        "EntityHitbox is: " + checkturn(entityhitbox) + "\n" +
                            "PlayerHitbox is: " + checkturn(playerhitbox) + "\n" +
                            "AutoSword is: " + checkturn(autosword) + "\n" +
                            "ItemHitbox is: " + checkturn(itemhitbox) + "\n" +
                            "AutoClicker is: " + checkturn(autoclicker) + "\n" +
                            "TileEntityHitbox is: " + checkturn(tileEntityhitbox) + "\n" +
                            "TriggerBot is: " + checkturn(triggerbot) + "\n" +
                            "TileEntityHitbox is: " + checkturn(tileEntityhitbox)
            );
        }
    }

    private static void sendMessageToChat(String message) {
        mc.player.connection.sendPacket(new CPacketChatMessage(message));
    }

    private void sendMessageToCLient(String message) {
        if (mc.player != null) {
            ITextComponent textComponent = new TextComponentString(message);
            mc.player.sendMessage(textComponent);
        }
    }

    private String checkturn(boolean check) {
        if (check) {
            return "On";
        } else {
            return "Off";
        }
    }
}
