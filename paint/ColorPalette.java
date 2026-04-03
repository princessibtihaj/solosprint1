package paint;

import javax.swing.*;
import java.awt.*;

/** Simple row of color swatches. Click one to set the current drawing color. */
public class ColorPalette extends JPanel {

    public interface ColorChangeListener {
        void onColorChanged(Color color);
    }

    private static final Color[] COLORS = {
        Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
    };

    private Color selected = Color.BLACK;
    private ColorChangeListener listener;

    public ColorPalette() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
        for (Color c : COLORS) {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(22, 22));
            b.setBackground(c);
            b.setOpaque(true);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            b.addActionListener(e -> {
                selected = c;
                if (listener != null) listener.onColorChanged(c);
            });
            add(b);
        }
    }

    public void setColorChangeListener(ColorChangeListener listener) {
        this.listener = listener;
    }

    public Color getSelectedColor() {
        return selected;
    }
}
