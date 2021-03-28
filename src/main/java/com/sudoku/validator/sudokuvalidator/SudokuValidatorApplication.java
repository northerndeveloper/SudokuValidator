package com.sudoku.validator.sudokuvalidator;

import com.opencsv.exceptions.CsvException;
import com.sudoku.validator.sudokuvalidator.exception.SudokuValidatorApplicationException;
import com.sudoku.validator.sudokuvalidator.exception.SudokuValidatorServiceException;
import com.sudoku.validator.sudokuvalidator.service.SudokuValidatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Starting point of the SudokuValidatorApplication
 */
@SpringBootApplication
public class SudokuValidatorApplication {

    private static final Logger logger = LogManager.getLogger(SudokuValidatorApplication.class);

    private static SudokuValidatorService sudokuValidatorService;

    public SudokuValidatorApplication(SudokuValidatorService sudokuValidatorService) {
        this.sudokuValidatorService = sudokuValidatorService;
    }

    public static void main(String[] args) throws IOException, CsvException, SudokuValidatorApplicationException, SudokuValidatorServiceException {

        if(args == null || args.length == 0) {
            logger.error("there should be at least one argument which gives the sudoku puzzle");
            throw new SudokuValidatorApplicationException("there should be at least one argument which gives the sudoku puzzle");
        }

        SpringApplication.run(SudokuValidatorApplication.class, args);
        System.out.println("Validation Result of Sudoku File  = "   + sudokuValidatorService.readFileAndValidateSudokuFile(args[0]));
    }

}
