package menu;

import service.MedicineService;
import model.MedicineBatch;
import model.Medicine;
import util.DateTimeFormat; // sa add batch

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class InventoryMenu extends Menu{
    private MedicineService medicineService;

    public InventoryMenu(MedicineService medicineService){
        this.medicineService = medicineService;
    }

    public void viewMedicines(){

        ArrayList<Medicine> medicines = medicineService.getMedicines();

        if (medicines.isEmpty()){
            System.out.println("No medicines found");
            enterToContinue();
        }

        System.out.println("========================= MEDICINE LIST =========================");
        System.out.printf("%-20s | %-20s | %-15s\n",
                "Medicine ID", "Name", "Manufacturer");
        System.out.println("=================================================================");

        for(Medicine medicine : medicines){
            int medicineId  = medicine.getId();
            String medicineName = medicine.getName();
            String medicineManufacturer = medicine.getManufacturer();

            System.out.printf("%-20s | %-20s | %-15s\n",
                    medicineId,medicineName, medicineManufacturer);
        }

            System.out.println("=================================================================");

    }

    private void viewBatches(ArrayList<MedicineBatch> batches){

        if (batches.isEmpty()){
            System.out.println("No batches found");
            enterToContinue();
        }

        System.out.println("========================================= MEDICINE BATCH LIST =========================================");
        System.out.printf("%-20s | %-20s | %-10s | %-10s| %-15s | %-15s\n",
                "Batch ID", "Medicine", "Stock", "Quantity","Expiry Date", "Stock Date");
        System.out.println("=======================================================================================================");

        for(MedicineBatch batch : batches){
            int batchId = batch.getId();
            String medicineName = null;
            try {
                medicineName = medicineService.getMedicine(batch.getMedicineId()).getName();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
                System.out.println("Enter 1 to return to menu, or any other key to continue.");
                String input = scn.nextLine();
                if(input.trim().equals("1")) return;
            }
            int batchStock = batch.getStock();
            int batchQuantity = batch.getQuantity();
            String expiryDate = DateTimeFormat.formatDate(batch.getExpiryDate());
            String stockedDate = DateTimeFormat.formatDate(batch.getStockedDate());

            System.out.printf("%-20s | %-20s | %-10s | %-10s| %-15s | %-15s\n",
                    batchId, medicineName, batchStock, batchQuantity, expiryDate, stockedDate);

        }

        System.out.println("=======================================================================================================");

    }

    public void viewInventory(){
        try{
            ArrayList<MedicineBatch> batches = medicineService.getMedicineBatches();
            viewBatches(batches);
        }
        catch (Exception e){

        }
    }

    public void viewExpiredBatches(){
        while (true){
            try{
                viewBatches(medicineService.getExpiredMedicineBatches());
                return;
            } catch (Exception e) {
                System.out.println("Enter 1 to return to menu, or any other key to continue.");
                String input = scn.nextLine();
                if(input.trim().equals("1")) return;
            }
        }
    }

    public void showNonExpiredMedicines(){
        while (true){
            try{
                viewBatches(medicineService.getNonExpiredMedicineBatches());
                return;
            } catch (Exception e) {
                System.out.println("Enter 1 to return to menu, or any other key to continue.");
                String input = scn.nextLine();
                if(input.trim().equals("1")) return;
            }
        }
    }


    public void addMedicineMenu(){
        while(true){
            try {
                int medicineId = medicineService.generateMedicineId();
                System.out.print("\nEnter name: ");
                String medicineName = scn.nextLine().trim();
                System.out.print("Enter manufacturer: ");
                String medicineManufacturer = scn.nextLine().trim();
                Medicine medicine = new Medicine(medicineId, medicineName, medicineManufacturer);
                medicineService.addMedicine(medicine);
                System.out.println("Medicine " + medicineName + medicineId + " added successfully!");

                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
                System.out.println("Enter 1 to return to menu, or any other key to continue.");
                String input = scn.nextLine();
                if(input.trim().equals("1")) return;
            }
        }
    }

    public void addBatchMenu() {
        while(true){
            try {
                viewMedicines();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                int batchId = medicineService.generateBatchId();

                System.out.print("\nEnter Medicine Name: ");
                String medicineName = scn.nextLine().trim();
                Medicine med = medicineService.getMedicineByName(medicineName);
                if (med == null){
                    System.out.println(medicineName + " medicine does not exist");
                    System.out.print("[Enter 1 to retry. Otherwise enter any key]");
                    String input = scn.nextLine();
                    if(input.trim().equals("1")) continue;
                    return;
                }
                int medicineId = med.getId();

                System.out.print("Enter quantity: ");
                int medicineBatchQuantity = inputNumber();
                System.out.print("Enter expiry date (MM-DD-YYYY): ");
                String expiryDate = scn.nextLine();
                
                LocalDate medicineBatchExpiryDate = LocalDate.parse(expiryDate, dateFormatter);
                LocalDate medicineBatchStockDate = LocalDate.now();

                if (medicineBatchExpiryDate.isBefore(medicineBatchStockDate)){
                    System.out.println("Invalid expiry date");
                    System.out.print("[Enter 1 to retry. Otherwise enter any key]");
                    String input = scn.nextLine();
                    if(input.trim().equals("1")) continue;
                    return;
                }

                MedicineBatch medicineBatch = new MedicineBatch(
                        batchId, medicineId,
                        medicineBatchQuantity, medicineBatchQuantity, medicineBatchExpiryDate, medicineBatchStockDate);
                medicineService.addBatch(medicineBatch);

                System.out.println("Medicine batch " + medicineName + " (" + batchId + ")" + " added successfully!");

                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
                System.out.println("Enter 1 to return to menu, or any other key to continue.");
                String input = scn.nextLine();
                if(input.trim().equals("1")) return;
            }
        }
    }


    public void decreaseStock(){
        while (true){
            try{
                System.out.print("Enter batch ID: ");
                int medicineBatchID = scn.nextInt();
                System.out.print("Enter amount: ");
                int medicineBatchAmount = scn.nextInt();
                medicineService.decreaseStock(medicineBatchID, medicineBatchAmount);
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
                System.out.println("Enter 1 to return to menu, or any other key to continue.");
                String input = scn.nextLine();
                if(input.trim().equals("1")) return;
            }
        }
    }


    public void exit(){
        System.out.println("Exiting the program....");
    }

    public void displayInventoryMenu(){
        System.out.println("\n================== INVENTORY MENU ==================");
        System.out.println("[1] View Medicines");
        System.out.println("[2] View Inventory");
        System.out.println("[3] View Expired Batches");
        System.out.println("[4] View Non-Expired Batches");
        System.out.println("[5] Add New Medicine");
        System.out.println("[6] Stock New Batch");
        System.out.println("[7] Exit");
    }

    public void displayMenu() {
        while (true) {
            displayInventoryMenu();
            System.out.print("Enter choice: ");
            String choice = scn.nextLine().trim();

            switch (choice){
                case "1" : viewMedicines(); break;
                case "2" : viewInventory(); break;
                case "3" : viewExpiredBatches(); break;
                case "4" : showNonExpiredMedicines(); break;
                case "5" : addMedicineMenu(); break;
                case "6" : addBatchMenu(); break;
                case "7" : exit(); return;
                default: invalidOptionError(6); break;
            }
        }
    }

}

