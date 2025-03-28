package service;

import java.util.*;
import model.*;
import util.*;

public class ReportService {
    
    private MedicineService medicineService;
    private PatientRecordService recordService;

    public ReportService(MedicineService medicineService, PatientRecordService recordService){

        this.medicineService = medicineService;
        this.recordService = recordService;

    }

    public Report generateMonthlyReport(){
        DateRange dateRange = DateRange.ofMonth();
        ArrayList<MedicineBatch> medicines = medicineService.getMedicineBatches();
        ArrayList<PatientRecord> records = recordService.getRecords(dateRange);

        return new Report(dateRange,records,medicines);
    }

    public Report generateWeeklyReport(){
        DateRange dateRange = DateRange.ofWeek();
        ArrayList<Medicine> medicines = medicineService.getMedicines();
        ArrayList<PatientRecord> records = recordService.getRecords(dateRange);

        return new Report(dateRange,records,medicines);
    }

}
