package com.prog.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.prog.*;
import javax.swing.*;
import com.formdev.flatlaf.*;

public class Window extends JFrame{

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

}
