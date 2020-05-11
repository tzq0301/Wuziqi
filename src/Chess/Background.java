package Chess;

import javax.swing.*;
import java.awt.*;

public class Background {

    private static Background instance;

    private Image backgroundImage;

    private Background() {
        backgroundImage = new ImageIcon("src\\images\\background.jpg").getImage();
    }

    public static Background getInstance() {
        if (instance == null) {
            instance = new Background();
        }
        return instance;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }
}
