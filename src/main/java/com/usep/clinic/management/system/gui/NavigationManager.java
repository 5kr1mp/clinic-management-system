package com.usep.clinic.management.system.gui;

import java.awt.*;
import javax.swing.*;

public class NavigationManager extends CardLayout{

    private JPanel container;
    private static NavigationManager navigationManager;

    public static NavigationManager getInstance(){
        return navigationManager == null ? new NavigationManager() : navigationManager;
    }

    private NavigationManager(){}

    /**
     * Registers a panel to the navigation manager
     * @param panel the panel to be registered
     * @param name the name of the panel
     */
    public void registerPanel(JPanel panel, String name){
        addLayoutComponent(panel, name);
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
