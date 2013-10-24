package org.jcommons.io.text;

import static org.jcommons.io.text.GridFactory.createComplexGrid;
import static org.jcommons.io.text.GridFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.*;

/** Check that default spreadsheet implementation for a table holds the contract */
public class SpreadsheetTest
{
  private Spreadsheet sheet = null;

  /** check that we can populate a spreadsheet right away */
  @Test
  public void testSpreadsheetGrid() {
    sheet = createComplexSheet();
    assertNotNull(sheet);
    assertNotNull(sheet.getGrid());
    assertEquals(2, sheet.size());

    sheet.setGrid(null);
    assertEquals(0, sheet.size());
  }

  /** check that we can retrieve column names */
  @Test
  public void testGetColumn() {
    sheet = new Spreadsheet(createSingleRowGrid());
    assertEquals(1, sheet.size());
    assertEquals("column", sheet.getColumn(0));
    assertNull(sheet.getColumn(20));
    assertNull(sheet.getColumn(-20));
  }

  /** check if we can determine all columns */
  @Test
  public void testGetColumns() {
    sheet = new Spreadsheet();
    List<String> columns = sheet.setGrid(createColumnsOnlyGrid()).getColumns();
    assertNotNull(columns);
    assertEquals(1, columns.size());
    assertEquals("column", columns.get(0));

    columns = sheet.setGrid(createComplexGrid()).getColumns();
    assertNotNull(columns);
    assertEquals(1, columns.size());
    assertEquals("header", columns.get(0));

    columns = sheet.setSkipHeader(2).getColumns();
    assertNotNull(columns);
    assertEquals(1, columns.size());
    assertEquals("column", columns.get(0));

    columns = sheet.setGrid(createEmptyGrid()).getColumns();
    assertNotNull(columns);
    assertEquals(0, columns.size());
  }

  /** check that we can access the data directly */
  @Test
  public void testGetData() {
    sheet = new Spreadsheet();
    // start with indirect test
    assertEquals("[], []", sheet.toString());
    assertEquals("[], []", sheet.setGrid(createEmptyGrid()).toString());
    assertEquals("[column], []", sheet.setGrid(createColumnsOnlyGrid()).toString());
    assertEquals("[column], [[data]]", sheet.setGrid(createSingleRowGrid()).toString());

    // now access directly with valid data
    complexGridIn(sheet);
    List<List<String>> data = sheet.getData();
    assertNotNull(data);
    assertEquals(sheet.size(), data.size());
    assertEquals(2, data.size());
    assertNotNull(data.get(0));
    assertNotNull(data.get(1));
    assertEquals("data", data.get(0).get(0));
    assertEquals("moreData", data.get(1).get(0));

    // and now with invalid data
    sheet.setGrid(createSingleRowGrid()).setSkipHeader(2).setSkipFooter(1).setSkipTrailer(1);
    data = sheet.getData();
    assertNotNull(data);
    assertEquals(sheet.size(), data.size());
    assertEquals(0, data.size());
    sheet.setSkipHeader(0).setSkipFooter(0).setSkipTrailer(0);
    data = sheet.getData();
    assertNotNull(data);
    assertEquals(sheet.size(), data.size());
    assertEquals(1, data.size());
    assertNotNull(data.get(0));
    assertEquals("data", data.get(0).get(0));
  }

  /** check that determination of first and last data row works fail safe */
  @Test
  public void testGetDataRows() {
    sheet = new Spreadsheet();
    assertEquals(-1, sheet.setGrid(null).getDataRow());
    assertEquals(-1, sheet.getLastDataRow());
    assertEquals(1, sheet.setGrid(createSingleRowGrid()).getDataRow());
    assertEquals(1, sheet.getLastDataRow());
    assertEquals(1, sheet.setGrid(createComplexGrid()).getDataRow());
    assertEquals(6, sheet.getLastDataRow());
    assertEquals(-1, sheet.setSkipHeader(4).setSkipTrailer(4).setSkipFooter(4).getDataRow());
    assertEquals(-1, sheet.getLastDataRow());
  }

  /** check that we can access any row in a fail safe manner */
  @Test
  public void testGetRow() {
    sheet = new Spreadsheet();
    // no grid
    List<String> data = sheet.getRow(0);
    assertNotNull(data);
    assertTrue(data.isEmpty());

    // empty grid
    data = sheet.setGrid(createEmptyGrid()).getRow(0);
    assertNotNull(data);
    assertTrue(data.isEmpty());

    // out of bounds tests
    data = sheet.setGrid(createSingleRowGrid()).getRow(0);
    assertNotNull(data);
    assertEquals(1, data.size());
    assertEquals("data", data.get(0));

    data = sheet.getRow(-20);
    assertNotNull(data);
    assertTrue(data.isEmpty());

    data = sheet.getRow(20);
    assertNotNull(data);
    assertTrue(data.isEmpty());

    data = sheet.setSkipHeader(2).getRow(0);
    assertNotNull(data);
    assertTrue(data.isEmpty());

    // check that data is still okay
    data = sheet.setSkipHeader(0).getRow(0);
    assertNotNull(data);
    assertEquals(1, data.size());
    assertEquals("data", data.get(0));
  }

  /** check that we can access values individually */
  @Test
  public void testGetValue() {
    sheet = new Spreadsheet();
    // use positional access
    assertNull(sheet.getValue(0, 0));
    assertNull(sheet.setGrid(createEmptyGrid()).getValue(0, 0));
    assertEquals("data", complexGridIn(sheet).getValue(0, 0));
    assertEquals("moreData", sheet.getValue(1, 0));

    // column out of bounds
    assertNull(sheet.getValue(1, 20));
    assertNull(sheet.getValue(1, -20));

    // row out of bounds
    assertNull(sheet.getValue(20, 0));
    assertNull(sheet.getValue(-15, 0));

    // use named access
    assertEquals("data", sheet.getValue("column", 0));
    assertEquals("moreData", sheet.getValue("column", 1));
    assertNull(sheet.getValue(null, 0));
    assertNull(sheet.getValue("goggles", 0));
    assertNull(sheet.getValue("column", 20));
    assertNull(sheet.getValue("column", -10));
  }

  /** check that we can actually retrieve the column */
  @Test
  public void testIndexOf() {
    sheet = createComplexSheet();
    assertNotNull(sheet);
    assertNotNull(sheet.getGrid());
    assertEquals(2, sheet.size());

    assertEquals(0, sheet.indexOf("column"));
    assertEquals(0, sheet.indexOf("CoLuMn"));
    assertEquals(-1, sheet.indexOf("no.such.column"));
  }

  /** check that we can define the column names ourself */
  @Test
  public void testSetColumn() {
    sheet = new Spreadsheet(createEmptyGrid());
    // cannot name columns on empty grids
    assertNull(sheet.setColumn(0, "mycolumn").getColumn(0));
    // positive check
    assertEquals("mycolumn", sheet.setGrid(createColumnsOnlyGrid()).setColumn(0, "mycolumn").getColumn(0));
    assertEquals(0, sheet.indexOf("mycolumn"));
    assertEquals(-1, sheet.indexOf("column"));
    // out of bounds checks
    assertNull(sheet.setColumn(4, "mycolumn").getColumn(4));
    assertNull(sheet.setColumn(-2, "mycolumn").getColumn(-2));
  }

  /** check that size is computed correctly */
  @Test
  public void testSize() {
    sheet = new Spreadsheet();
    assertEquals(0, sheet.setGrid(createEmptyGrid()).size());
    assertEquals(0, sheet.setGrid(createColumnsOnlyGrid()).size());
    assertEquals(1, sheet.setGrid(createSingleRowGrid()).size());
    assertEquals(6, sheet.setGrid(createComplexGrid()).size());
    assertEquals(4, sheet.setSkipHeader(2).size());
    assertEquals(3, sheet.setSkipFooter(1).size());
    assertEquals(2, sheet.setSkipTrailer(1).size());
  }

  /** test that arguments are created correctly */
  @Test
  public void testParameters() {
    sheet = new Spreadsheet();
    Map<String, String> parameters = sheet.setGrid(createEmptyGrid()).getParameters();
    assertNotNull(parameters);
    assertFalse(parameters.isEmpty());
    assertEquals("0", parameters.get("header"));
    assertEquals("0", parameters.get("trailer"));
    assertEquals("0", parameters.get("footer"));

    parameters = complexGridIn(sheet).setSkipTrailer(3).getParameters();
    assertEquals("2", parameters.get("header"));
    assertEquals("3", parameters.get("trailer"));
    assertEquals("1", parameters.get("footer"));
    assertEquals("Spreadsheet", parameters.get("class"));
  }

  private Spreadsheet createComplexSheet() {
    return new Spreadsheet(createComplexGrid()).setSkipHeader(2).setSkipFooter(1).setSkipTrailer(1);
  }
  
  private Spreadsheet complexGridIn(final Spreadsheet sheet) {
    sheet.setGrid(createComplexGrid()).setSkipHeader(2).setSkipFooter(1).setSkipTrailer(1);
    return sheet;
  }
}
