package lk.ijse.dep10.system.db;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
        private static DBConnection instance;
        private final Connection connection;

        private DBConnection(){
            try {

                File file = new File("application.properties");
                FileReader fileReader = new FileReader(file);
                Properties properties = new Properties();
                properties.load(fileReader);
                fileReader.close();

                String host = properties.getProperty("mysql.host", "localhost");
                String port = properties.getProperty("mysql.port", "3306");
                String database = properties.getProperty("mysql.database", "dep10_student_attendance");
                String username = properties.getProperty("mysql.username", "root");
                String password = properties.getProperty("mysql.password", "mysql");


                String url = "jdbc:mysql://" + host + ":" + port + "/" + database +
                        "?&allowMultiQueries=true";


                connection = DriverManager.getConnection(url,username,password);

            } catch (FileNotFoundException e) {
                new Alert(Alert.AlertType.ERROR,"Configuration file doesn't find").showAndWait();
                throw new RuntimeException(e);

            }catch (IOException e){
                new Alert(Alert.AlertType.ERROR,"Failed to read Configuration").showAndWait();
                throw new RuntimeException(e);
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR,"Failed to establish the database connection, Please contact the technical team").showAndWait();
                throw  new RuntimeException(e);
            }
        }

    public static DBConnection getInstance() {
        return (instance == null) ? instance = new DBConnection()  : instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
