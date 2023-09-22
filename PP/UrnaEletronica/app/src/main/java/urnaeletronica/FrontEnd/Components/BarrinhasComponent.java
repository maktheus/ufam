package urnaeletronica.FrontEnd.Components;

import javax.swing.JPanel;

public class BarrinhasComponent {
    private static JPanel panel = new JPanel();
    
    public BarrinhasComponent(){
        
    }

    public static JPanel getPanel() {
        panel.setBackground(new java.awt.Color(17, 17, 17));
        return panel;
    }
}
