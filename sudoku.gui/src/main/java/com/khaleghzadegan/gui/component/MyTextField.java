package com.khaleghzadegan.gui.component;

import com.khaleghzadegan.gui.common.Resources;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

import java.util.Objects;

public class MyTextField extends TextField {

    private final Coordinate coordinate;
    private final BorderWidthInfo borderWidthInfo;

    public MyTextField(Coordinate coordinate, BorderWidthInfo borderWidthInfo) {
        this.coordinate = coordinate;
        this.borderWidthInfo = borderWidthInfo;
        setProperties();
        setBehaviour();
    }

    private void setProperties() {
        setMinHeight(Resources.CELL_PREF_HEIGHT);
        setMinWidth(Resources.CELL_PREF_WIDTH);
        setPrefHeight(Resources.CELL_PREF_HEIGHT);
        setPrefWidth(Resources.CELL_PREF_WIDTH);
        setMaxHeight(Resources.CELL_PREF_HEIGHT);
        setMaxWidth(Resources.CELL_PREF_WIDTH);
        setBackground(Background.EMPTY);
        setAlignment(Pos.CENTER);
        setFont(new Font(Resources.CELL_FONT_SIZE));
        setStyle(Resources.CELL_BORDER_COLOR);
        setStyle(getStyle() + "-fx-border-width: " +
                borderWidthInfo.top + " " +
                borderWidthInfo.right + " " +
                borderWidthInfo.bottom + " " +
                borderWidthInfo.left + ";");
    }

    private void setBehaviour() {
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setStyle(getStyle() + Resources.CELL_SELECTED_BACKGROUND_COLOR);
            } else {
                setStyle(getStyle() + Resources.CELL_DESELECTED_BACKGROUND_COLOR);
            }
        });

        setOnKeyReleased(event -> {
            if (!isValid(getText()))
                clear();
        });
    }

    public Integer getXCoordinate() {
        return coordinate.getXCoordinate();
    }

    public Integer getYCoordinate() {
        return coordinate.getYCoordinate();
    }

    private Boolean isValid(String text) {
        if (text.length() == 1)
            return text.matches("[1-9]");
        return false;
    }

    public static class BorderWidthInfo {
        private final int top;
        private final int right;
        private final int bottom;
        private final int left;

        public BorderWidthInfo(int top, int right, int bottom, int left) {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }
    }

    public static class Coordinate {

        private final Integer xCoordinate;
        private final Integer yCoordinate;

        public Coordinate(Integer xCoordinate, Integer yCoordinate) {
            Objects.requireNonNull(xCoordinate);
            Objects.requireNonNull(yCoordinate);
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }

        public Integer getXCoordinate() {
            return xCoordinate;
        }

        public Integer getYCoordinate() {
            return yCoordinate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return xCoordinate.equals(that.xCoordinate) &&
                    yCoordinate.equals(that.yCoordinate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(xCoordinate, yCoordinate);
        }
    }
}
