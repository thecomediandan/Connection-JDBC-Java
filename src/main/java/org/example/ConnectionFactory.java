package org.example;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

// Se instalaron las librerias:
// com.mchange:c3p0:0.9.5.5, para el manejo del pool de conexiones
// com.mchange:mchange-commons-java:0.2.20,
public class ConnectionFactory {
    private DataSource dataSource;
    // Un DataSource es un objeto que contiene un pool de conexiones
    public ConnectionFactory() {
        // tambien podriamos usar DriverManager.getConnection(URL) importando el driver class.forName(nombre del driver)
        var pooledDataSource = new ComboPooledDataSource(); // c3p0 es un manejador de un pool de conexiones
        pooledDataSource.setJdbcUrl("jdbc:mysql://192.168.101.2:3306/myfavoritemovies?useTimeZone=true&serverTimeZone=UTC");
        pooledDataSource.setUser("laptop-windows");
        pooledDataSource.setPassword("bender868");

        pooledDataSource.setMaxPoolSize(10); // Configuramos la cantidad de conexiones habilitadas a la base de datos

        this.dataSource = pooledDataSource;
    }

    public Connection recuperarConexion() {
        try {
            return this.dataSource.getConnection();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
