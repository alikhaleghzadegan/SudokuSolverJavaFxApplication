package com.khaleghzadegan.logic.service;

import com.khaleghzadegan.logic.model.SudokuGrid;
import com.khaleghzadegan.logic.service.impl.GamePlayServiceImpl;

public interface GamePlayService {
    SudokuGrid tryToSolve(SudokuGrid sudokuGrid);

    static GamePlayService build() {
        return new GamePlayServiceImpl();
    }

}
