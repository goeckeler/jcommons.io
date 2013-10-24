package org.jcommons.io.sheet;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import org.jcommons.io.data.DataProvider;
import org.jcommons.io.text.Table;

/**
 * A sheet names a table and provides means to convert the string data into typed data
 *
 * Think of it as an Excel tab in an Excel file that contains tabular data only. As the table is currently read-only,
 * the sheet is currently read-only.
 *
 * @author Thorsten Goeckeler
 */
public class Sheet
{
  private String name;
  private Table table;
  private DataProvider dataProvider;

  /** @return the name of this sheet, never null */
  public String getName() {
    return defaultString(name);
  }

  /**
   * Define a sensible name to this sheet, should be either the file name or the tab name
   *
   * @param name the name to identify this sheet by
   * @return this to allow chaining
   */
  public Sheet setName(final String name) {
    this.name = name;
    return this;
  }

  /** @return the underlying table of this sheet */
  public Table getTable() {
    return table;
  }

  /**
   * Assign the given table to this sheet
   *
   * @param table the tabular data that this sheet references
   * @return this to allow chaining
   */
  public Sheet setTable(final Table table) {
    this.table = table;
    return this;
  }

  /** @return the current data provider to convert the table data into objects */
  public DataProvider getDataProvider() {
    return dataProvider;
  }

  /**
   * Define the data provider that can convert string objects into real objects.
   *
   * @param dataProvider the dataProvider to use to convert string values
   */
  public void setDataProvider(final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (other == null) return false;
    if (!this.getClass().isAssignableFrom(other.getClass())) return false;

    Sheet that = (Sheet) other;

    // very lazy, but as the sheet name indicates the unique table name should do the trick
    boolean equals = equalsIgnoreCase(this.getName(), that.getName());
    // should actually test for table as well
    return equals;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
