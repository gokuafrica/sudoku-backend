package com.sudoku.solver.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudoku.solver.service.SolverService;

import payload.Input;


@RestController
@RequestMapping("/api")
public class MainController {
	
	SolverService solverService;
	
	@Autowired
	ApplicationContext context;
	
	@PostMapping("/solve")
    public Input authenticateUser(@RequestBody Input input) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<int[]> future = executor.submit(getSolverService(input.getBoard()));
		try {
			input.setBoard(future.get(15, TimeUnit.SECONDS));
		}
		catch(Exception e) {
			future.cancel(true);
		}
		finally {
			executor.shutdown();
		}
		return input;
    }
	
	
	public SolverService getSolverService(int[] input) {
		return context.getBean(SolverService.class, input);
	}

	public void setSolverService(SolverService solverService) {
		this.solverService = solverService;
	}
	
}
