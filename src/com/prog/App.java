package com.prog;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.prog.dao.*;
import com.prog.gui.Window;
import com.prog.service.*;


public class App {

    public App(){
        // init dao
        PatientRecordDao recordDao = new PatientRecordDao();
        PatientDao patientDao = new PatientDao();
        MedicineDao medDao = new MedicineDao();
        MedicineBatchDao batchDao = new MedicineBatchDao();
        LogDao logDao = new LogDao();
        IssuedMedicineDao issuedMedicineDao = new IssuedMedicineDao();

        // init services
        PatientService patientService = new PatientService(patientDao,recordDao);
        MedicineService medicineService = new MedicineService(medDao,batchDao,issuedMedicineDao);
        LogService logService = new LogService(logDao);
        ReportService reportService = new ReportService(medicineService,patientService);

        new Window();
    }

    public static void main(String[] args) {
        new App();

        for (LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()){
            System.out.println(lafInfo.getName());
            System.out.println(lafInfo.getClassName());
            System.out.println();
        }

    }
}
