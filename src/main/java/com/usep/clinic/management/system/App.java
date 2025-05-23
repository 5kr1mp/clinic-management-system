package com.usep.clinic.management.system;

import com.usep.clinic.management.system.gui.*;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        
        new AppConfig();

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new LoginWindow();
            }
            
        });
    }
}
