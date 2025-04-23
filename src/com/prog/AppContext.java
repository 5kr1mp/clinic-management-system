package com.prog;

import com.prog.dao.*;
import com.prog.service.*;

public class AppContext{
    public static AuthService getAuthService() {return AuthService.getInstance();}
    public static LogService getLogService() {return LogService.getInstance();}
    public static MedicineService getMedicineService() {return MedicineService.getInstance();}
    public static PatientService getPatientService() {return PatientService.getInstance();}
    public static ReportService getReportService() {return ReportService.getInstance();}
}   
