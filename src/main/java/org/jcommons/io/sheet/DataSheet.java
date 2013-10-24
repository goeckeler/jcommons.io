package org.jcommons.io.sheet;

import java.util.List;

/**
 * A sheet that provides access to the data objects rather than just the plain data underlying a sheet.
 *
 * @author Thorsten Goeckeler
 */
public interface DataSheet
{
  /** @return the names of the columns in this table or an empty list if no columns have been defined, never null */
  List<String> getColumns();

  /** @return the names of the columns in this table or an empty array if no columns have been defined, never null */
  String[] getHeaders();

  /**
   * Determine the name of a column if the position is known.
   *
   * @param index the position in the header starting with 0
   * @return the column name at the given position, null if unknown
   */
  String getColumn(int index);

  /**
   * Determine the index of a column if the name is known.
   *
   * @param column the case insensitive name of the respective column
   * @return the index of the column in the headings, -1 if the column is unknown
   */
  int indexOf(String column);

  /**
   * Access the given row.
   *
   * @param index the row number, the first row is 0
   * @return the data of the row or an empty array if the row is out of bounds, never null
   */
  Object[] getDataRow(final int index);

  /**
   * Access all rows.
   *
   * @return the data of all rows or an empty array no row exist, never null
   */
  Object[][] getDataRows();

  /**
   * Retrieve the value of the given row and column.
   *
   * If the row is out of bounds or the column is unknown will simply return <code>null</code> to indicate that no value
   * has been set.
   *
   * @param column the column name in the table
   * @param row the row number in the table starting with 0
   * @return the associated value of that cell, can be null
   */
  Object getDataValue(String column, int row);

  /**
   * Retrieve the value of the given row and column.
   *
   * If row or column are out of bounds will simply return <code>null</code> to indicate that no value has been set.
   *
   * @param row the row number in the table starting with 0
   * @param column the column number in the table starting with 0
   * @return the associated value of that cell, can be null
   */
  Object getDataValue(final int row, final int column);

  /** @return the number of current rows of this table */
  int size();
}
