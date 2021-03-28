package com.sudoku.validator.sudokuvalidator.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sudoku.validator.sudokuvalidator.exception.SudokuValidatorServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Service method implementations of Sudoku Validator
 */
@Service
public class SudokuValidatorService {

    private static final Logger logger = LogManager.getLogger(SudokuValidatorService.class);

    private static final int SUDOKU_BOARD_SIZE = 9;


    /**
     * Reads the file given by the user and validates if it meets proper sudoku file conditions
     *
     * @param fileName
     * @throws IOException
     * @throws CsvException
     */
    public int readFileAndValidateIt(String fileName) throws IOException, CsvException, SudokuValidatorServiceException {

        logger.debug(" readFileAndValidateIt method is called.Filename is " + fileName);


        File file = new File(fileName);
        if (!file.exists()) {
            logger.error(fileName + " does not exist. Please correct fileName and try again");
            throw new SudokuValidatorServiceException(fileName + " does not exist. Please correct fileName and try again");
        }
        int[][] sudokuTable = generateArrayListFromFile(file);
        logger.debug(" readFileAndValidateIt method is ended properly");
        return validateSudokuMain(sudokuTable);
    }

    /**
     * Validates Sudoku numbers which is received from the File
     * Converts every line into Int array , Sort them and finally validate them
     *
     * @param sudokuTable
     * @return
     */
    private int validateSudokuMain(int[][] sudokuTable) throws SudokuValidatorServiceException {   //TODO

        logger.debug(" validateSudoku method is called.Numbers size of the sudoku file list " + sudokuTable.length);

        if (sudokuTable.length == 0) {
            logger.error("numbers in the CSV path can not be null");
            throw new SudokuValidatorServiceException("numbers in the CSV path can not be null");
        }

        if (sudokuTable.length != 9) {
            logger.error("A proper Sudoku File should consist from 9 line");
            throw new SudokuValidatorServiceException("A proper Sudoku File should consist from 9 line");
        }
        // TODO check for validation
     /*   if (!checkSudokuLinesAreValid(numbers[i])) {
            logger.error("A proper Sudoku File should consist numbers from 1 to 9 ! Not other numbers ! Please" +
                    "correct your sudoku file");
            throw new SudokuValidatorServiceException("A proper Sudoku File should consist numbers from 1 to 9 ! Not other numbers ! Please" +
                    " correct your sudoku file");
        }*/

        if (!checkSudokuStatus(sudokuTable)) {
            return -1;
        }


        logger.debug(" validateSudoku method is ended properly");
        logger.info(" Sudoku is  validated correctly. Your file is suitable for a sudoku file");
        return 0;
    }


    /**
     * Reads the file and generates an Array from that file
     *
     * @param file
     * @return
     * @throws IOException
     * @throws CsvException
     */
    private int[][] generateArrayListFromFile(File file) throws IOException, CsvException {

        logger.debug(" generateArrayListFromFile method is called.File Name is " + file.getName() + " .Absolute file" +
                "path is " + file.getAbsolutePath());

        CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()));
        List<String[]> readedListFromCSV = reader.readAll();


        String[][] readedListArray = new String[readedListFromCSV.size()][];
        int[][] sudokuListIntArray = new int[9][9];
        readedListArray = readedListFromCSV.toArray(readedListArray);

        for (int i = 0; i < readedListArray.length; i++)  //TODO to be discussed
        {
            for (int j = 0; j < readedListArray[i].length; j++) {
                sudokuListIntArray[i][j] = Integer.parseInt(readedListArray[i][j]);

            }
        }


        logger.debug(" generateArrayListFromFile method is ended properly");
        return sudokuListIntArray;
    }

    private boolean checkSudokuStatus(int[][] grid) {
        for (int i = 0; i < 9; i++) {

            int[] row = new int[9];
            int[] square = new int[9];
            int[] column = grid[i].clone();

            for (int j = 0; j < 9; j++) {
                row[j] = grid[j][i];
                square[j] = grid[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3];
            }
            if (!(validate(column) && validate(row) && validate(square)))
                return false;
        }
        return true;
    }

    private boolean validate(int[] check) {
        int i = 0;
        Arrays.sort(check);
        for (int number : check) {
            if (number != ++i)
                return false;
        }
        return true;
    }


}
