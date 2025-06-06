package com.usep.clinic.management.system.gui;

import java.awt.*;
import javax.swing.*;

public class NavigationManager extends CardLayout{

    private JPanel container;
    private static NavigationManager instance;

    public static NavigationManager getInstance(){
        
        if (instance == null){
            instance = new NavigationManager();
        }

        return instance;
    }

    private NavigationManager(){}

    /**
     * Registers a panel to the navigation manager
     * @param panel the panel to be registered
     * @param name the name of the panel
     */
    public void registerPanel(JPanel panel, String name){
        try {
            container.add(panel);
            addLayoutComponent(panel, name);
        } catch (NullPointerException ex){
            throw new NullPointerException("Container is null. Make sure to call setContainer() to set the container.");
        }
    }

    /**
     * Shows the panel of name to the container.
     * 
     * @param name name of the panel to show.
     */
    public void show(String name){
        super.show(container, name);
    }

    public void setContainer(JPanel container){
        this.container = container;
    }

    public JPanel getContainer(){
        return container;
    }

}
