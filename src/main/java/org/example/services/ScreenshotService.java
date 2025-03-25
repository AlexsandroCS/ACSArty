package org.example.services;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenshotService {
    public BufferedImage captureMilArea() throws AWTException {
        String procName = "HLL-Win64-Shipping";
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, procName);

        if (hwnd == null) {
            String engine = "UnrealWindow";
            hwnd = User32.INSTANCE.FindWindow(engine, null);
        }

        if (hwnd == null) {
            System.out.println("Hellzinho não foi encontrado chefe!");
            return null;
        }

        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);

        int wArea = 150;
        int hArea = 30;
        int xArea = rect.left + 1708;
        int yArea = rect.top + 935;

        Rectangle screenRect = new Rectangle(xArea, yArea, wArea, hArea);
        return new Robot().createScreenCapture(screenRect);
    }
}