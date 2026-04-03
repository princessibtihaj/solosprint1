package paint;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

// Builds the tool selection bar at the top of the window.
// Adding a new tool is just one more addTool() call — nothing else changes.
public class ToolBar extends JPanel {

    private final CanvasPanel canvas;

    // Keep a map of label -> tool so we can update them when the color changes
    private final Map<String, Tool> toolMap = new LinkedHashMap<>();
    private Tool activeTool;
    private String activeLabel;  // Track which tool is active by label

    public ToolBar(CanvasPanel canvas) {
        this.canvas = canvas;
    }

    public void addTool(String label, Tool tool) {
        toolMap.put(label, tool);

        JButton button = new JButton(label);
        button.addActionListener(e -> {
            activeLabel = label;
            activeTool = toolMap.get(label);
            canvas.setTool(activeTool);
        });
        add(button);
    }

    // Replaces a tool in the map without touching the button layout
    // If this tool is currently active, update the active tool and canvas too
    public void updateTool(String label, Tool tool) {
        toolMap.put(label, tool);
        // If this is the currently active tool, update it immediately
        if (activeLabel != null && activeLabel.equals(label)) {
            activeTool = tool;
            canvas.setTool(tool);
        }
    }

    public Tool getActiveTool() {
        return activeTool;
    }
    
    // Set the active tool by label (used for initial setup)
    public void setActiveTool(String label) {
        activeLabel = label;
        activeTool = toolMap.get(label);
        if (activeTool != null) {
            canvas.setTool(activeTool);
        }
    }
}