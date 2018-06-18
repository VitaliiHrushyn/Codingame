import java.util.List;

import Player10.Cell;

public class Parts {
	
	static void fiiCellList(int i, String row, List<Cell> cellList) { // fills cellList row by row for every call
		
		int level;
		char[] rowChars = row.toCharArray();					
		for (int j = 0; j < rowChars.length; j++) {
			if (rowChars[j] == '.') {
				level = -1;
			} else {
				level = Integer.parseInt(""+rowChars[j]);
			}						
			cellList.add(new Cell(j, i, level));
		}
	}

}
