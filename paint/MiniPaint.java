package paint;

import javax.swing.*;
import java.awt.*;

// Entry point. Assembles the canvas, toolbar, palette, and tools,
// then hands control over to Swing.
public class MiniPaint extends JFrame {

    // Keep track of the active tool type so we can recreate it with a new color
    private CanvasPanel canvas;
    private ToolBar toolbar;
    private ColorPalette palette;
    private Color activeColor = Color.BLACK;

    // We hold references so we can swap colors without rebuilding the whole UI
    private Tool selectionTool = new SelectionTool();
    private Tool eraserTool    = new EraserTool(18f);
    private Tool lineTool      = new LineTool(activeColor);
    private Tool rectangleTool = new RectangleTool(activeColor);

    public MiniPaint() {
        setTitle("Mini Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        canvas  = new CanvasPanel();
        toolbar = new ToolBar(canvas);
        palette = new ColorPalette();

        toolbar.addTool("Selection", selectionTool);
        toolbar.addTool("Brush",     new BrushTool(8f, activeColor));
        toolbar.addTool("Eraser",    eraserTool);
        toolbar.addTool("Line",      lineTool);
        toolbar.addTool("Rectangle", rectangleTool);

        // Start with brush active - set it through toolbar so activeLabel is set
        toolbar.setActiveTool("Brush");

        // When the user picks a color, recreate color-aware tools with the new color
        // and immediately apply it to whatever is currently active on the canvas
        palette.setColorChangeListener(color -> {
            activeColor = color;

            // Rebuild the color-sensitive tools and re-register them in the toolbar
            // updateTool will automatically update the canvas if that tool is active
            toolbar.updateTool("Brush",     new BrushTool(8f, activeColor));
            toolbar.updateTool("Line",      new LineTool(activeColor));
            toolbar.updateTool("Rectangle", new RectangleTool(activeColor));
        });

        // Toolbar on top, color palette under it
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(toolbar);
        top.add(palette);

        add(top, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniPaint().setVisible(true));
    }
}
