This project intends to make the use of Database Meta Data easy in java.
It reads the database structure from DatabaseMetaData jdbc interface and create a simple model with three classes: Table, Field and Relation.
With this classes it´s easy to iterate in the fields of the table and, for example, verify if it is not null, or if it is a primary key, etc.
It´s the ideal framework for projects that are based on metadata, such as generators systems that read the database to create classes.