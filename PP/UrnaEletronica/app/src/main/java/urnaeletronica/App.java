package urnaeletronica;

import com.formdev.flatlaf.FlatLightLaf;

import urnaeletronica.FrontEnd.Frames.LoginFrames.LoginFrame;

public class App {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible();
    }
}
