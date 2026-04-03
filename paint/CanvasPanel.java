package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// The drawing surface. Its only jobs are forwarding mouse events to
// whatever tool is active and rendering the canvas layer to the screen.
// It has no idea how any tool actually works.
public class CanvasPanel extends JPanel {

    private Tool currentTool;
    private final CanvasLayer canvasLayer = new CanvasLayer();

    private boolean dragging = false;
    private int startX, startY;
    private int curX, curY;

    public CanvasPanel() {
        setBackground(Color.WHITE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvasLayer.ensureSize(getWidth(), getHeight());
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasLayer.ensureSize(getWidth(), getHeight());
                startX = e.getX();
                startY = e.getY();
                curX = startX;
                curY = startY;
                dragging = true;

                if (currentTool != null) {
                    Graphics2D g2 = canvasLayer.createGraphics();
                    currentTool.onPress(e, g2);
                    g2.dispose();
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();

                if (currentTool != null) {
                    Graphics2D g2 = canvasLayer.createGraphics();
                    currentTool.onRelease(e, g2);
                    g2.dispose();
                }

                dragging = false;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();

                if (currentTool != null) {
                    Graphics2D g2 = canvasLayer.createGraphics();
                    currentTool.onDrag(e, g2);
                    g2.dispose();
                }
                repaint();
            }
        });
    }

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        canvasLayer.ensureSize(getWidth(), getHeight());
        g.drawImage(canvasLayer.getImg(), 0, 0, null);

        // Draw the live overlay on top if the active tool needs it (e.g. selection box)
        if (dragging && currentTool != null && currentTool.needsOverlay()) {
            Graphics2D g2 = (Graphics2D) g.create();
            currentTool.drawOverlay(g2, startX, startY, curX, curY);
            g2.dispose();
        }
    }
}