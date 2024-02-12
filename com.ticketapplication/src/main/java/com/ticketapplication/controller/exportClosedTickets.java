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
public class exportClosedTickets {

    @Autowired
    myApplicationBean myApplicationBean;

    public void exportClosedTickets_txt() {
        LocalDate currentDate = LocalDate.now();

        String query =  "SELECT " +
                "'id', 'address','AFM', 'assigned_to', 'category', 'city', 'client_name', 'creation_date'," +
                "'customer_id', 'device_model', 'email', " +
                "'issue', 'phone_number', 'serial_number', 'solution', 'solution_type', 'subject', " +
                "'ticket_state' " +
                "UNION " +
                "SELECT * FROM ticketingdatabase.ticket WHERE ticket_state = 0";

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
            Path filepath = Paths.get(myApplicationBean.getSavePath(), "closedexport_" + currentDate + ".txt");
            Files.write(filepath, resultData);

            Path csvfilepath = Paths.get(myApplicationBean.getSavePath(), "closedexport_" + currentDate + ".csv");
            Files.write(csvfilepath, resultData);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
