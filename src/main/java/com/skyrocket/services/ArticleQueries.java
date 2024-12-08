package com.skyrocket.services;
import com.skyrocket.model.articles.Notebook;

import java.sql.PreparedStatement;
import java.util.UUID;

import static com.skyrocket.DatabaseConnector.DBConnector.connection;
import static com.skyrocket.controller.PageController.LOG;

public class ArticleQueries {
    public void insertNotebook(Notebook notebook){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO notebook(pk_shelve, name, amount, type, description, price_when_bought_unit, selling_price, manufacturer, model_number, cpu, ram, storage_drive, display_size, keyboard_layout, side_note, fk_shelve_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, notebook.getName());
            statement.setInt(3, notebook.getAmount());
            statement.setString(4, notebook.getType());
            statement.setString(5, notebook.getDescription());
            statement.setDouble(6, notebook.getPriceWhenBought());
            statement.setDouble(7, notebook.getSellingPrice());
            statement.setString(8, notebook.getBrand());
            statement.setString(9, notebook.getModelNr());
            statement.setString(10, notebook.getCpu());
            statement.setInt(11, notebook.getRam());
            statement.setInt(12, notebook.getStorage());
            statement.setDouble(13, notebook.getDisplaySize());
            statement.setString(14, notebook.getKeyboardLayout());
            statement.setString(15, notebook.getSideNote());
            statement.setString(16, notebook.getShelveIdAsForeignKey().toString());

            statement.execute();
            LOG.info("Inserted Notebook successfully");

            statement.close();
        } catch (Exception e) {
            LOG.info("Adding notebook failed, REASON: "+ e.getMessage());
        }
        }
}
