package com.owcadev.sheepcheats;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;
//
// This File is a mess
//
public class KeyBinds {

    public static KeyBinding entityHitbox;
    public static KeyBinding playerHitbox;
    public static KeyBinding autoSword;
    public static KeyBinding itemHitbox;
    public static KeyBinding autoClicker;
    public static KeyBinding tileEntityHitbox;
    public static KeyBinding triggerBot;
    public static KeyBinding showstatusinchat;

    private static String category = "SheepCheats";

    public void ImportKeybinds() {
        entityHitbox = new KeyBinding("EntityHitbox", Keyboard.KEY_H, category);
        playerHitbox = new KeyBinding("PlayerHitbox", Keyboard.KEY_I, category);
        autoSword = new KeyBinding("AutoSword", Keyboard.KEY_O, category);
        itemHitbox = new KeyBinding("ItemHitbox", Keyboard.KEY_J, category);
        autoClicker = new KeyBinding("AutoClicker", Keyboard.KEY_K, category);
        tileEntityHitbox = new KeyBinding("TileEntityHitbox", Keyboard.KEY_V, category);
        showstatusinchat = new KeyBinding("Show Status cheats", Keyboard.KEY_M, category);
        triggerBot = new KeyBinding("triggerBot", Keyboard.KEY_X, category);

        ClientRegistry.registerKeyBinding(entityHitbox);
        ClientRegistry.registerKeyBinding(playerHitbox);
        ClientRegistry.registerKeyBinding(autoSword);
        ClientRegistry.registerKeyBinding(itemHitbox);
        ClientRegistry.registerKeyBinding(autoClicker);
        ClientRegistry.registerKeyBinding(tileEntityHitbox);
        ClientRegistry.registerKeyBinding(showstatusinchat);
        ClientRegistry.registerKeyBinding(triggerBot);
    }
}
