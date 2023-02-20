package com.example.rod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTest {


    @Test
    @DisplayName("JDBC 테이블 생성 test")
    void jdbcTest() throws SQLException {
        // docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER='user' -e POSTGRES_DB=messenger --name postgres_boot -d postgres
        // docker exec -i -t postgres_boot bash
        // su - postgres
        // psql --username teasun --dbname messenger

        String url2 = "jdbc:postgresql://localhost:5432/messenger";
        String url = "jdbc:h2://localhost:8080";
        String username = "byeongdoo";
        String password = "pass";

        try(Connection connection = DriverManager.getConnection(url, username, password)) {
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
    }
}
