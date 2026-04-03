package paint;

import java.awt.*;
import java.awt.event.MouseEvent;

// Works exactly like the brush but paints white, effectively erasing
// whatever is underneath. Keeping it as a separate class means the
// brush doesn't have to know anything about erasing.
public class EraserTool implements Tool {

    private final float size;
    private int lastX, lastY;

    public EraserTool(float size) {
        this.size = size;
    }

    @Override
    public void onPress(MouseEvent e, Graphics2D g2) {
        lastX = e.getX();
        lastY = e.getY();
        applyStyle(g2);
        g2.drawLine(lastX, lastY, lastX, lastY);
    }

    @Override
    public void onDrag(MouseEvent e, Graphics2D g2) {
        int curX = e.getX();
        int curY = e.getY();
        applyStyle(g2);
        g2.drawLine(lastX, lastY, curX, curY);
        lastX = curX;
        lastY = curY;
    }

    @Override
    public void onRelease(MouseEvent e, Graphics2D g2) {
        applyStyle(g2);
        g2.drawLine(lastX, lastY, e.getX(), e.getY());
    }

    private void applyStyle(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    @Override
    public boolean needsOverlay() { return false; }

    @Override
    public void drawOverlay(Graphics2D g2, int startX, int startY, int curX, int curY) {}
}