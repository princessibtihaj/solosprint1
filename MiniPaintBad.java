import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MiniPaintBad extends JFrame {

    public enum ToolType { SELECTION, BRUSH, ERASER }

    public MiniPaintBad() {
        setTitle("Mini Paint — Intentionally Bad Design");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        CanvasPanel canvas = new CanvasPanel();

        JPanel tools = new JPanel();
        JButton selection = new JButton("Selection");
        JButton brush = new JButton("Brush");
        JButton eraser = new JButton("Eraser");

        selection.addActionListener(e -> canvas.setCurrentTool(ToolType.SELECTION));
        brush.addActionListener(e -> canvas.setCurrentTool(ToolType.BRUSH));
        eraser.addActionListener(e -> canvas.setCurrentTool(ToolType.ERASER));

        tools.add(selection);
        tools.add(brush);
        tools.add(eraser);

        add(tools, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniPaintBad().setVisible(true));
    }

    static class CanvasPanel extends JPanel {
        private ToolType currentTool = ToolType.BRUSH;
        private BufferedImage layer;

        private boolean dragging = false;
        private int startX, startY;
        private int lastX, lastY;
        private int curX, curY;

        private final float brushSize = 8f;
        private final float eraserSize = 18f;

        public CanvasPanel() {
            setBackground(Color.WHITE);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    ensureLayer();
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ensureLayer();

                    startX = e.getX();
                    startY = e.getY();
                    lastX = startX;
                    lastY = startY;
                    curX = startX;
                    curY = startY;
                    dragging = true;

                    if (currentTool == ToolType.SELECTION) {

                    } else if (currentTool == ToolType.BRUSH) {
                        Graphics2D g2 = layer.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.BLACK);
                        g2.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(lastX, lastY, lastX, lastY);
                        g2.dispose();
                    } else if (currentTool == ToolType.ERASER) {
                        Graphics2D g2 = layer.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.WHITE);
                        g2.setStroke(new BasicStroke(eraserSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(lastX, lastY, lastX, lastY);
                        g2.dispose();
                    }

                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    curX = e.getX();
                    curY = e.getY();

                    if (currentTool == ToolType.SELECTION) {
                        Graphics2D g2 = layer.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        float[] dash = {6f, 6f};
                        g2.setColor(Color.BLACK);
                        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
                        g2.drawRect(Math.min(startX, curX), Math.min(startY, curY),
                                    Math.abs(curX - startX), Math.abs(curY - startY));
                        g2.dispose();
                    } else if (currentTool == ToolType.BRUSH) {
                        Graphics2D g2 = layer.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.BLACK);
                        g2.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(lastX, lastY, curX, curY);
                        g2.dispose();
                    } else if (currentTool == ToolType.ERASER) {
                        Graphics2D g2 = layer.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.WHITE);
                        g2.setStroke(new BasicStroke(eraserSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(lastX, lastY, curX, curY);
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

                    if (currentTool == ToolType.SELECTION) {
                    } else if (currentTool == ToolType.BRUSH) {
                        Graphics2D g2 = layer.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.BLACK);
                        g2.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(lastX, lastY, curX, curY);
                        g2.dispose();
                        lastX = curX;
                        lastY = curY;
                    } else if (currentTool == ToolType.ERASER) {
                        Graphics2D g2 = layer.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.WHITE);
                        g2.setStroke(new BasicStroke(eraserSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(lastX, lastY, curX, curY);
                        g2.dispose();
                        lastX = curX;
                        lastY = curY;
                    }

                    repaint();
                }
            });
        }

        public void setCurrentTool(ToolType tool) {
            this.currentTool = tool;
        }

        private void ensureLayer() {
            int w = Math.max(1, getWidth());
            int h = Math.max(1, getHeight());
            if (layer == null || layer.getWidth() != w || layer.getHeight() != h) {
                BufferedImage newLayer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = newLayer.createGraphics();
                if (layer != null) {
                    g2.drawImage(layer, 0, 0, null);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, w, h);
                }
                g2.dispose();
                layer = newLayer;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ensureLayer();
            g.drawImage(layer, 0, 0, null);

            if (dragging && currentTool == ToolType.SELECTION) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int x = Math.min(startX, curX);
                int y = Math.min(startY, curY);
                int w = Math.abs(curX - startX);
                int h = Math.abs(curY - startY);
                float[] dash = {6f, 6f};
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
                g2.drawRect(x, y, w, h);
                g2.dispose();
            }
        }
    }
}