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
    public int readFileAndValidateSudokuFile(String fileName) throws IOException, CsvException, SudokuValidatorServiceException {

        logger.debug(" readFileAndValidateIt method is called.Filename is " + fileName);


        File file = new File(fileName);
        if (!file.exists()) {
            logger.error(fileName + " does not exist. Please correct fileName and try again");
            throw new SudokuValidatorServiceException(fileName + " does not exist. Please correct fileName and try again");
        }
        int[][] sudokuTable = getSudokuTableFromSudokuFile(file);
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
    public int validateSudokuMain(int[][] sudokuTable) throws SudokuValidatorServiceException {

        logger.debug(" validateSudoku method is called.Numbers size of the sudoku file list " + sudokuTable.length);

        if (sudokuTable.length == 0) {
            logger.error("numbers in the CSV path can not be null");
            throw new SudokuValidatorServiceException("numbers in the CSV path can not be null");
        }

        if (sudokuTable.length != 9) {
            logger.error("A proper Sudoku File should consist from 9 line");
            throw new SudokuValidatorServiceException("A proper Sudoku File should consist from 9 line");
        }

        if (!checkSudokuStatus(sudokuTable)) {
            return -1;
        }

        logger.debug(" validateSudoku method is ended properly");
        logger.info(" Sudoku is  validated correctly. Your file is suitable for Sudoku");
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
    public int[][] getSudokuTableFromSudokuFile(File file) throws IOException, CsvException, SudokuValidatorServiceException {

        logger.debug(" getSudokuTableFromSudokuFile method is called.File Name is " + file.getName() + " .Absolute file" +
                "path is " + file.getAbsolutePath());

        CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()));
        List<String[]> readedListFromCSV = reader.readAll();

        String[][] readedListArray = new String[readedListFromCSV.size()][];
        int[][] sudokuListIntArray = new int[9][9];
        readedListArray = readedListFromCSV.toArray(readedListArray);

        if(readedListArray.length != 9 ){
            logger.error("A Proper sudoku line should consist from 9 line.No more or less");
            throw new SudokuValidatorServiceException("A Proper sudoku line should consist from 9 line.No more or less");
        }

        for (int i = 0; i < readedListArray.length; i++) {
            for (int j = 0; j < readedListArray[i].length; j++) {
                if(!Character.isDigit(readedListArray[i][j].charAt(0))){
                    logger.error("Input file should consist of digits ! Not any character !. Problem line is " +  i +
                            " problem column is " + j);
                    throw new SudokuValidatorServiceException("Input file should consist of digits ! Not any character !. Problem line is " +  i +
                            " problem column is " + j);

                }
                sudokuListIntArray[i][j] = Integer.parseInt(readedListArray[i][j]);
            }
        }

        logger.debug(" getSudokuTableFromSudokuFile method is ended properly");
        return sudokuListIntArray;
    }

    /**
     * Checks Sudoku State
     *
     * @param sudokuTable
     * @return
     */
    public boolean checkSudokuStatus(int[][] sudokuTable) {

        logger.debug("Validation is initiated inside checkSudokuStatus method");
        for (int i = 0; i < 9; i++) {

            int[] row = new int[SUDOKU_BOARD_SIZE];
            int[] square = new int[SUDOKU_BOARD_SIZE];
            int[] column = sudokuTable[i].clone();

            for (int j = 0; j < SUDOKU_BOARD_SIZE; j++) {
                row[j] = sudokuTable[j][i];
                square[j] = sudokuTable[(i / 3) * 3 + j / 3] [i * 3 % SUDOKU_BOARD_SIZE + j % 3];
            }
            if (!(validate(column) && validate(row) && validate(square))) {
                logger.error("Validation at failed at row = " + row + " column " + column + " Please check those row" +
                        "and columns and correct it.");
                 System.out.println("Validation at failed at row = " + row + " column " + column + " Please check those row" +
                    "and columns and correct it.");
                return false;
            }
        }
        logger.debug("Validation is successfull inside checkSudokuStatus method");
        return true;
    }

    /**
     * Checks if a column & row & square is suitable for a proper Sudoku
     *
     * @param checkedRowColumn
     * @return
     */
    public boolean validate(int[] checkedRowColumn) {

        logger.debug("validate method is initiated with the check size " + checkedRowColumn.length);
        int count = 0;
        Arrays.sort(checkedRowColumn);
        for (int number : checkedRowColumn) {
            if (number != ++count) {
                logger.error("Validation is failed at number " + number);
                return false;
            }
        }
        logger.debug("validate method is completed succesfully");
        return true;
    }
}
