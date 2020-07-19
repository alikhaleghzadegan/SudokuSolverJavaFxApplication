package com.khaleghzadegan.gui.service;

import com.khaleghzadegan.gui.common.Resources;
import com.khaleghzadegan.gui.component.MyTextField;
import com.khaleghzadegan.logic.model.GridCell;
import com.khaleghzadegan.logic.model.SudokuGrid;
import com.khaleghzadegan.logic.service.GamePlayService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class UserInterface {

    private final Stage stage;
    private final GamePlayService gamePlayService;
    private final Map<MyTextField.Coordinate, MyTextField> textFieldHashMap = new HashMap<>();

    public UserInterface(Stage stage) {
        this.stage = stage;
        this.gamePlayService = GamePlayService.build();
        initializeUserInterface();
        stage.show();
    }

    private void initializeUserInterface() {
        TilePane centerPane = initializeAndCenterPane();
        HBox topPane = initializeAndGetTopPane();
        HBox bottomPane = initializeAndGetBottomPane();
        BorderPane pane = new BorderPane();
        pane.setTop(topPane);
        pane.setCenter(centerPane);
        pane.setBottom(bottomPane);
        var scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle(Resources.WINDOW_TITLE);
    }

    private HBox initializeAndGetTopPane() {
        Button solveButton = initializeAndGetSolveButton();
        Button resetButton = initializeAndGetResetButton();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(Resources.TOP_PANE_PADDING));
        hbox.setSpacing(Resources.TOP_PANE_SPACING);
        hbox.setStyle(Resources.TOP_PANE_BACKGROUND_COLOR);
        hbox.getChildren().addAll(solveButton, resetButton);
        return hbox;
    }

    private HBox initializeAndGetBottomPane() {
        Label statusLabel = initializeAndGetStatusLabel();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(Resources.BOTTOM_PANE_PADDING));
        hbox.setSpacing(Resources.BOTTOM_PANE_SPACING);
        hbox.setStyle(Resources.BOTTOM_PANE_BACKGROUND_COLOR);
        hbox.getChildren().add(statusLabel);
        return hbox;
    }

    private TilePane initializeAndCenterPane() {
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(9);
        tilePane.setStyle(Resources.WINDOW_BACKGROUND_COLOR);
        tilePane.setMaxHeight(Resources.BORDER_PREF_WIDTH_HEIGHT);
        tilePane.setMaxWidth(Resources.BORDER_PREF_WIDTH_HEIGHT);
        tilePane.setMinHeight(Resources.BORDER_PREF_WIDTH_HEIGHT);
        tilePane.setMinWidth(Resources.BORDER_PREF_WIDTH_HEIGHT);
        initializeTextFieldCoordinates(tilePane);
        return tilePane;
    }

    private void initializeTextFieldCoordinates(TilePane tilePane) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int top = Resources.CELL_BORDER_NORMAL;
                int right = Resources.CELL_BORDER_NORMAL;
                int bottom = Resources.CELL_BORDER_NORMAL;
                int left = Resources.CELL_BORDER_NORMAL;
                if (i == 2 || i == 5)
                    bottom = Resources.CELL_BORDER_THICK;
                if (j == 2 || j == 5)
                    right = Resources.CELL_BORDER_THICK;

                var coordinate = new MyTextField.Coordinate(i, j);
                var borderWidthInfo = new MyTextField.BorderWidthInfo(top, right, bottom, left);
                MyTextField tile = new MyTextField(coordinate, borderWidthInfo);
                textFieldHashMap.put(coordinate, tile);
                tilePane.getChildren().add(tile);
            }
        }
    }

    private Button initializeAndGetSolveButton() {
        Button btn = new Button(Resources.BUTTON_SOLVE_TEXT);
        btn.setPrefHeight(Resources.BUTTON_PREF_HEIGHT);
        btn.setPrefWidth(Resources.BUTTON_PREF_WIDTH);
        btn.setFont(new Font(Resources.BUTTON_FONT_SIZE));
        setSolveButtonOnMouseClicked(btn);
        return btn;
    }

    private void setSolveButtonOnMouseClicked(Button btn) {
        btn.setOnMouseClicked(event -> {
            try {
                SudokuGrid blueprintSudokuGrid = extractBlueprintSudokuGridFromUserInput();
                final var solution = gamePlayService.tryToSolve(blueprintSudokuGrid);
                displaySolution(solution);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void displaySolution(SudokuGrid solution) {
        var solutionGridCells = solution.getGridCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final var textField = textFieldHashMap.get(new MyTextField.Coordinate(i, j));
                GridCell gridCell = solutionGridCells[i][j];
                if (gridCell.getCellType().equals(GridCell.CellType.FIXED)) {
                    textField.setFont(Font.font("Verdana", FontWeight.BOLD, Resources.CELL_FONT_SIZE_BOLD));
                }
                textField.setText(gridCell.getCellValue().toString());
            }
        }
    }

    private SudokuGrid extractBlueprintSudokuGridFromUserInput() {
        SudokuGrid blueprintSudokuGrid = new SudokuGrid();
        final var gridCells = blueprintSudokuGrid.getGridCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final var text = textFieldHashMap.get(new MyTextField.Coordinate(i, j)).getText();
                GridCell gridCell;
                if (text.matches("[1-9]")) {
                    gridCell = new GridCell(Integer.parseInt(text), GridCell.CellType.FIXED);
                } else {
                    gridCell = new GridCell(0, GridCell.CellType.CHANGEABLE);
                }
                gridCells[i][j] = gridCell;
            }
        }
        return blueprintSudokuGrid;
    }

    private Button initializeAndGetResetButton() {
        Button btn = new Button(Resources.BUTTON_RESET_TEXT);
        btn.setPrefHeight(Resources.BUTTON_PREF_HEIGHT);
        btn.setPrefWidth(Resources.BUTTON_PREF_WIDTH);
        btn.setFont(new Font(Resources.BUTTON_FONT_SIZE));
        setResetButtonOnMouseClicked(btn);
        return btn;
    }

    private void setResetButtonOnMouseClicked(Button btn) {
        btn.setOnMouseClicked(event -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    final var textField = textFieldHashMap.get(new MyTextField.Coordinate(i, j));
                    textField.setFont(new Font(Resources.CELL_FONT_SIZE));
                    textField.setText("");
                }
            }
        });
    }

    private Label initializeAndGetStatusLabel() {
        Label lbl = new Label(Resources.LABEL_STATUS_WELCOME_MESSAGE);
        lbl.setFont(new Font(Resources.BOTTOM_PANE_STATUS_FONT_SIZE));
        return lbl;
    }

}
