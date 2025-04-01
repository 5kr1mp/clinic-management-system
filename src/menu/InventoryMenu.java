package menu;

import service.MedicineService;
import model.MedicineBatch;
import model.Medicine;
import util.DateTimeFormat; // sa add batch
import util.Sorter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class InventoryMenu extends Menu{
    private MedicineService medicineService;

    public InventoryMenu(MedicineService medicineService){
        this.medicineService = medicineService;
    }

    public void displayInventoryMenu(){
        System.out.println("\n================== INVENTORY MENU ==================");
        System.out.println("[1] Add Medicine");
        System.out.println("[2] Add Batch");
        System.out.println("[3] View Expired Batches");
        System.out.println("[4] Filter Expired Batches");
        System.out.println("[5] Decrease Stock");
        System.out.println("[6] Exit");
    }

    public void viewMedicines(){
        System.out.println("================== MEDICINE LIST ==================");
        System.out.printf("%-20s | %-20s | %-15%s%n",
                "Medicine ID", "Name", "Manufacturer");
        System.out.println("===================================================");

        for(Medicine medicine : medicineService.getMedicines()){
            int medicineId  = medicine.getId();
            String medicineName = medicine.getName();
            String medicineManufacturer = medicine.getManufacturer();

            System.out.printf("%-20s | %-20s | %-15%s%n",
                    medicineId,medicineName, medicineManufacturer);
        }

            System.out.println("===================================================");

    }

    public void viewBatches(){
        System.out.println("================== MEDICINE BATCH LIST ==================");
        System.out.printf("%-20s | %-20s | %-10%s | %-15s | %-15s%n",
                "Batch ID", "Medicine ID", "Quantity", "Expiry Date", "Stock Date");
        System.out.println("=========================================================");

        for(MedicineBatch batch : Sorter.sortBatchByExpiryDate(medicineService.getMedicineBatches(),false)){
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

            System.out.printf("%-20s | %-20s | %-10%s | %-15s | %-15s%n",
                    batchId, medicineName, batchStock, batchQuantity, expiryDate, stockedDate);

        }

        System.out.println("=========================================================");

    }

    public void addMedicineMenu(){
        while(true){
            try {
                viewMedicines();
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
                viewBatches();
                System.out.println();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                int batchId = medicineService.generateBatchId();

                System.out.print("\nEnter Medicine Name: ");
                String medicineName = scn.nextLine().trim();
                int medicineId = medicineService.getMedicineByName(medicineName).getId();
                System.out.print("Enter stock: ");
                int medicineBatchStock = scn.nextInt();
                System.out.print("Enter quantity: ");
                int medicineBatchQuantity = scn.nextInt();
                System.out.print("Enter expiry date: ");
                String expiryDate =  scn.nextLine();
                System.out.print("Enter stock date: ");
                String stockDate = scn.nextLine();

                LocalDate medicineBatchExpiryDate = LocalDate.parse(expiryDate, dateFormatter);
                LocalDate medicineBatchStockDate = LocalDate.parse(stockDate, dateFormatter);


                MedicineBatch medicineBatch = new MedicineBatch(
                        batchId, medicineId,
                        medicineBatchStock, medicineBatchQuantity, medicineBatchExpiryDate, medicineBatchStockDate);
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

    public void viewExpiredBatches(){
        while (true){
            try{
                medicineService.getExpiredMedicineBatches();
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
                medicineService.getNonExpiredMedicineBatches();
                return;
            } catch (Exception e) {
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

    public void displayMenu() {
        displayInventoryMenu();
        while (true) {
            System.out.print("Enter choice: ");
            String choice = scn.nextLine();
            scn.next();

            switch (choice){
                case "1" : addMedicineMenu(); break;
                case "2" : addBatchMenu(); break;
                case "3" : viewExpiredBatches(); break;
                case "4" : showNonExpiredMedicines(); break;
                case "5" : decreaseStock(); break;
                case "6" : exit(); return;
                default: invalidOptionError(6); break;
            }
        }
    }

}

