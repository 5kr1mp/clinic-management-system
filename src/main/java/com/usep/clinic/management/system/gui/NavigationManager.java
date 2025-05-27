/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usep.clinic.management.system.gui;

import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JPanel;

/**
 *
 * @author Skrimp
 */
public class NavigationManager extends CardLayout {

    JPanel container;
    

    public NavigationManager(JPanel container) {
        super();
        this.container = container;
    }
    
    public NavigationManager(JPanel container, int hgap, int vgap){
        super(hgap, vgap);
        this.container = container;
    }
    
    
    
    
    
}
