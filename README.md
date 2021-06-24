<div>
<img alt="CircleCI" src="https://img.shields.io/circleci/build/github/jerrylasama/perpustakaanku/main?token=7ec0ded0d34af77c35df84c5a6a03fd751940f0c">
<img alt="Java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=flat&logo=java&logoColor=white"/>
</div>

# Perpustakaanku
A Library in A Terminal

## Getting Started

1. Create a database named perpustakanku

```mysql
CREATE DATABASE perpustakaan
```

2. Import the tables from db/perpustakaan.sql

```bash
mysql -h hostname -u user database < path/to/perpustakaan.sql
```

3. Open the project file using IntelliJ, and set Main Class to 

```
view.MainActivity
```

4. Make sure the database configuration is the same as your machine configuration, it's located at model.DatabaseConnection. For example:

```java
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/perpustakaan";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection con;
```

5. Build and Run the project

