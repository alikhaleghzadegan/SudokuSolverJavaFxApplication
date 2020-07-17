package com.khaleghzadegan.logic.model;

import java.io.Serializable;

public class GridCell implements Serializable {
    private Integer cellValue;
    private final CellType cellType;

    public GridCell(Integer cellValue, CellType cellType) {
        this.cellValue = cellValue;
        this.cellType = cellType;
    }

    public Integer getCellValue() {
        return cellValue;
    }

    public void setCellValue(Integer cellValue) {
        if (cellType == CellType.CHANGEABLE) {
            this.cellValue = cellValue;
        } else {
            throw new RuntimeException("ERROR: CELL VALUE IS FIXED AND CAN NOT BE CHANGED!");
        }
    }

    public CellType getCellType() {
        return cellType;
    }

    public enum CellType {
        FIXED,
        CHANGEABLE
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "cellValue=" + cellValue +
                ", cellType=" + cellType +
                '}';
    }
}