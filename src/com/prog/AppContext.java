package com.prog;

import javax.swing.ImageIcon;

import com.prog.model.User;
import com.prog.service.*;

public interface AppContext{

    public static final String APP_NAME = "Clinic Management System";
    public static final ImageIcon APP_ICON = new ImageIcon("");
    

    // user
    public static User getCurrentUser(){return getAuthService().getCurrentUser();}
    public static AuthService getAuthService() {return AuthService.getInstance();}
    public static LogService getLogService() {return LogService.getInstance();}
    public static MedicineService getMedicineService() {return MedicineService.getInstance();}
    public static PatientService getPatientService() {return PatientService.getInstance();}
    public static ReportService getReportService() {return ReportService.getInstance();}
    
}   
