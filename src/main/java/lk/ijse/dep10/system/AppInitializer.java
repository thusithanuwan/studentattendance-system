package lk.ijse.dep10.system;

import javafx.application.Application;
import javafx.stage.Stage;
import lk.ijse.dep10.system.db.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppInitializer extends Application {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook( new Thread(() -> {        // This Hook should be at before the launch() method
            try {
                if (DBConnection.getInstance().getConnection() != null &&
                        !DBConnection.getInstance().getConnection().isClosed()){
                    System.out.println("Data base connection is about to close");
                    DBConnection.getInstance().getConnection().close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {



        generateSchemaIfNotExist();

    }
    private void generateSchemaIfNotExist(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("show tables ");

            HashSet<String> tableNameSet = new HashSet<>();


            while (resultSet.next()){
                tableNameSet.add(resultSet.getString(1));
            }

            boolean tableExist = tableNameSet.containsAll(Set.of("Attendance","Picture","Student","User"));

            System.out.println(tableExist);

            if (!tableExist){
                System.out.println("Schema is about to auto generate");
                statement.execute(readDBScript());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String readDBScript(){
        InputStream resourceAsStream = getClass().getResourceAsStream("/schema.sql");
        try ( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));) {   // We don't need to worry about closing -> Use try with resource

            StringBuilder stringBuilder = new StringBuilder();
            String line ;

            while ((line=bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }




}
}
