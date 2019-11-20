/* DRIVER
 * This class includes main, and handles all of the GUI elements of the main screen and dialog boxes.
 */

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
public class Driver extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }
 
    @Override
    public void start(Stage primaryStage)
    {
        // Create all of the main interface elements
    	// Create logo image and imageview with the given filepath
    	Image logo = new Image("reformaticLogo.png");
    	ImageView logoView = new ImageView(logo); 
    	
    	// Setting the logo's imageview properties
    	logoView.setImage(logo);
    	logoView.setFitWidth(75);
    	logoView.setPreserveRatio(true);
    	logoView.setSmooth(true);
    	logoView.setCache(true);
    	
    	// Create program title and description
    	Label title = new Label("REFORMATIC");
        Text description = new Text("Automatic file reformatting tool.");
        description.setId("description");
        
        // LOAD FILE - button that gets user's file path and returns reformatted file
        Button formatBtn = new Button("Load File");
        formatBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Creating a new stage for the format pop-up, make it modal, and set its owner to the primary stage
            	Stage formatStage = new Stage();
            	formatStage.initModality(Modality.APPLICATION_MODAL);
            	formatStage.initOwner(primaryStage);
            	
            	// Create the format box's elements such as title, description, and buttons
                Label formatDialogTitle = new Label("FORMAT FILE");
                Text formatDescription = new Text("Enter the filepath where you have saved your text file. Include the name of the file.");
                
                // Text field for the user to enter their file path
                Text pathFieldDescrip = new Text("\nFilepath:");
                TextField formatFilepathField = new TextField("C:\\Users\\user\\Documents\\input.txt");
                formatFilepathField.setMaxWidth(400);
                
                // All of the elements are contained in a VBox
                VBox formatVBox = new VBox(formatDialogTitle, formatDescription, pathFieldDescrip, formatFilepathField);
                formatVBox.setId("formatBox");
                
                // Create and set properties of the format stage/scene
                Scene formatScene = new Scene(formatVBox, 450, 200);
                formatScene.getStylesheets().add("stylesheet.css");
                formatStage.setScene(formatScene);
                formatStage.show();
            }
         });
        
        // SAVE FILE - button that gets the user's preferred file path and name, to save the inputted file
        Button saveBtn = new Button("Save As");
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	// Create the stage
                Stage saveStage = new Stage();
                saveStage.initModality(Modality.APPLICATION_MODAL);
                saveStage.initOwner(primaryStage);
                
                // Create the title and description for the save pop up window
                Label saveDialogTitle = new Label("SAVE FILE");
                Text saveDescrip = new Text("To save your output, enter the filepath where you would like to save, and the name of the new file.\n");
                
                // Create a name field for the user to enter the file name 
                Text nameFieldDescrip = new Text("Name:");
                TextField saveNameField = new TextField("NewDocument");
                
                // Create a file path field for the user to enter where they would like to save their reformatted text file
                Text pathFieldDescrip = new Text("\nFilepath:");
                TextField saveFilepathField = new TextField("C:\\Users\\user\\Documents\\output.txt");
                
                // Set the maximum width of the text fields
                saveFilepathField.setMaxWidth(400);
                saveNameField.setMaxWidth(400);
                
                // All elements are contained in a VBox
                VBox formatVBox = new VBox(saveDialogTitle, saveDescrip, nameFieldDescrip, saveNameField, pathFieldDescrip, saveFilepathField);
                formatVBox.setId("formatBox");
                
                // Create and set properties of the save stage/scene
                Scene saveScene = new Scene(formatVBox, 450, 200);
                saveScene.getStylesheets().add("stylesheet.css");
                saveStage.setScene(saveScene);
                saveStage.show();
            }
         });
        
        // VIEW FLAGS - The view flags button brings up a dialog box to display a list of all flags
        Button viewFlagsBtn = new Button("View List of Flags");
        viewFlagsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	// Create the stage
                Stage viewFlagsStage = new Stage();
                viewFlagsStage.initModality(Modality.APPLICATION_MODAL);
                viewFlagsStage.initOwner(primaryStage);
                
                // Create the title and add flags into the view flags pop up window
                Label dialogTitle = new Label("LIST OF FLAGS");
                
                // Note: This can draw from a text file if we have time to implement
                Text dialogFlagList = new Text("-r\tRight Justification\n"
                							+ "-c\tCenter Justification\n"
                							+ "-l\tLeft Justification\n"
                							+ "-t\tTitle\n"
                							+ "-n\tRemove Indentation\n\n"
                							+ "-d\tDouble Space\n"
                							+ "-s\tSingle Space\n\n"
                							+ "-i\tIndent on First Line\n"
                							+ "-b\tBlock Text\n\n"
                							+ "-2\tTwo Column Layout\n"
                							+ "-1\tOne Column Layout\n"
                							+ "-e\tBlank Line\n");
                
                // All elements contained in a VBox
                VBox dialogVBox = new VBox(dialogTitle, dialogFlagList);
                dialogVBox.setId("dialogBox");
                
                // Create and set properties of the view flags stage/scene
                Scene viewFlagsScene = new Scene(dialogVBox, 200, 300);
                viewFlagsScene.getStylesheets().add("stylesheet.css");
                viewFlagsStage.setScene(viewFlagsScene);
                viewFlagsStage.show();
            }
         });
        
        // QUIT - The quit button quits the application upon click
        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction(e -> Platform.exit());
        
        // Adding the main screen's right column elements
        Label previewTitle = new Label("OUTPUT PREVIEW");
        previewTitle.setId("previewTitle");
        
        // Output text area, where the reformatted file preview text will be placed
        TextArea output = new TextArea();
        output.setId("output");
        output.setPrefWidth(640); 
        output.setPrefHeight(500); 
        
        // Error Log, where the error messages will appear
        Label errorLogTitle = new Label("ERROR LOG");
        errorLogTitle.setId("errorLogTitle");
        
        TextArea errorLog = new TextArea();
        errorLog.setId("errorLog");
        
        // Separate the grid pane into a left and right column
        VBox leftColumn = new VBox(logoView, title, description, formatBtn, saveBtn, viewFlagsBtn, quitBtn);
        leftColumn.setId("lCol");
        VBox rightColumn = new VBox(previewTitle, output, errorLogTitle, errorLog);
        rightColumn.setId("rCol");
        
        // Set all left and right column elements to align to center
        leftColumn.setAlignment(Pos.CENTER);
        rightColumn.setAlignment(Pos.CENTER); 
        
        // Create the grid pane to store both columns
        GridPane grid = new GridPane();
        
        // Add the left and right columns (to column indexes 0 and 1 respectively)
        grid.add(leftColumn, 0, 0);
        grid.add(rightColumn, 1, 0);
        
        // Set both columns to automatically scale
        GridPane.setHgrow(leftColumn, Priority.ALWAYS);
        GridPane.setVgrow(leftColumn, Priority.ALWAYS);
        GridPane.setHgrow(rightColumn, Priority.ALWAYS);
        GridPane.setVgrow(rightColumn, Priority.ALWAYS);
        VBox.setVgrow(output, Priority.ALWAYS);

        // Set the minimum size of the grid to 350 x 250 px
        grid.setMinSize(350, 250);
        
        // Set each column to fill 50% of the screen
        ColumnConstraints equalWidth = new ColumnConstraints();
        equalWidth.setPercentWidth(50);
        grid.getColumnConstraints().addAll(equalWidth);
 
        // Set the background color of the grid pane to a custom color
        Color leftBgColor = Color.web("0xDFE3EF");
        Color rightBgColor = Color.web("0x2D3142");
        BackgroundFill leftBgFill = new BackgroundFill(leftBgColor, CornerRadii.EMPTY, Insets.EMPTY); 
        BackgroundFill rightBgFill = new BackgroundFill(rightBgColor, CornerRadii.EMPTY, Insets.EMPTY); 
        
        Background leftBackground = new Background(leftBgFill);
        Background rightBackground = new Background(rightBgFill); 
        
        grid.setBackground(leftBackground);
        leftColumn.setBackground(leftBackground);
        rightColumn.setBackground(rightBackground);
        
        // Create the scene
        Scene scene = new Scene(grid);
        
        // Linking an external stylesheet to the project
        scene.getStylesheets().add("stylesheet.css");
        
        // Set stage properties
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        
        // Add the scene to the stage
        primaryStage.setScene(scene);
        
        // Set the title of the stage
        primaryStage.setTitle("Reformatic - A File Reformatting Tool");
        
        primaryStage.getIcons().add(new Image("reformaticLogo.png"));
        // Display the stage
        primaryStage.show();
    }
}