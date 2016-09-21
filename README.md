
# BugTrckr
[![Build Status](https://travis-ci.org/IF-066-Java/bugTrckr.svg?branch=dev)](https://travis-ci.org/IF-066-Java/bugTrckr)
***

## Running project locally:

1. clone the project;
2. open project in IDE;
3. in the file *AgileSoftware/src/main/resources/application.properties* change properties (*jdbc.username* and *jdbc.password*) to your own;
4. in the file *AgileSoftware/src/main/resources/sql_maven_plugin.properties* change all properties to false;
5. run maven goal `mvn clean package` for execute database-scripts and create war-package;
6. run the project in the local server (for example *Tomcat*)
    In the Database will be user with admin rights, e-mail: admin@ss.com, and password: admin
7. (optional) for inserting images
    7. run MySQL terminal
         **mysql -u root -p**
    7. Check where is a directory configured by MySQL to work with files
        **SHOW VARIABLES LIKE "secure_file_priv";**
    7. Copy images from project folder to this directory, for example
        **\\! sudo cp src/main/resources/dbscripts/img/*.jpg   /var/lib/mysql-files/**
    7. start using bugtrckr database
       **use bugtrckr;**
    7. check if you can execute insersion images into database
         **select hex(LOAD_FILE('/var/lib/mysql-files/large.jpg'));**
      The result will be shown data in console followed by  "_1 row in set (0,04 sec)_"
      
#####      For Windows users
      Please, use different string for file path in sql file in LOAD_FILE function, for example
      '\\var\\lib\\mysql-files\\martin-schoeller-bill.jpg'



## Troubleshooting

#### Web server

   In case a Tomcat will give you a warning like org.apache.catalina.webresources.Cache.getResource Unable to add the resource ... to the cache because there was insufficient free space

   Please, In your *$CATALINA_BASE/conf/context.xml* add block before `</Context>`

   `<Resources cachingAllowed="true" cacheMaxSize="100000">`
   
#### Database

In case you can not load images by LOAD_FILE function you may do

1. Change owner of the files
    **\\! sudo chown -R mysql:mysql  /var/lib/mysql-files**
   
2. disable secure-file-priv. Example for Windows 7
    2. Stop the MySQL server service by going into services.msc.
    2. Go to C:\ProgramData\MySQL\MySQL Server 5.6 (ProgramData was a hidden folder in my case).
    2. Open the my.ini file in Notepad.
    2. Search for 'secure-file-priv'.
    2. Comment the line out by adding '#' at the start of the line.
    2. Save the file.
    2. Start the MySQL server service by going into services.msc
 
# Style Guide
This guide highlights the most important and most common conventions for writing code.

### Formatting
* Four spaces should be used as the unit of indentation (no tabs)
* Don't write lines longer than 120 characters.
* Avoid write methods longer than 30 lines.
* Don't write classes that are longer than 150 lines (not including comments).
* Use empty line between method definitions.
* Use ampty line between the local variables in a method and its first statement
* Don't use extra empty lines at the end and beginning of class/method definitions.
* Don't include spaces before () or [] when writing or calling methods.
* Don't include a newline before {. Don't use C# style.
* Use spaces around operators, except unary operators, such as !.

### Naming
* Use lowerCamelCase for variable/method names and UpperCamelCase for class/interface names.
* Prefer longer clearer names and not shorter unclear names.
* Prefer code that's clear and easy to understand and not unclear code that requires lots of comments.
* Constants should be all uppercase and with underscores, for wxample: MY_CONSTANT.

### Organization

The order of things inside classes should be:

1. Static variables
2. Instance variables
3. Constructors
4. Static methods
5. Instance methods
6. Private static methods
7. Private instance methods

### Comments
* Write high level comments above classes or methods explaining what the code does, not how it does it. 
* Use Javadoc comments with tags: @return, @param, @see. (http://www.oracle.com/technetwork/articles/java/index-137868.html)
* Avoid write single line comments. 

### Structure of project
Basic project structure:

![structure](https://s10.postimg.org/ucgmwn2hl/Screenshot_from_2016_08_11_16_22_20.png)

_Directories:_

`src` directory must contain all of the source material for building the project.

`src/main/java` directory must contain package hierarchy with Java-code.

`src/main/resources` directory must contain application/library resourcs (such as properties for database or logger config-file).

`src/main/resources/dbscripts` directory must contain database scripts.

`src/main/webapp` directory must contain files directly connected with web application.

`src/main/webapp/WEB-INF` directory must contain resources that are accessible to the resource loader of Web-Application.

`src/main/webapp/WEB-INF/resources` directory must contain only static resources needed for web-application (such as css, js, images).

`src/main/webapp/WEB-INF/layouts` directory must contain Apache Tiles templates.

`src/main/webapp/WEB-INF/views` directory must contain jsp-pages.

`src/test` directory must contain the test source code. 

`target` directory is created by Maven. It contains all the compiled classes, JAR files etc. 

_Packages:_

`com.softserverinc.edu.configs` package must contain config-classes (for example for database or for Spring MVC).

`com.softserverinc.edu.controllers` package must contain web-controllers.

`com.softserverinc.edu.entities` package must contain classes-entities for mapping to database tables.

`com.softserverinc.edu.forms` package must contain classes for validating form input.

`com.softserverinc.edu.repositories` package must contain Spring Data repositories.

`com.softserverinc.edu.services` package must contain interfaces for service-layer (and inside in package `impl` classes-implementations).

## JPA (Hibernate)
* Call the entity classes like tables in the database (otherwise you must add @Table annotation).
* Call the fields in entity classes like columns in the tables (otherwise you must add such code: @Column(name = ...)).
* Don't write big constructors in entity classes (but don't forget to write default constructor).
* Avoid the redundant code in the JPA annotations. For example don't add fetch type "EAGER" to annotation @ManyToOne, because it's default type. Always try to keep source as less as possible. 

## SQL

### General
* Use consistent and descriptive identifiers and names.
* Ensure the name is unique and does not exist as a reserved keyword.
* Named objects should not be surrounded by backticks. If you need to use backticks because of something in your table name, rename your table.
* Keep code succinct and devoid of redundant SQL—such as unnecessary quoting or parentheses or WHERE clauses that can otherwise be derived.
* Include comments in SQL code where necessary. Use the C style opening /* and closing */ where possible otherwise preceed comments with -- and finish them with a new line.

### Tables
* Use singular names for tables.
* Never give a table the same name as one of its columns and vice versa.
* Tables must have at least one key to be complete and useful.
* For FOREIGN KEY constraints use such convention: `[referencing table name]_[referenced table name]_fk`

### Columns
* Use the singular name.
* Use CamelCase for names if it's necessary (it will be easy to support Java code).

### Scripts
* Always use uppercase for the reserved keywords like SELECT and WHERE.
* Include in scripts newlines/vertical space for better understanding of code. Newlines should be used for any query that is at all complex or longer than 72 characters.
* Never use `*` operator for queries.

## Git (Github)
#### How to commit:

1. Before making commits switch to `dev` branch and do `git pull origin dev` to load the latest commits from central repository.
2. Create your own branch for feature by command `git checkout -b myfeature`.
3. Make commits.
4. After finishing merge your branch `myfeature` to `dev` branch. For this switch to `dev` branch and then do `git merge --no-ff myfeature`.
5. Push your local branch `dev` to central repository by command `git push origin dev`.

#### General rules:
* Don`t do pull requests (except you want to discuss your commits with everybody in the team or you want to get the feedback to the code).
* Commit only completed functional (don`t commit minor changes).
* Review code of other team members on Github, comment it and give suggestions how to solve problems.
* If someone added comment on code review you need to answer to this comment (are you agree or not).
* If you agree with comment fix it in next commit.

#### Commit messages:
* Write descriptive and understandable messages for commits.
* Use imperative, present tense in messages: "change", "add", "fix", not "changeds", “added”, "fixed".
* Don't capitalize first letter in messages.
* No dot (.) at the end of message.
* Use such style for writing messages: `what to do + for what entity + details(optional)`.
For example: `add ui-bootstrap.js dependency` or `add couple of missing semi colons`
