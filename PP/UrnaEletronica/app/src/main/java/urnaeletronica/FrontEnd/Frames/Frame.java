package urnaeletronica.FrontEnd.Frames;

import javax.swing.JFrame;

public abstract class Frame {
    public static void changeFrame(JFrame frame, JFrame newFrame) {
        frame.dispose();
        newFrame.setVisible(true);
    }
}
