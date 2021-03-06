package com.sudoku.solver.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import generic.Pair;

@Service
@Scope("prototype")
public class SolverService implements Callable<int []>{
	
	private int[] input;
	
	public SolverService(int[] input) {
		this.input = input;
	}
	
	private static int determineStart(int pos){
	    if(pos>=6)
            return 6;
        else if(pos>=3)
            return 3;
        else
            return 0;
	}
	private static List<Integer> getPossibleValues(Pair<Integer,Integer> coordinate, int[][] sudoku){
        HashSet<Integer> allPossible = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        for(int i=0;i<9;++i)
                allPossible.remove(sudoku[coordinate.x][i]);
        for(int i=0;i<9;++i)
                allPossible.remove(sudoku[i][coordinate.y]);
        int xstart = determineStart(coordinate.x), ystart = determineStart(coordinate.y);
        for(int i=xstart;i<xstart+3;++i){
            for(int j=ystart;j<ystart+3;++j){
                allPossible.remove(sudoku[i][j]);
            }
        }
        ArrayList<Integer> result = new ArrayList<>(9);
        result.addAll(allPossible);
        return result;
	}
	private static boolean recur(int[][] sudoku, int index, ArrayList<Pair<Integer,Integer>> zeroes) throws InterruptedException{
		if(Thread.currentThread().isInterrupted())
			throw new InterruptedException();
        if(index==zeroes.size())
            return true;
        Pair<Integer,Integer> coordinate = zeroes.get(index);
        List<Integer> possibleValues = getPossibleValues(coordinate,sudoku);
        if(possibleValues.size()==0)
            return false;
        boolean bool=false;
        for(Integer ele: possibleValues){
            sudoku[coordinate.x][coordinate.y]=ele;
            if(!recur(sudoku,index+1,zeroes))
                sudoku[coordinate.x][coordinate.y]=0;
            else{
                bool=true;
                break;
            }
        }
        return bool;
	}
    private static void solver(int[][] sudoku) throws InterruptedException{
        ArrayList<Pair<Integer,Integer>> zeroes = new ArrayList<>(81);
        for(int i=0;i<9;++i)
                for(int j=0;j<9;++j)
                    if(sudoku[i][j]==0)
                        zeroes.add(new Pair<>(i,j));
        
        recur(sudoku,0,zeroes);	
    }
	
	@Override
	public int[] call() throws Exception {
		int[][] trueInput = new int[9][9];
		for(int i=0,c=0;i<9;++i)
			for(int j=0;j<9;++j)
				trueInput[i][j] = input[c++];
		try {
			solver(trueInput);			
			int[] result = new int[81];
			for(int i=0,c=0;i<9;++i)
				for(int j=0;j<9;++j)
					result[c++]=trueInput[i][j];
			return result;
		}
		catch(InterruptedException e) {
			return input;
		}
	}
}
