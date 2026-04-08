package paint;

import java.awt.*;
import java.awt.event.MouseEvent;

// Draws a solid-outlined rectangle. The user drags from one corner to the
// opposite corner, and the shape commits to the layer on release.
public class RectangleTool implements Tool {

    private final Color color;
    private int startX, startY;

    public RectangleTool(Color color) {
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
        int x = Math.min(startX, e.getX());
        int y = Math.min(startY, e.getY());
        int w = Math.abs(e.getX() - startX);
        int h = Math.abs(e.getY() - startY);

        applyStyle(g2);
        g2.drawRect(x, y, w, h);
    }

    @Override
    public boolean needsOverlay() { return true; }

    @Override
    public void drawOverlay(Graphics2D g2, int startX, int startY, int curX, int curY) {
        int x = Math.min(startX, curX);
        int y = Math.min(startY, curY);
        int w = Math.abs(curX - startX);
        int h = Math.abs(curY - startY);

        applyStyle(g2);
        g2.drawRect(x, y, w, h);
    }

    private void applyStyle(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2f));
    }
}
