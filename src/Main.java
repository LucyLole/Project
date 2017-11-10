import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Model.DatabaseConnection;

public class Main extends Application {
    public static DatabaseConnection Database;
    //DatabaseConnection.DatabaseConnection("../MusicPlayerDatabase.db");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Database = new DatabaseConnection("../MusicPlayerDataBase.db");
        BorderPane borderRoot = new BorderPane();
        VBox root = new VBox();

        //setting up stuff
        primaryStage.setTitle("Hello World");
        Scene mainScene = new Scene(root, 300, 275);

        // top box things
        HBox topSection = new HBox(10);
        borderRoot.setTop(topSection);
        mainScene.getStylesheets().add("stylesheet.css");
        MenuBar topMenu = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem fileItem1 = new MenuItem("Open");
        fileMenu.getItems().addAll(fileItem1);
        topMenu.getMenus().addAll(fileMenu);
        //root.getChildren().add(fileMenu);

        //buttons
        Button audioButton = new Button("Audio");
        audioButton.getStyleClass().add("top_button");
        Button videoButton = new Button("Video");
        //alignment
        videoButton.setAlignment(Pos.CENTER_RIGHT);
        audioButton.setAlignment(Pos.CENTER_RIGHT);
        topSection.setAlignment(Pos.TOP_RIGHT);
        BorderPane.setAlignment(topSection, Pos.TOP_RIGHT);
        //adding to the scene
        topSection.getChildren().add(audioButton);
        topSection.getChildren().add(videoButton);
        //initialising
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

