package DAO;

import java.sql.*;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static LinkedBlockingDeque<Connection> connectionPool;

    public static void createConnectionPool(int size){

        Connection con;

        connectionPool = new LinkedBlockingDeque<>(size);

        for (int i = 0; i < size; i++) {

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/litenms", "root", "password");

                connectionPool.put(con);

            } catch (SQLException | ClassNotFoundException | InterruptedException e) {

                e.printStackTrace();

                System.exit(1);

            }

        }
    }

    public static Connection getConnection(){

        try {

            return connectionPool.take();

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        }

    }

    public static void putConnection(Connection c){

        try {

            connectionPool.put(c);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        }

    }
}
