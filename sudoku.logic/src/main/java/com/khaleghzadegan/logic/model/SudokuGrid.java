package com.khaleghzadegan.logic.model;

import java.io.Serializable;

public class SudokuGrid implements Serializable {

    public static final Integer GAME_BOUNDARY = 9;
    private GridState gridState;
    private final GridCell[][] gridCells;
    private Integer gridFitnessValue;

    public SudokuGrid() {
        this.gridState = GridState.NEW;
        gridFitnessValue = 0;
        gridCells = new GridCell[GAME_BOUNDARY][GAME_BOUNDARY];
    }

    public GridState getGridState() {
        return gridState;
    }

    public void setGridState(GridState gridState) {
        this.gridState = gridState;
    }

    public Integer getGridFitnessValue() {
        return gridFitnessValue;
    }

    public void setGridFitnessValue(Integer gridFitnessValue) {
        this.gridFitnessValue = gridFitnessValue;
    }

    public GridCell[][] getGridCells() {
        return gridCells;
    }
}
