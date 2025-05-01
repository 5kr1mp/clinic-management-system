package com.prog.gui;

import java.util.*;
import javax.swing.*;

public class Navigate {
    
    private List<JPanel> pages = new ArrayList<>();
    private List<String> pageNames = new ArrayList<>();

    public void addPage(String name, JPanel page) throws Exception{

        if (pageNames.contains(name.trim())) throw new NavigationException("Duplicate");
        if (pages.contains(page));

        pages.add(page);
        pageNames.add(name.trim());

    }
    

}
