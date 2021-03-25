package com.sudoku.validator.sudokuvalidator.service;

import com.opencsv.exceptions.CsvException;
import com.sudoku.validator.sudokuvalidator.exception.SudokuValidatorServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SudokuValidatorServiceTest {

    @Autowired
    private SudokuValidatorService sudokuValidatorService;

    @Test
    public void testReadFileAndValidateItSuccesfull() throws SudokuValidatorServiceException, IOException, CsvException {

        int validateResult = sudokuValidatorService.readFileAndValidateIt("src/test/resources/puzzleNameProperFile.txt");
        assertEquals(0, validateResult);
    }

    @Test
    public void testReadFileAndValidateItFail() throws SudokuValidatorServiceException, IOException, CsvException {

        int validateResult = sudokuValidatorService.readFileAndValidateIt("src/test/resources/puzzleNameNonProperFile.txt");
        assertEquals(-1, validateResult);
    }

}