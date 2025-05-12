package com.prog.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.prog.*;
import com.prog.model.enums.Role;

import javax.swing.*;
import com.formdev.flatlaf.*;

public class Window extends JFrame implements ActionListener{

    final JButton patientMgmt, inventory, 

    public Window(){

        style();

        setTitle(AppContext.APP_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,400);
        setLayout(new BorderLayout());

        setVisible(true);

        
    }

    public void style(){
        FlatLightLaf.setup();
    }

    public void initSidebar(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        switch (AppContext.getCurrentUser().getRole()) {
            case Role.ADMIN:{
                

                break;
            }
            case Role.DOCTOR:{

                
                break;
            }
            case Role.PATIENT:{

                break;
            }
            case Role.NURSE:{

                break;
            }
        }
    }

}
