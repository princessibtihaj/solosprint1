package paint;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

// The contract every drawing tool must follow. CanvasPanel only ever
// talks to this interface, so swapping tools in and out is painless.
public interface Tool {
    void onPress(MouseEvent e, Graphics2D g2);
    void onDrag(MouseEvent e, Graphics2D g2);
    void onRelease(MouseEvent e, Graphics2D g2);

    // Some tools (like Selection) draw a temporary preview instead of painting directly
    boolean needsOverlay();
    void drawOverlay(Graphics2D g2, int startX, int startY, int curX, int curY);
}