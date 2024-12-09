package org.gwajae.accountbook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalendarService {
    private final String URL = "jdbc:mysql://localhost:3306/account_db";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "root";
    private final String PASS = "1234";

    public void create(Calendar calendar) {
        String sql = "INSERT INTO calendar (" +
                "calendar_id, user_id, type," +
                "category, amount, date, description )" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, calendar.getCalendarId());
            stmt.setString(2, calendar.getUserId());
            stmt.setString(3, calendar.getType());
            stmt.setString(4, calendar.getCategory());
            stmt.setDouble(5, calendar.getAmount());
            stmt.setString(6, calendar.getDate());
            stmt.setString(7, calendar.getDescription());

            int result = stmt.executeUpdate();

            System.out.println(result);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Calendar calendar) {
        String sql = "DELETE FROM calendar WHERE calendar_id='" + calendar.getCalendarId() + "'";

        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(sql);

            int result = stmt.executeUpdate();

            System.out.println(result);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Calendar calendar) {
        String sql = "UPDATE calendar SET user_id = ?, type = ?, category = ?, amount = ?, date = ?, description = ? WHERE calendar_id = ?";

        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(7, calendar.getCalendarId());
            stmt.setString(1, calendar.getUserId());
            stmt.setString(2, calendar.getType());
            stmt.setString(3, calendar.getCategory());
            stmt.setDouble(4, calendar.getAmount());
            stmt.setString(5, calendar.getDate());
            stmt.setString(6, calendar.getDescription());

            int result = stmt.executeUpdate();

            System.out.println(result);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Calendar> read() {
        List<Calendar> calendars = new ArrayList<>();
        String sql = "select * from calendar";

        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String cid = rs.getString("calendar_id");
                String uid = rs.getString("user_id");
                String type = rs.getString("type");
                String category = rs.getString("category");
                int amount = rs.getInt("amount");
                Date date = rs.getDate("date");
                String description = rs.getString("description");

                Calendar calendar = new Calendar(cid, uid, type, category, amount, date, description);

                calendars.add(calendar);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return calendars;
    }

    public List<Calendar> sortread(boolean order, String sorttype) {
        String sqlend = "order by ";

        sqlend += sorttype;

        if (order) {
            sqlend = sqlend + " ASC";
        } else {
            sqlend = sqlend +  " DESC";
        }

        String sqlstart = "select * from calendar " + sqlend;

        List<Calendar> calendars = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sqlstart);

            while (rs.next()) {
                String cid = rs.getString("calendar_id");
                String uid = rs.getString("user_id");
                String type = rs.getString("type");
                String category = rs.getString("category");
                int amount = rs.getInt("amount");
                Date date = rs.getDate("date");
                String description = rs.getString("description");

                Calendar calendar = new Calendar(cid, uid, type, category, amount, date, description);

                calendars.add(calendar);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return calendars;
    }
}
