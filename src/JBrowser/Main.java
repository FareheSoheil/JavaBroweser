/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JBrowser;

/**
 *
 * @author Erfan
 */
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;


public class Main {

    public static void main(String args[]) {
        
        //JFrame jbrowser = new JFrame
        try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
    } catch (Exception e) {
    // If Nimbus is not available, you can set the GUI to another look and feel.
}
        JBrowserFrame jbrowserframe = new JBrowserFrame();
        jbrowserframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jbrowserframe.pack();
        jbrowserframe.setVisible(true);
    }
    
    
    
}
