package com.owcadev.sheepcheats.combat;

import java.awt.*;
import java.util.Random;

public class AutoClicker {

    private volatile boolean isClicking = false;
    private Thread clickerThread;
    private final Object lock = new Object();

    public void autoclicker() {
        if (org.lwjgl.input.Mouse.isButtonDown(0)) {
            isClicking = true;
            if (clickerThread == null || !clickerThread.isAlive()) {
                clickerThread = new Thread(this::click);
                clickerThread.start();
            }
        } else {
            isClicking = false;
        }
    }

    private void click() {
        try {
            Robot robot = new Robot();

            while (true) {
                synchronized (lock) {
                    if (!isClicking) {
                        break;
                    }
                    for (int i = 0; i < new Random().nextInt(6) + 22; i++) {
                        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(new Random().nextInt(20) + 20);
                    }
                    Thread.sleep(new Random().nextInt(20) + 10);
                }
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
