package com.khaleghzadegan.gui.service.impl;

import com.khaleghzadegan.gui.component.MyTextField;
import com.khaleghzadegan.gui.service.UserInterfaceService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class UserInterfaceServiceImpl implements UserInterfaceService {

    private final Stage stage;
    private final Map<Coordinate, MyTextField> textFieldCoordinates;

    public UserInterfaceServiceImpl(Stage stage) {
        this.stage = stage;
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
        stage.show();
    }

    private void initializeUserInterface() {
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(9);
        tilePane.setStyle(Style.WINDOW_BACKGROUND_COLOR);
        tilePane.setMaxHeight(630);
        tilePane.setMaxWidth(630);
        tilePane.setMinHeight(630);
        tilePane.setMinWidth(630);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int right = Style.CELL_BORDER_WIDTH_NORMAL;
                int bottom = Style.CELL_BORDER_WIDTH_NORMAL;
                if (i == 2 || i == 5)
                    bottom = Style.CELL_BORDER_WIDTH_THICK;
                if (j == 2 || j == 5)
                    right = Style.CELL_BORDER_WIDTH_THICK;

                var coordinate = new Coordinate(i, j);
                var border = new MyBorder(Style.CELL_BORDER_WIDTH_NORMAL, right, bottom, Style.CELL_BORDER_WIDTH_NORMAL);
                MyTextField tile = new MyTextField(coordinate, border);
                tile.setPadding(new Insets(1));
                textFieldCoordinates.put(coordinate, tile);
                tilePane.getChildren().add(tile);
            }
        }


        BorderPane pane = new BorderPane();
        pane.setCenter(tilePane);

        var scene = new Scene(pane);
        stage.setScene(scene);
    }


}
