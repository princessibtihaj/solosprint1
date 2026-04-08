package paint;

import java.awt.*;
import java.awt.event.MouseEvent;

// Lets the user drag out a rectangular selection region.
// The dashed box is drawn as a live overlay so it never gets
// permanently burned into the canvas layer.
public class SelectionTool implements Tool {

    @Override
    public void onPress(MouseEvent e, Graphics2D g2) {}

    @Override
    public void onDrag(MouseEvent e, Graphics2D g2) {}

    @Override
    public void onRelease(MouseEvent e, Graphics2D g2) {}

    @Override
    public boolean needsOverlay() {
        return true;
    }

    @Override
    public void drawOverlay(Graphics2D g2, int startX, int startY, int curX, int curY) {
        int x = Math.min(startX, curX);
        int y = Math.min(startY, curY);
        int w = Math.abs(curX - startX);
        int h = Math.abs(curY - startY);

        float[] dash = {6f, 6f};
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10f, dash, 0f));
        g2.drawRect(x, y, w, h);
    }
}