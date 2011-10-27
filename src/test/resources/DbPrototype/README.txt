Unit tests are using embedded h2database. This folder should contain a reference h2database file with a schema matching prod db and seed data for DatabaseExportUtil class to be able to generate a DbUnit data set to be used in data access unit tests.
If the web app will be using the same seed data, the h2database files here should be kept in sync with files under src/main/webapp/WEB-INF/db/h2


protoTypeDbConnection-Context.xml file is used to provide a spring configured data source bean that can be utilized by utility classes such as DatabaseExportUtil that is going to access the proto-type db to extract schema information.