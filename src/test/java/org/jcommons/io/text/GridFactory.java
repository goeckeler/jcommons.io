package org.jcommons.io.text;

import java.util.Arrays;

/**
 * Simple factory to provide predefined grids for the test cases
 * 
 * @author Thorsten Goeckeler
 */
public final class GridFactory
{
  private static final int DATA_ROW = 4;
  private static final int COLUMN_ROW = 2;
  
  // @formatter:off
  private static final String[][] GRID = {
    { "header"   },
    { "header"   }, 
    { "column"   },
    { "trailer"  },
    { "data"     },
    { "moreData" },
    { "footer"   }
  };
  // @formatter:on

  private GridFactory() {
  }

  /** @return an empty grid */
  public static Grid createEmptyGrid() {
    return new Grid();
  }
  
  /** @return a grid that contains only column headings */
  public static Grid createColumnsOnlyGrid() {
    Grid grid = new Grid();
    grid.add(Arrays.asList(GRID[COLUMN_ROW].clone()));
    return grid;
  }
  
  /** @return a grid that contains column headings and a single row */
  public static Grid createSingleRowGrid() {
    Grid grid = new Grid();
    grid.add(Arrays.asList(GRID[COLUMN_ROW].clone()));
    grid.add(Arrays.asList(GRID[DATA_ROW].clone()));
    return grid;
  }
  
  /** @return a grid that is contains all possible sections */
  public static Grid createComplexGrid() {
    Grid grid = new Grid();
    for (String[] row : GRID) {
      grid.add(Arrays.asList(row.clone()));
    }
    return grid;    
  }
}
