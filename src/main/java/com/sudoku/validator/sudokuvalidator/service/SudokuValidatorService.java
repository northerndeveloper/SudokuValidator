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
import java.util.Comparator;
import java.util.List;

/**
 * Service method implementations of Sudoku Validator
 */
@Service
public class SudokuValidatorService {

    private static final Logger logger = LogManager.getLogger(SudokuValidatorService.class);


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
        String[][] readedListFromCSV = generateArrayListFromFile(file);
        logger.debug(" readFileAndValidateIt method is ended properly");
        return validateSudoku(readedListFromCSV);
    }

    /**
     * Validates Sudoku numbers which is received from the File
     * Converts every line into Int array , Sort them and finally validate them
     *
     * @param numbers
     * @return
     */
    private int validateSudoku(String[][] numbers) throws SudokuValidatorServiceException {

        logger.debug(" validateSudoku method is called.Numbers size of the sudoku file list " + numbers.length);

        if (numbers.length == 0) {
            logger.error("numbers in the CSV path can not be null");
            throw new SudokuValidatorServiceException("numbers in the CSV path can not be null");
        }

        if (numbers.length != 9) {
            logger.error("A proper Sudoku File should consist from 9 line");
            throw new SudokuValidatorServiceException("A proper Sudoku File should consist from 9 line");
        }


        for (int i = 0; i < numbers.length; i++) {

            if (numbers[i].length != 9) {
                logger.error("A proper Sudoku File should consist from 9 line");
                throw new SudokuValidatorServiceException("A proper Sudoku File should consist from 9 line");
            }

            Arrays.sort(numbers[i]);

            if (!checkSudokuLinesAreValid(numbers[i])) {
                logger.error("A proper Sudoku File should consist numbers from 1 to 9 ! Not other numbers ! Please" +
                        "correct your sudoku file");
                throw new SudokuValidatorServiceException("A proper Sudoku File should consist numbers from 1 to 9 ! Not other numbers ! Please" +
                        " correct your sudoku file");
            }

            Arrays.sort(numbers, new ColumnComparator(i, SortingOrder.ASCENDING));
            for (int j = 0; j < numbers[i].length; j++) {
                if ( !( numbers[i][j].equals(String.valueOf(j + 1))) ) {
                    logger.info(" Sudoku is not validated correctly. Please correct your sudoku numbers in order to valdiate it. The problemed line "
                            + i + " column is " + j);
                    return -1;
                }
            }
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
    private String[][] generateArrayListFromFile(File file) throws IOException, CsvException {

        logger.debug(" generateArrayListFromFile method is called.File Name is " + file.getName() + " .Absolute file" +
                "path is " + file.getAbsolutePath());

        CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()));
        List<String[]> readedListFromCSV = reader.readAll();

        String[][] readedListArray = new String[readedListFromCSV.size()][];
        readedListArray = readedListFromCSV.toArray(readedListArray);

        logger.debug(" generateArrayListFromFile method is ended properly");

        return readedListArray;
    }

    /**
     * Checks if sudoku lines are valid or not
     *
     * @param sudokuStrLine
     * @return
     */
    private boolean checkSudokuLinesAreValid(String[] sudokuStrLine) {

        String[] properSudokuNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        if (!Arrays.equals(properSudokuNumbers, sudokuStrLine)) {
            return false;
        }
        return true;
    }


    private static void sortbyColumn(String arr[][], int col)
    {
        // Using built-in sort function Arrays.sort
        Arrays.sort(arr, new Comparator<String[]>() {

            @Override
            // Compare values according to columns
            public int compare(String[] entry1,
                               String[] entry2) {

                // To sort in descending order revert
                // the '>' Operator
                if (Integer.parseInt(entry1[col]) > Integer.parseInt(entry2[col]))
                    return 1;
                else
                    return -1;
            }
        });  // End of function call sort().
    }


}
