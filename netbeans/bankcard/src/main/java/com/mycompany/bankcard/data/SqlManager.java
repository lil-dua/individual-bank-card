/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankcard.data;

import com.mycompany.bankcard.accuracy.RSA;
import static com.mycompany.bankcard.config.BytesConfig.fromHexString;
import static com.mycompany.bankcard.config.BytesConfig.toHexString;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class SqlManager {

    private String classForname = "com.mysql.jdbc.Driver";
    private String host = "jdbc:mysql://localhost:3306";
    private String dbName = "data";
    private String username = "root";
    private String password = "";

    public SqlManager() {
    }

    public void insertData(String id, PublicKey publicKey) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(host + "/" + dbName, username, password);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Data(id, public_key) VALUES (?, ?)");
            statement.setString(1, id);
            statement.setString(2, toHexString(publicKey.getEncoded()));
            statement.executeUpdate();

            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertHistory(String id, String time, String description) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(host + "/" + dbName, username, password);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO History(id, time,description) VALUES (?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, time);
            statement.setString(3, description);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public DefaultTableModel getHistory(String id) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(host + "/" + dbName, username, password);
            statement = conn.createStatement();
            String query = "SELECT * FROM `History`where id=" + id + ";;";
            resultSet = statement.executeQuery(query);
            
            Vector<String> columnNames = new Vector<>();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(resultSet.getMetaData().getColumnName(column));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (resultSet.next()) {
                Vector<Object> rowData = new Vector<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    rowData.add(resultSet.getObject(columnIndex));
                }
                data.add(rowData);
            }

            return new DefaultTableModel(data, columnNames);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public PublicKey getData(String id) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(host + "/" + dbName, username, password);

            Statement st = conn.createStatement();
            String query = "SELECT public_key FROM Data where id=" + id + ";";
            ResultSet rs = st.executeQuery(query);

            return rs.next() ? RSA.generatePublicKey(fromHexString(rs.getString(1))) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Long getNumberCard() {
        Connection conn = null;
        long id = 0L;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(host + "/" + dbName, username, password);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Data WHERE id = (SELECT MAX(id) FROM Data);";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                id = Long.parseLong(rs.getString("id"));
            }
            conn.close();
            System.out.println("id= " + id);
            return id;
        } catch (SQLException e) {
            return 1000000001L;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }
}
