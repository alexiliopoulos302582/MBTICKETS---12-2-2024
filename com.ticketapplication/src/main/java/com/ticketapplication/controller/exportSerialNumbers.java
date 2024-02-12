package com.ticketapplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class exportSerialNumbers {

    @Autowired
    myApplicationBean myApplicationBean;


    public void exportSerialNumbers_txt() {
        LocalDate currentDate = LocalDate.now();

        String query = "SELECT " +
                "'id', 'branch','comments', 'deviceModel', 'serialNumber' " +
                "UNION " +
                "SELECT * FROM ticketingdatabase.device ";


        try (Connection connection = DriverManager.getConnection(
                myApplicationBean.getAppURL(),
                myApplicationBean.getAppUser(),
                myApplicationBean.getAppPassword());

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            List<String> resultData = new ArrayList<>();

            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(resultSet.getString(i));
                    if (i <= columnCount) {
                        row.append(';');
                    }
                }
                resultData.add(row.toString());
            }
            Path filepath = Paths.get(myApplicationBean.getSavePath(), "serialnumberexport_" + currentDate + ".txt");
            Files.write(filepath, resultData);

            Path csvfilepath = Paths.get(myApplicationBean.getSavePath(), "serialnumberexport_" + currentDate + ".csv");
            Files.write(csvfilepath, resultData);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
