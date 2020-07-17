package com.khaleghzadegan.gui.component;

import javafx.scene.control.TextField;

public class SudokuTextField extends TextField {

    private final Integer x;
    private final Integer y;

    public SudokuTextField(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public void replaceText(int i, int i1, String str) {
        if (!str.matches("[0-9]")) {
            super.replaceText(i, i1, str);
        }
    }

    @Override
    public void replaceSelection(String str) {
        if (!str.matches("[0-9]")) {
            super.replaceSelection(str);
        }
    }
}
