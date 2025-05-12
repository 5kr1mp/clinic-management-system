package com.prog.gui;

import java.util.List;
import javax.swing.*;
import com.prog.model.enums.Role;
import java.awt.*;

public class Sidebar extends JPanel{
    
    List<JButton> sideBarItems = List.of(
        new JButton("Patient Management"),
        new JButton("Inventory"),
        new JButton("Reports"),
        new JButton("Logs")
    );

    public Sidebar(Role role){

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(200,500);
        for (JButton jButton : sideBarItems) {

            jButton.setMaximumSize(new Dimension(200,50));
            add(jButton);
        }

    }

    

}
