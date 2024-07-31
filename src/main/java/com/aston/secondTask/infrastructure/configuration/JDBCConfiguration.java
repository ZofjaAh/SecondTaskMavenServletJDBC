package com.aston.secondTask.infrastructure.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class JDBCConfiguration {
    private static final HikariConfig config = new HikariConfig("datasource.properties");
    private static final HikariDataSource dataSource = new HikariDataSource(config);

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getHikariDataSource() {
        return dataSource;
    }


}
