package app;

import javax.swing.*;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {

        // ─────── Делаем текст идеально читаемым в 2025 году ──────
        System.setProperty("awt.useSystemAAFontSettings", "on");     // Лучший антиалиасинг от системы
        System.setProperty("swing.aatext", "true");                  // Принудительно включаем сглаживание

        // Самые важные свойства для чёткого текста (работают с JDK 8+)
        UIManager.put("Swing.boldMetal", Boolean.FALSE);             // Убираем жирный уродливый шрифт в заголовках
        UIManager.put("Label.font", new javax.swing.plaf.FontUIResource("Segoe UI", java.awt.Font.PLAIN, 14));
        UIManager.put("Button.font", new javax.swing.plaf.FontUIResource("Segoe UI", java.awt.Font.PLAIN, 14));
        UIManager.put("TextField.font", new javax.swing.plaf.FontUIResource("Segoe UI", java.awt.Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new javax.swing.plaf.FontUIResource("Segoe UI", java.awt.Font.PLAIN, 14));
        UIManager.put("Label.foreground", new java.awt.Color(240, 240, 240)); // читаемый белый текст на тёмном фоне

        // Если хочешь светлую тему — поменяй только эту строку:
        UIManager.put("Label.foreground", new java.awt.Color(30, 30, 30));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new Application().run());
    }
}
