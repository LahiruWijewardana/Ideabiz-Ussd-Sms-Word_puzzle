package wordpuzzle.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import wordpuzzle.util.Utility;

public class DatabaseConnect {

	Connection con;

    public Connection dbConnect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Utility.DB_URL, Utility.DB_USERNAME, Utility.DB_PASSWORD);

            System.out.println("Database connected!");

            return con;

        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
