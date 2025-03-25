package org.example.theme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class StyledButtonUI extends BasicButtonUI {
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fundo do botão
        if (button.getModel().isPressed()) {
            g2.setColor(Theme.PRIMARY_COLOR.darker());
        } else if (button.getModel().isRollover() || button.isSelected()) {
            g2.setColor(Theme.PRIMARY_COLOR);
        } else {
            g2.setColor(Theme.DARKER_BG);
        }

        // Desenha o fundo arredondado
        g2.fill(new RoundRectangle2D.Double(0, 0, button.getWidth(), button.getHeight(), 15, 15));

        // Borda
        g2.setColor(Theme.PRIMARY_COLOR.brighter());
        g2.draw(new RoundRectangle2D.Double(0, 0, button.getWidth()-1, button.getHeight()-1, 15, 15));

        // Texto
        g2.setColor(Theme.TEXT_COLOR);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle textRect = new Rectangle(button.getWidth(), button.getHeight());
        int textX = (textRect.width - fm.stringWidth(button.getText())) / 2;
        int textY = (textRect.height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(button.getText(), textX, textY);

        g2.dispose();
    }
}