package notepad;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Scanner;

public class FindAndReplace {


    public void getView(TextArea ta) {
        Stage stage = new Stage();
        BorderPane bp = new BorderPane();

        VBox leftLabels = new VBox();
        Label find = new Label("Find");
        find.setAlignment(Pos.CENTER);
        Label replace = new Label("Replace");
        replace.setAlignment(Pos.CENTER);
        leftLabels.setSpacing(20);
        leftLabels.getChildren().addAll(find, replace);

        VBox leftTF = new VBox();
        TextField findTF = new TextField();
        TextField replaceTF = new TextField();
        leftTF.setSpacing(10);
        leftTF.getChildren().addAll(findTF, replaceTF);

        HBox leftHBox = new HBox();
        leftHBox.setSpacing(10);
        leftHBox.getChildren().addAll(leftLabels, leftTF);

        bp.setLeft(leftHBox);
        RadioButton radioButton = new RadioButton("take letter case into account");


        Button replaceButton = new Button("Replace");
        replaceButton.setOnAction(event -> {
            Scanner scanner = new Scanner(ta.getText());
            StringBuilder newText = new StringBuilder();
            while (scanner.hasNextLine()) {
                String read = scanner.nextLine();
                if (radioButton.isSelected()) {
                    if (read.contains(findTF.getText())) {
                        read = read.replace(findTF.getText(), replaceTF.getText());
                    }
                    newText.append(read).append("\n");
                }

                if (!radioButton.isSelected()) {
                    if (read.toLowerCase().contains(findTF.getText().toLowerCase())) {
                        read = read.replace(findTF.getText(), replaceTF.getText());
                        read = read.replace(findTF.getText().toUpperCase(), replaceTF.getText());
                    }
                    newText.append(read).append("\n");
                }
            }

            if (newText.length() > 0) {
                ta.setText(newText.toString());
            }

        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> stage.close());

        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(replaceButton, cancelButton);
        bp.setRight(buttons);


        bp.setBottom(radioButton);
        bp.setPadding(new Insets(10, 10, 10, 10));

        bp.setPrefSize(300, 200);
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Find and replace");

    }
}
