package com.usep.clinic.management.system;

import javax.swing.*;
import java.awt.*;

import com.usep.clinic.management.system.model.User;
import com.usep.clinic.management.system.service.*;

public interface AppContext{

    public static final String APP_NAME = "Clinic Management System";
    public static final ImageIcon APP_ICON = new ImageIcon("");
    
    public static final ImageIcon BAR_ICON = new ImageIcon(
        new ImageIcon("assets/bars.png")
            .getImage()
            .getScaledInstance(30,30,Image.SCALE_SMOOTH)
    );
    
    public static final ImageIcon LOGOUT_ICON = new ImageIcon(
        new ImageIcon("assets/logout.png")
            .getImage()
            .getScaledInstance(25, 25, Image.SCALE_SMOOTH)
    );
    
    public static final ImageIcon SEARCH_ICON = new ImageIcon(
       new ImageIcon("assets/search.png")
            .getImage()
            .getScaledInstance(20,20, Image.SCALE_SMOOTH)
    );

    // user
    public static User getCurrentUser(){return getAuthService().getCurrentUser();}
    public static AuthService getAuthService() {return AuthService.getInstance();}
    public static LogService getLogService() {return LogService.getInstance();}
    public static MedicineService getMedicineService() {return MedicineService.getInstance();}
    public static PatientService getPatientService() {return PatientService.getInstance();}
    public static ReportService getReportService() {return ReportService.getInstance();}
    
}   
