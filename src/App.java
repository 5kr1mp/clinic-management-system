
import java.lang.reflect.Array;
import java.util.*;
import dao.*;
import service.*;
import model.*;
import util.*;
import menu.*;


public class App {



    Scanner scn;

    public App(){
        // init dao
        PatientRecordDao recordDao = new PatientRecordDao();
        PatientDao patientDao = new PatientDao();
        MedicineDao medDao = new MedicineDao();
        MedicineBatchDao batchDao = new MedicineBatchDao();
        LogDao logDao = new LogDao();
        IssuedMedicineDao issuedMedicineDao = new IssuedMedicineDao();

        // init services
        PatientService patientService = new PatientService(patientDao);
        PatientRecordService recordService = new PatientRecordService(recordDao);
        MedicineService medicineService = new MedicineService(medDao,batchDao);
        LogService logService = new LogService(logDao);
        IssuedMedicineService issuedMedicineService = new IssuedMedicineService(issuedMedicineDao);
        ReportService reportService = new ReportService(medicineService, recordService, patientService, issuedMedicineService);

        // init menu
        InventoryMenu inventoryMenu = new InventoryMenu(medicineService);
        LogBookMenu logBookMenu = new LogBookMenu(logService, patientService);
        PatientManagementMenu patientManagementMenu = new PatientManagementMenu(recordService, patientService, medicineService, issuedMedicineService);
        ReportMenu reportMenu = new ReportMenu(reportService);

        MainMenu mainMenu = new MainMenu(inventoryMenu, logBookMenu, patientManagementMenu, reportMenu);

        mainMenu.start();
    }

    public static void main(String[] args) {
        new App();
    }
}
