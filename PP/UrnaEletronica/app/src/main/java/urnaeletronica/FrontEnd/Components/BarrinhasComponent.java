package urnaeletronica.FrontEnd.Components;

import javax.swing.JPanel;

public class BarrinhasComponent {
    private static JPanel panel = new JPanel();
    
    public BarrinhasComponent(){
        panel.setBackground(new java.awt.Color(0, 0, 0));
    }

    public static JPanel getPanel() {
        return panel;
    }
}
