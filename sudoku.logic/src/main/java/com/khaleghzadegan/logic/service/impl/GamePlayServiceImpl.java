package com.khaleghzadegan.logic.service.impl;

import com.khaleghzadegan.logic.exception.AnswerNotFoundException;
import com.khaleghzadegan.logic.model.GridCell;
import com.khaleghzadegan.logic.model.SudokuGrid;
import com.khaleghzadegan.logic.service.GameOperationsService;
import com.khaleghzadegan.logic.service.GamePlayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePlayServiceImpl implements GamePlayService {

    private final Random random = new Random();
    private static final Integer FITNESS_GOAL = 972;
    private static final Integer POPULATION_COUNT = 20 * SudokuGrid.GAME_BOUNDARY;
    private static final Integer ITERATION_COUNT = 100_000;

    private final GameOperationsService gameOperationsService = GameOperationsService.build();


    @Override
    public SudokuGrid tryToSolve(SudokuGrid blueprintSudokuGrid) {
        int resetPoint = getResetPoint(blueprintSudokuGrid);
        int iterationCounter = 0;
        int resetPointCounter = 0;
        SudokuGrid answer;

        while (iterationCounter < ITERATION_COUNT) {
            var population = generateInitialPopulation(blueprintSudokuGrid);
            while (resetPointCounter < resetPoint) {
                answer = removeRepetitionAndUpdateFitnessValuesAndGetSolutionIfExist(population);
                if (answer != null)
                    return answer;
                var populationBeforeCrossover = clonePopulation(population);
                performCrossover(population);
                answer = removeRepetitionAndUpdateFitnessValuesAndGetSolutionIfExist(population);
                if (answer != null)
                    return answer;
                population.forEach(gameOperationsService::performBitWiseMutation);
                var populationAfterMutation = clonePopulation(population);
                answer = removeRepetitionAndUpdateFitnessValuesAndGetSolutionIfExist(population);
                if (answer != null)
                    return answer;
                population = gameOperationsService.performElitismAndGetNextGeneration(populationBeforeCrossover, populationAfterMutation);
                iterationCounter++;
                resetPointCounter++;
            }
            resetPointCounter = 0;
        }
        throw new AnswerNotFoundException("ANSWER NOT FOUND!");
    }

    private void performCrossover(List<SudokuGrid> population) {
        for (int i = 0; i < POPULATION_COUNT - 1; i++)
            gameOperationsService.performUniformRowWiseCrossover(population.get(i), population.get(i + 1));
    }

    private List<SudokuGrid> clonePopulation(List<SudokuGrid> population) {
        List<SudokuGrid> clonedPopulation = new ArrayList<>();
        // todo
        return clonedPopulation;
    }

    private SudokuGrid removeRepetitionAndUpdateFitnessValuesAndGetSolutionIfExist(List<SudokuGrid> population) {
        population.forEach(gameOperationsService::removeRepetition);
        population.forEach(gameOperationsService::calculateAndUpdateFitnessValue);
        return getAnswer(population);
    }


    private List<SudokuGrid> generateInitialPopulation(SudokuGrid blueprintSudokuGrid) {
        List<SudokuGrid> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_COUNT; i++)
            population.add(getNewSudokuGridBasedOn(blueprintSudokuGrid));

        return population;
    }

    private SudokuGrid getNewSudokuGridBasedOn(SudokuGrid blueprintSudokuGrid) {
        SudokuGrid sudokuGrid = new SudokuGrid();
        final var gridCells = sudokuGrid.getGridCells();
        for (int i = 0; i < SudokuGrid.GAME_BOUNDARY; i++)
            for (int j = 0; j < SudokuGrid.GAME_BOUNDARY; j++)
                gridCells[i][j] = getNewGridCellBasedOn(blueprintSudokuGrid.getGridCells()[i][j]);

        return sudokuGrid;
    }

    private GridCell getNewGridCellBasedOn(GridCell gridCell) {
        if (gridCell.getCellType() == GridCell.CellType.FIXED) {
            var value = gridCell.getCellValue();
            return new GridCell(value, GridCell.CellType.FIXED);
        } else {
            var value = random.nextInt(SudokuGrid.GAME_BOUNDARY) + 1;
            return new GridCell(value, GridCell.CellType.CHANGEABLE);
        }
    }

    private Integer getResetPoint(SudokuGrid blueprintSudokuGrid) {
        final var gridCells = blueprintSudokuGrid.getGridCells();
        Integer givenNumbers = getGivenNumbersCount(gridCells);

        if (givenNumbers <= 27) {
            return 2000;
        } else if (givenNumbers <= 29) {
            return 350;
        } else if (givenNumbers <= 31) {
            return 300;
        } else return 200;

    }

    private Integer getGivenNumbersCount(GridCell[][] gridCells) {
        Integer counter = 0;
        for (int i = 0; i < SudokuGrid.GAME_BOUNDARY; i++)
            for (int j = 0; j < SudokuGrid.GAME_BOUNDARY; j++)
                if (gridCells[i][j].getCellType() == GridCell.CellType.CHANGEABLE) counter++;

        return counter;
    }

    private SudokuGrid getAnswer(List<SudokuGrid> population) {
        for (var sudokuGrid : population)
            if (sudokuGrid.getGridFitnessValue().equals(FITNESS_GOAL)) return sudokuGrid;

        return null;
    }

}
