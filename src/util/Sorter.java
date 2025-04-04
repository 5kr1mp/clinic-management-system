package util;
import model.*;
import java.util.ArrayList;

public class Sorter {

    public static ArrayList<Log> sortLogsByDate(ArrayList<Log> logs, boolean ascending) {
        
        ArrayList<Log> sortedLogs = new ArrayList<>(logs);
        
        int n = sortedLogs.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if ((ascending && sortedLogs.get(j).getDate().isBefore(sortedLogs.get(minIdx).getDate())) ||
                    (!ascending && sortedLogs.get(j).getDate().isAfter(sortedLogs.get(minIdx).getDate()))) {
                    minIdx = j;
                }
            }
            Log temp = sortedLogs.get(i);
            sortedLogs.set(i, sortedLogs.get(minIdx));
            sortedLogs.set(minIdx, temp);
        }
        
        return sortedLogs;
    }
    

    public static ArrayList<Medicine> sortMedicinesByName(ArrayList<Medicine> medicines, boolean ascending) {
        
        ArrayList<Medicine> sortedMedicinesByName = new ArrayList<>(medicines);

        int n = sortedMedicinesByName.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if ((ascending && sortedMedicinesByName.get(j).getName().compareTo(sortedMedicinesByName.get(minIdx).getName()) < 0) ||
                    (!ascending && sortedMedicinesByName.get(j).getName().compareTo(sortedMedicinesByName.get(minIdx).getName()) > 0)) {
                    minIdx = j;
                }
            }
            Medicine temp = sortedMedicinesByName.get(i);
            sortedMedicinesByName.set(i, sortedMedicinesByName.get(minIdx));
            sortedMedicinesByName.set(minIdx, temp);
        }

        return sortedMedicinesByName;
    }

    // public static ArrayList<Medicine> sortMedicinesByStock(ArrayList<Medicine> medicines, boolean ascending) {
        
    //     ArrayList<Medicine> sortedMedicinesByStock = new ArrayList<>(medicines);
        
    //     int n = sortedMedicinesByStock.size();

    //     for (int i = 0; i < n - 1; i++) {
    //         int minIdx = i;
    //         for (int j = i + 1; j < n; j++) {
    //             if ((ascending && sortedMedicinesByStock.get(j).getStock() < sortedMedicinesByStock.get(minIdx).getStock()) ||
    //                 (!ascending && sortedMedicinesByStock.get(j).getStock() > sortedMedicinesByStock.get(minIdx).getStock())) {
    //                 minIdx = j;
    //             }
    //         }
    //         Medicine temp = sortedMedicinesByStock.get(i);
    //         sortedMedicinesByStock.set(i, sortedMedicinesByStock.get(minIdx));
    //         sortedMedicinesByStock.set(minIdx, temp);
    //     }

    //     return sortedMedicinesByStock;

    // }

    public static ArrayList<Patient> sortPatientsByLastName(ArrayList<Patient> patients, boolean ascending) {
        
        ArrayList<Patient> sortedPatientsByName = new ArrayList<>(patients);
        
        int n = sortedPatientsByName.size();

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if ((ascending && sortedPatientsByName.get(j).getLastname().compareTo(sortedPatientsByName.get(minIdx).getLastname()) < 0) ||
                    (!ascending && sortedPatientsByName.get(j).getLastname().compareTo(sortedPatientsByName.get(minIdx).getLastname()) > 0)) {
                    minIdx = j;
                }
            }
            Patient temp = sortedPatientsByName.get(i);
            sortedPatientsByName.set(i, sortedPatientsByName.get(minIdx));
            sortedPatientsByName.set(minIdx, temp);
        }

        return sortedPatientsByName;
    }

    public static ArrayList<Patient> sortPatientsById(ArrayList<Patient> patients, boolean ascending) {
        
        ArrayList<Patient> sortedPatientsById = new ArrayList<>(patients);
        
        int n = sortedPatientsById.size();

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if ((ascending && sortedPatientsById.get(j).getId() < sortedPatientsById.get(minIdx).getId()) ||
                    (!ascending && sortedPatientsById.get(j).getId() > sortedPatientsById.get(minIdx).getId())) {
                    minIdx = j;
                }
            }
            Patient temp = sortedPatientsById.get(i);
            sortedPatientsById.set(i, sortedPatientsById.get(minIdx));
            sortedPatientsById.set(minIdx, temp);
        }

        return sortedPatientsById;
    }

    public static ArrayList<PatientRecord> sortPatientRecordsByDate(ArrayList<PatientRecord> records, boolean ascending) {

        ArrayList<PatientRecord> sortedPatientRecordsByDate = new ArrayList<>(records);

        int n = sortedPatientRecordsByDate.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if ((ascending && sortedPatientRecordsByDate.get(j).getDate().isBefore(sortedPatientRecordsByDate.get(minIdx).getDate())) ||
                    (!ascending && sortedPatientRecordsByDate.get(j).getDate().isAfter(sortedPatientRecordsByDate.get(minIdx).getDate()))) {
                    minIdx = j;
                }
            }
            PatientRecord temp = sortedPatientRecordsByDate.get(i);
            sortedPatientRecordsByDate.set(i, sortedPatientRecordsByDate.get(minIdx));
            sortedPatientRecordsByDate.set(minIdx, temp);
        }

        return sortedPatientRecordsByDate;
    }

    public static ArrayList<MedicineBatch> sortBatchByExpiryDate(ArrayList<MedicineBatch> batches, boolean ascending){

        ArrayList<MedicineBatch> sortedBatches = new ArrayList<>(batches);

        int n = sortedBatches.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if ((ascending && sortedBatches.get(j).getExpiryDate().isBefore(sortedBatches.get(minIdx).getExpiryDate())) ||
                    (!ascending && sortedBatches.get(j).getExpiryDate().isAfter(sortedBatches.get(minIdx).getExpiryDate()))) {
                    minIdx = j;
                }
            }
            MedicineBatch temp = sortedBatches.get(i);
            sortedBatches.set(i, sortedBatches.get(minIdx));
            sortedBatches.set(minIdx, temp);
        }

        return sortedBatches;
    }
}
