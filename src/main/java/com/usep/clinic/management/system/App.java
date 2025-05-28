package com.usep.clinic.management.system;

import com.formdev.flatlaf.FlatLightLaf;
import com.usep.clinic.management.system.gui.*;
import com.usep.clinic.management.system.service.AuthService;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        
        new AppConfig();

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                FlatLightLaf.setup();
                try{
                    AuthService.getInstance().attemptLogin("admin", "admin123");
                } catch (Exception e){}
                new MainWindow();
            }
            
        });
    }
}
