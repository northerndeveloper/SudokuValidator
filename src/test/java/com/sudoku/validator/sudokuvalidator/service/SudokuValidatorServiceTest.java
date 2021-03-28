package com.sudoku.validator.sudokuvalidator.service;

import com.opencsv.exceptions.CsvException;
import com.sudoku.validator.sudokuvalidator.exception.SudokuValidatorServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SudokuValidatorServiceTest {

    @Autowired
    private SudokuValidatorService sudokuValidatorService;

    @Test
    public void testReadFileAndValidateItSuccessful() throws SudokuValidatorServiceException, IOException, CsvException {

        int validateResult = sudokuValidatorService.readFileAndValidateSudokuFile("src/test/resources/puzzleNameProperFile.txt");
        assertEquals(0, validateResult);
    }

    @Test
    public void testReadFileAndValidateItFail() throws SudokuValidatorServiceException, IOException, CsvException {

        int validateResult = sudokuValidatorService.readFileAndValidateSudokuFile("src/test/resources/puzzleNameNonProperFile.txt");
        assertEquals(-1, validateResult);
    }

    @Test
    public void testReadFileAndValidateItWithLettersFail() {

        assertThrows(SudokuValidatorServiceException.class, () -> {
            sudokuValidatorService.readFileAndValidateSudokuFile("src/test/resources/sudokuPuzzleWithLetters.txt");
        });
    }

    @Test
    public void testReadFileAndValidateItWithMoreLineFail() {

        assertThrows(SudokuValidatorServiceException.class, () -> {
            sudokuValidatorService.readFileAndValidateSudokuFile("src/test/resources/sudokuPuzzleTenLine.txt");
        });
    }

    @Test
    public void testValidateSucess() {
        int[] check = {1,9,7,8,3,4,5,6,2};
        assertEquals(true, sudokuValidatorService.validate(check));
    }

    @Test
    public void testValidateFails() {
        int[] check = {1,2,7,7,3,3,4,4,1};
        assertEquals(false, sudokuValidatorService.validate(check));
    }

    @Test
    public void testCheckSudokuStatusSuccess() {

        int[][] sudokuTable = new int[][] { {4,3,5,2,6,9,7,8,1},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,5,7,1,3,6},
                {7,6,3,4,1,8,2,5,9}
        };

        assertEquals(true, sudokuValidatorService.checkSudokuStatus(sudokuTable));
    }

    @Test
    public void testCheckSudokuStatusFails() {

        int[][] sudokuTable = new int[][] { {1,1,1,1,1,1,1,1,1},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,5,7,1,3,6},
                {7,6,3,4,1,8,2,5,9}
        };

        assertEquals(false, sudokuValidatorService.checkSudokuStatus(sudokuTable));
    }

//    public int[][] getSudokuTableFromSudokuFile(File file) throws IOException, CsvException, SudokuValidatorServiceException {

    @Test
    public void testSudokuTableFromSudokuFileSuccessWithAProperFile() throws SudokuValidatorServiceException, IOException, CsvException {

        File file = new File("src/test/resources/puzzleNameProperFile.txt");

        int[][] sudokuTable = new int[][] { {4,3,5,2,6,9,7,8,1},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,5,7,1,3,6},
                {7,6,3,4,1,8,2,5,9}
        };


        assertEquals(sudokuTable.length,sudokuValidatorService.getSudokuTableFromSudokuFile(file).length);
        assertEquals(sudokuTable[0][1],sudokuValidatorService.getSudokuTableFromSudokuFile(file)[0][1]);
        assertEquals(sudokuTable[2][3],sudokuValidatorService.getSudokuTableFromSudokuFile(file)[2][3]);
        assertEquals(sudokuTable[5][7],sudokuValidatorService.getSudokuTableFromSudokuFile(file)[5][7]);

    }

//    public int validateSudokuMain(int[][] sudokuTable) throws SudokuValidatorServiceException {

    @Test
    public void testValidateSudokuMainSuccesfull() throws SudokuValidatorServiceException {

        int[][] sudokuTable = new int[][] { {4,3,5,2,6,9,7,8,1},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,5,7,1,3,6},
                {7,6,3,4,1,8,2,5,9}
        };

        assertEquals(0,sudokuValidatorService.validateSudokuMain(sudokuTable));
    }

    @Test
    public void testValidateSudokuMainFail() throws SudokuValidatorServiceException {

        int[][] sudokuTable = new int[][] { {1,2,3,2,4,1,9,1,5},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,5,7,1,3,6},
                {7,6,3,4,1,8,2,5,9}
        };

        assertEquals(-1,sudokuValidatorService.validateSudokuMain(sudokuTable));
    }


    }

