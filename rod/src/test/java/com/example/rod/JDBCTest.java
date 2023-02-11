package com.example.rod;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTest {


    @Test
    void jdbcTest() throws SQLException {
//        String url = "jdbc:mysql://localhost:4321/mysql-container";
//        String username = "root";
//        String password = "pass";

        // docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=teasun -e POSTGRES_DB=messenger --name postgres_boot -d postgres

        // docker run -p 4321:4321 -e MYSQL_USER=byeongdoo -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_PASSWORD=pass --name mysql-container -e MYSQL_DATABASE=messenger -d mysql
        //docker exec -it mysql-container bash

        // docker exec -i -t postgres_boot bash
        // su - postgres
        // psql --username teasun --dbname messenger
        String url = "jdbc:mysql://localhost:4321/messenger?useSSL=false";
        try(Connection connection = DriverManager.getConnection(url, "byeongdoo", "pass")) {

//            Class.forName("com.mysql.cj.jdbc.Driver");

//            System.out.println("연결 성공");
//        } catch (ClassNotFoundException e){
//            System.out.println("로딩실패");
          try{
                String createSql = "CREATE TABLE ACCOUNT (id SERIAL PRIMARY KEY, username varchar(255), password varchar(255))";
                try(PreparedStatement statment = connection.prepareStatement(createSql)){
                    statment.execute();
                }
            } catch (SQLException e){
                if (e.getMessage().equals("ERROR: relation \"account\" already exists")) {
                    System.out.println("ACCOUNT 테이블이 이미 존재합니다.");
                } else {
                    throw new RuntimeException();
                }
            }
        }

        // when
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            try {
//                String creatSql = "CREATE TABLE ACCOUNT (id SERIAL PRIMARY KEY, username varchar(255), password varchar(255))";
//                try (PreparedStatement statement = connection.prepareStatement(creatSql)) {
//                    statement.execute();
//                }
//            } catch (SQLException e) {
//                if (e.getMessage().equals("ERROR: relation \"account\" already exists")) {
//                    System.out.println("ACCOUNT 테이블이 이미 존재합니다.");
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//        }
    }
}
