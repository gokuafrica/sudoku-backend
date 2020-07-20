package com.sudoku.solver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudoku.solver.service.SolverService;

import payload.Input;


@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	SolverService solverService;
	
	@PostMapping("/solve")
    public Input authenticateUser(@RequestBody Input input) {
		input.setBoard(solverService.solveForSudoku(input.getBoard()));
		return input;
    }
	
}
