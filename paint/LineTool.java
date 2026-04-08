package paint;

import java.awt.*;
import java.awt.event.MouseEvent;

// Draws a straight line from where the user clicked to where they released.
// The live preview is drawn as an overlay so the line only commits on release.
public class LineTool implements Tool {

    private final Color color;
    private int startX, startY;

    public LineTool(Color color) {
        this.color = color;
    }

    @Override
    public void onPress(MouseEvent e, Graphics2D g2) {
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void onDrag(MouseEvent e, Graphics2D g2) {}

    @Override
    public void onRelease(MouseEvent e, Graphics2D g2) {
        applyStyle(g2);
        g2.drawLine(startX, startY, e.getX(), e.getY());
    }

    @Override
    public boolean needsOverlay() { return true; }

    @Override
    public void drawOverlay(Graphics2D g2, int startX, int startY, int curX, int curY) {
        applyStyle(g2);
        g2.drawLine(startX, startY, curX, curY);
    }

    private void applyStyle(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }
}
