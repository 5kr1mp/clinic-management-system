package com.usep.clinic.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.usep.clinic.management.system.model.IssuedMedicine;
import com.usep.clinic.management.system.util.DatabaseConnection;

public class IssuedMedicineDao {


    public void add(IssuedMedicine issuedMedicine){
        Connection conn = null;
        PreparedStatement statement = null;

        try{
            // Step 1: connect to db
            conn = DatabaseConnection.getConnection();

            // Step 2: prepare query statement
            String query = "INSERT INTO issued_medicines (record_id,medicine_id,amount) VALUES(?,?,?)";
            statement = conn.prepareStatement(query);

            // Step 3: i edit ang query (pag base sa columns sa table para makabalo unsay i set)
            statement.setInt(1, issuedMedicine.getRecordId());
            statement.setInt(2, issuedMedicine.getMedicineId());
            statement.setInt(3, issuedMedicine.getAmount());

            // Step 4: Run executeUpdate() na method (executeUpdate() gamit since wala tay data gi retrieve. If naa to, executeQuery ang gamiton).
            statement.executeUpdate();


        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            // Step 5: close connections
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }
    }

    public ArrayList<IssuedMedicine> getAll(){
        // Step 1: initialize arraylist
        ArrayList<IssuedMedicine> issuedMedicines = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            // Step 2: connect to db
            conn = DatabaseConnection.getConnection();

            // Step 3: prepare query statement
            String query = "SELECT * FROM issued_medicines";
            statement = conn.prepareStatement(query);

            // Step 4: execute query (need nato ug data so executeQuery() na method gamiton)
            rs = statement.executeQuery();

            // Step 5: i loop ang result set
            while (rs.next()){
                // Step 6: add new IssuedMedicine sa katong arraylist 

                issuedMedicines.add(
                    new IssuedMedicine(
                        rs.getInt("id"),
                        rs.getInt("record_id"),
                        rs.getInt("medicine_id"),
                        rs.getInt("amount")    
                    )
                );
            }
        }
        catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        }
        finally {

            // Step 7: close connections
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }

        // Step 8: return arraylist
        return issuedMedicines;
    }

    public IssuedMedicine get(int id){
        // Step 1: initialize issuedMedicine nga null
        IssuedMedicine issuedMedicine = null;

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            // Step 2: Connect to db
            conn = DatabaseConnection.getConnection();

            // Step 3: Prepare query
            String query = "SELECT * FROM issued_medicines WHERE id = ?";
            statement = conn.prepareStatement(query);

            // Step 4: Configure query
            statement.setInt(1, id);

            // Step 5: executeQuery()
            rs = statement.executeQuery();

            // Step 6: get the values from result set and create an object (I run jud una ang rs.next() 
            // since mao nay mu move sa "imaginary cursor" from row 0 to row 1 sa table na result. Ang kaning
            // "imaginary cursor" ang magdetermine which row ta magkuha ug data
            // )
            if (rs.next()){
                issuedMedicine = new IssuedMedicine(
                    rs.getInt("id"),
                    rs.getInt("record_id"),
                    rs.getInt("medicine_id"),
                    rs.getInt("amount")
                );
            }
        }        
        catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        }
        finally {

            // Step 7: close connections
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }

        // Step 8: return object
        return issuedMedicine;
    }

    public ArrayList<IssuedMedicine> getByMedicineId(int medicineId){
        // Step 1: initialize arraylist
        ArrayList<IssuedMedicine> issuedMedicines = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            // Step 2: connect to db
            conn = DatabaseConnection.getConnection();

            // Step 3: prepare query statement
            String query = "SELECT * FROM issued_medicines WHERE medicine_id = ?";
            statement = conn.prepareStatement(query);

            // Step 4: configure query
            statement.setInt(1, medicineId);

            // Step 5: execute query (need nato ug data so executeQuery() na method gamiton)
            rs = statement.executeQuery();

            // Step 6: i loop ang result set
            while (rs.next()){
                // Step 7: add new IssuedMedicine sa katong arraylist 

                issuedMedicines.add(
                    new IssuedMedicine(
                        rs.getInt("id"),
                        rs.getInt("record_id"),
                        rs.getInt("medicine_id"),
                        rs.getInt("amount")    
                    )
                );
            }
        }
        catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        }
        finally {

            // Step 8: close connections
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }

        // Step 9: return arraylist
        return issuedMedicines;
    }

    public ArrayList<IssuedMedicine> getByRecordId(int recordId){
        // Step 1: initialize arraylist
        ArrayList<IssuedMedicine> issuedMedicines = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            // Step 2: connect to db
            conn = DatabaseConnection.getConnection();

            // Step 3: prepare query statement
            String query = "SELECT * FROM issued_medicines WHERE record_id = ?";
            statement = conn.prepareStatement(query);

            // Step 4: configure query
            statement.setInt(1, recordId);

            // Step 5: execute query (need nato ug data so executeQuery() na method gamiton)
            rs = statement.executeQuery();

            // Step 6: i loop ang result set
            while (rs.next()){
                // Step 7: add new IssuedMedicine sa katong arraylist 

                issuedMedicines.add(
                    new IssuedMedicine(
                        rs.getInt("id"),
                        rs.getInt("record_id"),
                        rs.getInt("medicine_id"),
                        rs.getInt("amount")    
                    )
                );
            }
        }
        catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        }
        finally {

            // Step 8: close connections
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }

        // Step 9: return arraylist
        return issuedMedicines;
    }

    public void update(IssuedMedicine issuedMedicine){
        Connection conn = null;
        PreparedStatement statement = null;

        try{
            // Step 1: connect to db
            conn = DatabaseConnection.getConnection();

            // Step 2: prepare query statement
            String query = "UPDATE issued_medicine SET amount = ?, medicine_id = ? WHERE id = ?";
            statement = conn.prepareStatement(query);

            // Step 3: i edit ang query (pag base sa columns sa table para makabalo unsay i set)
            statement.setInt(1, issuedMedicine.getId());
            statement.setInt(2, issuedMedicine.getMedicineId());
            statement.setInt(3, issuedMedicine.getAmount());

            // Step 4: Run executeUpdate() na method (executeUpdate() gamit since wala tay data gi retrieve. If naa to, executeQuery ang gamiton).
            statement.executeUpdate();

        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            // Step 5: close connections
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }
    }

    public void delete(int id){
        Connection conn = null;
        PreparedStatement statement = null;

        try{
            // Step 1: connect to db
            conn = DatabaseConnection.getConnection();

            // Step 2: prepare query statement
            String query = "DELETE FROM issued_medicines WHERE id = ?";
            statement = conn.prepareStatement(query);

            // Step 3: i edit ang query (pag base sa columns sa table para makabalo unsay i set)
            statement.setInt(1, id);

            // Step 4: Run executeUpdate() na method (executeUpdate() gamit since wala tay data gi retrieve. If naa to, executeQuery ang gamiton).
            statement.executeUpdate();

        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            // Step 5: close connections
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }
    }
}
