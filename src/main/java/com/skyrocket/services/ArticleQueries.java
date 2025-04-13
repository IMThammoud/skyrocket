package com.skyrocket.services;
import com.skyrocket.model.articles.Notebook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.skyrocket.DatabaseConnector.DBConnector.connection;
import static com.skyrocket.controller.PageController.LOG;

public class ArticleQueries {

    /* public ArrayList<Notebook> getArticlesFromShelve(String sessionId, String shelveId) {
        // This stores the List of Articles that are extracted from a shelve like "notebook" using the shelve_id as fk_shelve_id
        ArrayList<Notebook> notebooks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("""
                                    SELECT notebook.*
                                    FROM notebook
                                    WHERE fk_shelve_id = ?""");
            statement.setString(1, shelveId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                notebooks.add(new Notebook(UUID.fromString(resultSet.getString("pk_id")),
                        resultSet.getString("name"),
                        resultSet.getInt("amount"),
                        resultSet.getString("type"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price_when_bought"),
                        resultSet.getDouble("selling_price"),
                        UUID.fromString(resultSet.getString("fk_shelve_id")),
                        resultSet.getString("brand"),
                        resultSet.getString("model_nr"),
                        resultSet.getString("cpu"),
                        resultSet.getInt("ram"),
                        resultSet.getInt("storage_in_gb"),
                        resultSet.getInt("display_size_inches"),
                        resultSet.getString("operating_system"),
                        resultSet.getInt("battery_capacity_health"),
                        resultSet.getString("keyboard_layout"),
                        resultSet.getString("side_note")));
            }

            return notebooks;

        } catch(SQLException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

     */

    public int getArticleCountInShelveIfTypeNotebook(String shelveId) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                                    select count(*)
                                    from notebook
                                    where fk_shelve_id = ?
                                    """);
            statement.setString(1,shelveId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            // get count of articles in that shelve
            return resultSet.getInt(1);

        } catch(SQLException e) {
            LOG.error(e.getMessage());
            return 0;
        }
    }

    public void insertNotebook(Notebook notebook, String sessionId, String shelveId){
        try {

            //Get the correct shelve and check if the shelveID that comes from frontend is actually there.
            PreparedStatement theShelve = connection.prepareStatement("""
                                    select pk_shelve_id
                                    from shelve inner join user_account
                                    ON user_account.pk_id = shelve.fk_user_account_id
                                    where user_account.session_id = ? AND shelve.pk_shelve_id = ?""");
            theShelve.setString(1, sessionId);
            theShelve.setString(2, shelveId);
            ResultSet rs = theShelve.executeQuery();
            rs.next();
            LOG.info("right after executing query to search shelve id.");
            String correctShelve = rs.getString("pk_shelve_id");

            LOG.info(correctShelve.toString());

            PreparedStatement statement = connection.prepareStatement("INSERT INTO notebook(pk_id," +
                    " fk_shelve_id," +
                    " name," +
                    " amount," +
                    " type," +
                    " description," +
                    " price_when_bought," +
                    " selling_price, brand," +
                    " model_nr," +
                    " cpu," +
                    " ram," +
                    " storage_in_gb," +
                    " display_size_inches," +
                    " operating_system," +
                    " battery_capacity_health," +
                    " keyboard_layout," +
                    " side_note) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            statement.setString(1, notebook.getId().toString());
            statement.setString(2, correctShelve);
            statement.setString(3, notebook.getName());
            statement.setInt(4, notebook.getAmount());
            statement.setString(5, notebook.getType());
            statement.setString(6, notebook.getDescription());
            statement.setDouble(7, notebook.getPriceWhenBought());
            statement.setDouble(8, notebook.getSellingPrice());
            statement.setString(9, notebook.getBrand());
            statement.setString(10, notebook.getModelNr());
            statement.setString(11, notebook.getCpu());
            statement.setInt(12, notebook.getRam());
            statement.setDouble(13, notebook.getStorage());
            statement.setDouble(14, notebook.getDisplaySize());
            statement.setString(15, notebook.getOperatingSystem());
            statement.setDouble(16, notebook.getBatteryCapacityHealth());
            statement.setString(17, notebook.getKeyboardLayout());
            statement.setString(18, notebook.getSideNote());

            statement.execute();
            LOG.info("Inserted Notebook successfully");

            statement.close();
        } catch (Exception e) {
            LOG.info("Adding notebook failed, REASON: "+ e.getMessage());
        }
        }
}
