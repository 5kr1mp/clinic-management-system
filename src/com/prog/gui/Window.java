package com.prog.gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class Window extends JFrame{

    private Sidebar sidebar;

    public Window(){

        style();

        setTitle(AppConstants.APP_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,400);

        JPasswordField tf = new JPasswordField();
        tf.setColumns(10);

        String.valueOf(tf.getPassword());
        tf.

        sidebar = new Sidebar();
        add(sidebar);
        add(tf);
        setVisible(true);
    }

    public void style(){
        FlatLightLaf.setup();
    }

}
