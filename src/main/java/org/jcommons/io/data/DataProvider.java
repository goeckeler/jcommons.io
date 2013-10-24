package org.jcommons.io.data;

import java.util.List;
import java.util.Map;

import org.jcommons.message.Message;

/**
 * A converter between plain tabular data and the respective object data.
 *
 * @author Thorsten Goeckeler
 */
public interface DataProvider
{
  /**
   * Define the name of the table for which this data conversion takes place.
   *
   * @param tableName the name of the table tha contains all columns, never <code>null</code>
   */
  void setTable(String tableName);

  /** @return the current table name, never <code>null</code> */
  String getTable();

  /**
   * Define the column names.
   *
   * @param columns the names of the respective columns, never <code>null</code>
   */
  void setHeaders(String[] columns);

  /** @return the current columns names, never <code>null</code> */
  String[] getHeaders();

  /**
   * Set a plain value for the given column name.
   *
   * @param column the column name for which this value applies, never <code>null</code>
   * @param value the corresponding value, can be <code>null</code>
   */
  void setValue(String column, String value);

  /**
   * Set a plain value for the given column index.
   *
   * @param index the index of the column for which this value applies, invalid indices are ignored
   * @param value the corresponding value, can be <code>null</code>
   */
  void setValueAt(int index, String value);

  /**
   * Set plain values for all columns, sequence must be the same order as the headers.
   *
   * @param values the plain values for the respective columns, <code>null</code> resets all values
   */
  void setValues(String[] values);

  /**
   * Set plain values for all columns, sequence must be the same order as the headers.
   *
   * @param values the plain values for the respective columns, <code>null</code> resets all values
   */
  void setValues(List<String> values);

  /**
   * Set values matching the given map of values.
   *
   * The map represents <code>(column, value)</code> bindings. Will replace all values for known columns, will ignore
   * unknown columns, and will leave values for all other columns as they are. If you want to override other existing
   * column values you must call <code>clear()</code> first.
   *
   * @param values the mapping of columns and their respective values, will be ignored if <code>null</code>
   */
  void setValues(Map<String, String> values);

  /** resets all values to <code>null</code> but not the columns */
  void clear();

  /**
   * Convert the current value to its object representation.
   *
   * @param column the column name whose value we want to convert, never <code>null</code>
   * @return the respective object value, can be <code>null</code>
   */
  Object getValue(String column);

  /**
   * Convert the current value to its object representation.
   *
   * @param index the index of the column whose value we want to convert, never negative
   * @return the respective object value, can be <code>null</code>
   */
  Object getValueAt(int index);

  /** @return the object values for all current values in the sequence of the column names */
  Object[] getValues();

  /**
   * Validates if the data or which data could be converted.
   *
   * @return the list of error messages, should contain no faults for a successful conversion
   */
  Message validate();

  /**
   * Validates if the columns can be converted at all that is if the table columns can be mapped to the data columns.
   *
   * @return the list of error messages, should be empty to start conversion
   */
  Message validateTable();
}
