# play-scala-crud
CRUD api using Play Scala Slick

## Setup
Clone repo 

```
$ git clone https://github.com/pankajtumpala/play-scala-crud.git
```

Install sbt

```https://www.scala-sbt.org/download.html```

Update database schema url

```
slick.dbs.default.profile = "slick.jdbc.MySQLProfile$"
slick.dbs.default.db.driver = "com.mysql.cj.jdbc.Driver"
slick.dbs.default.db.url = "jdbc:mysql://localhost/scalacrud?serverTimezone=UTC"
slick.dbs.default.db.user = "root"
slick.dbs.default.db.password = ""
```

## Create schema
Create schema in mysql

```CREATE SCHEMA `scalacrud` ;```

## Migration
Runs automatically on first api access for both test and run

## Running app

```
$ cd play-scala-crud
$ sbt run
```



## API documentation
Swagger

http://localhost:9000/docs/swagger-ui/index.html?url=/assets/swagger.jso

Postman

https://documenter.getpostman.com/view/8337911/SzYbycrp?version=latest


