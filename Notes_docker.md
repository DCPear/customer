##### Installing MySQL database using docker
1. Install Docker
      ```
   $ docker --version
     Docker version 20.10.2, build 2291f61
   ```
2. Install the MySQL Docker image.
   ```
   docker run  --detach   --name customer-mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=customer -e MYSQL_USER=deepa -e MYSQL_PASSWORD=mypassword -d mysql
   ```
   ```
   1. Download mysql locally from hub.docker.com 
   2. Create and run a container 
   3. Name the container customer-mysql
   4. Set the root password to password
   5. Create a database called customer
   6. Set the username and password to the database as deepa and mypassword, respectively
   7. Publish the MySQL 3306 port to the localhost 3307 port.
   ```
   ```
   Now we have mysql image running in its own Linux operating system.
   $ docker images
   REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
   mysql        latest    c8562eaf9d81   32 hours ago   546MB

   in a running container
   $ docker ps -a
   CONTAINER ID   IMAGE     COMMAND                  CREATED          STATUS          PORTS                               NAMES
   04b6e3ae804f   mysql     "docker-entrypoint.s…"   30 minutes ago   Up 30 minutes   33060/tcp, 0.0.0.0:3307->3306/tcp   customer-mysql
   ```
3. Run the MySQL Docker container
4. Have the application integrate with that MySQL container. 
   ```
    update pom.xml with maven dependency
      <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
     </dependency>
    ```
    ```
    Update application.properties
    #Connection parameters to the mysql database
      spring.datasource.url=jdbc:mysql://localhost:3307/customer
      spring.datasource.username=deepa
      spring.datasource.password=mypassword
   #Needed for non-h2 datasource
      spring.datasource.initialization-mode=always
   #Continue running schema.sql even if tables already exists
      spring.datasource.continue-on-error=true
    ```
    ```
    docker run -it --link customer-mysql:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
    ```
    ```
      PS D:\projects_java\Microservices\customer> docker run  --detach   --name customer-mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=customer -e MYSQL_USER=deepa -e MYSQL_PASSWORD=mypassword -d mysql
      882f7abdf0d6e4fead9225f44b97c44dc42f79023c623d25116d5c4fe352db5a
      PS D:\projects_java\Microservices\customer> docker run -it --link customer-mysql:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
      mysql: [Warning] Using a password on the command line interface can be insecure.
      Welcome to the MySQL monitor.  Commands end with ; or \g.
      Your MySQL connection id is 8
      Server version: 8.0.23 MySQL Community Server - GPL

      Copyright (c) 2000, 2021, Oracle and/or its affiliates.

      Oracle is a registered trademark of Oracle Corporation and/or its
      affiliates. Other names may be trademarks of their respective
      owners.

      Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

      mysql> show databases;
      +--------------------+
      | Database           |
      +--------------------+
      | customer           |
      | information_schema |
      | mysql              |
      | performance_schema |
      | sys                |
      +--------------------+
      5 rows in set (0.00 sec)
      mysql>
      mysql> use customer
        Database changed
      mysql> show tables;
        Empty set (0.00 sec)
  
5. Launch application and check data
   ```
   mysql> show tables;
   +--------------------+
   | Tables_in_customer |
   +--------------------+
   | customer           |
   | security_role      |
   | security_user      |
   | user_role          |
   +--------------------+
   4 rows in set (0.00 sec)
   mysql> select * from security_role;
   +----+---------------------------------+------------+
   | id | description                     | role_name  |
   +----+---------------------------------+------------+
   |  1 | Administrator                   | ROLE_ADMIN |
   |  2 | Customer Service Representative | ROLE_CSR   |
   +----+---------------------------------+------------+
   2 rows in set (0.00 sec)

   mysql> select * from customer;
   +----+------------+-----------+------------+-----------+---------+------------+--------+
   | id | first_Name | last_Name | dob        | email     | phone   | address    | level  |
   +----+------------+-----------+------------+-----------+---------+------------+--------+
   |  1 | John       | Smith     | 1965-09-08 | me@me.com | 5678499 | 34 Tasman  | Gold   |
   |  2 | Jane       | Doe       | 1974-06-09 | me@me.com | 5678567 | 34 my road | Silver |
   |  3 | John       | Smith     | 1965-09-08 | me@me.com | 5678499 | 34 Tasman  | Gold   |
   |  4 | Jane       | Doe       | 1974-06-09 | me@me.com | 5678567 | 34 my road | Silver |
   +----+------------+-----------+------------+-----------+---------+------------+--------+
   4 rows in set (0.01 sec)
   ```
6. Remove container and leave the image
   ```
   mysql> quit
   Bye
   PS D:\projects_java\Microservices\customer> docker stop customer-mysql
   customer-mysql
   PS D:\projects_java\Microservices\customer> docker rm customer-mysql
   customer-mysql
   ```
7. Using Flyway data migration tool
   ```   
   that controls triggering of version database migration scripts.
   
   update pom with maven dependency
   <dependency>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-core</artifactId>
   </dependency>











##### Reading:
```
https://severalnines.com/database-blog/mysql-docker-building-container-image
https://techexpert.tips/mysql/mysql-docker-installation/
https://towardsdatascience.com/how-to-run-mysql-using-docker-ed4cebcd90e4
```