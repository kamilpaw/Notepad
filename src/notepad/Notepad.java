package notepad;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Notepad extends Application {

    BorderPane bp;
    MenuBar menuBar;
    File file;


    public void start(Stage stage) {
        bp = new BorderPane();
        menuBar = new MenuBar();

        bp.setTop(menuBar);
        bp.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        TextArea ta = new TextArea();
        ta.setPrefSize(bp.getWidth(), bp.getHeight());
        bp.setCenter(ta);

        Menu file = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(event -> {
            ta.setText("");
            stage.setTitle("notepad");
        });

        MenuItem open = new MenuItem("Open");
        open.setOnAction(event -> open(stage, ta));

        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> save(stage, ta));

        MenuItem saveAs = new MenuItem("Save as");
        saveAs.setOnAction(event -> saveAs(stage, ta));

        MenuItem close = new MenuItem("Close");
        close.setOnAction(event -> Platform.exit());

        file.getItems().addAll(newFile, open, save, saveAs, close);

        Menu edit = new Menu("Edit");

        MenuItem cut = new MenuItem("Cut");
        cut.setOnAction(event -> ta.cut());

        MenuItem copy = new MenuItem("Copy");
        copy.setOnAction(event -> ta.copy());

        MenuItem paste = new MenuItem("Paste");
        paste.setOnAction(event -> ta.paste());

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(event -> ta.deleteText(ta.getSelection().getStart(), ta.getSelection().getEnd()));

        MenuItem replace = new MenuItem("Replace");
        replace.setOnAction(event -> replace(ta));

        MenuItem time = new MenuItem("Date/Time");
        time.setOnAction(event -> insertTime(ta));

        edit.getItems().addAll(cut, copy, paste, delete, replace, time);

        Menu format = new Menu("Format");
        MenuItem font = new MenuItem("Font");
        format.getItems().add(font);

        Menu view = new Menu("View");
        MenuItem statusBar = new MenuItem("Status bar");
        view.getItems().add(statusBar);

        Menu help = new Menu("Help");
        MenuItem helpItem = new MenuItem("Help");
        help.getItems().add(helpItem);


        menuBar.getMenus().addAll(file, edit, format, view, help);


        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();

    }

    public void replace (TextArea ta) {

    }

    public void insertTime(TextArea ta) {
        LocalDateTime ldt = LocalDateTime.now();
        String dt = ldt.format(DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss"));
        ta.replaceSelection(dt);
    }


    public void open(Stage stage, TextArea ta) {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(stage);
        if (file != null && file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                fis.read(bytes);
                ta.setText(new String(bytes));
                fis.close();
            } catch (Exception IO) {
                System.out.println(IO.getMessage());
            }
        }
    }

    public void save(Stage stage, TextArea ta) {
        if (file == null) {
            saveAs(stage, ta);
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(ta.getText().getBytes());
                fos.close();

            } catch (Exception IO) {
                System.out.println(IO.getMessage());
            }
        }
    }

    public void saveAs(Stage stage, TextArea ta) {
        FileChooser fc = new FileChooser();
        file = fc.showSaveDialog(stage);
        if (file != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(ta.getText().getBytes());
                fos.close();
            } catch (Exception IO) {
                System.out.println(IO.getMessage());
            }
        }
    }

}

