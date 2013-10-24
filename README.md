Java Common Libraries Extensions / I/O Abstraction
======================================================

Routines to load mostly text files into a tabular
representation. Supports CSV and Excel files.

Basically it covers a table (the data), a sheet that
gives a table a name, and a book which contains
multiple sheets that belong together.

An Excel file holds all this in a single file, using
CSV files you would probably compare a book to a folder,
a sheet with the corresponding file name, and the table
is the data contained in the file.

This library actually builds on Apache Commons IO.
