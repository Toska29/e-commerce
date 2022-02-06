package com.phoenix;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PhoenixApplicationTest {

    @Value("${test.property.name}") //spring expression language
    private String testName;

    @Autowired
    private DataSource dataSource;

    @Test
    void valueExists(){
        assertThat(testName).isEqualTo("phoenix");
        log.info(testName);
    }

    @Test
    void applicationCanConnectToDatabaseTest(){
        assertThat(dataSource).isNotNull();
        Connection connection;
        try{
              connection = dataSource.getConnection();
              assertThat(connection).isNotNull();
              log.info("Connection -> {}", connection.getSchema());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}