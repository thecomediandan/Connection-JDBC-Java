package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// El patron DAO es un objeto encargado de conectar con la base de datos directamente
public class DominioDAO {
    public void listar() {
        try {
            Statement statement = new ConnectionFactory().recuperarConexion().createStatement();
            statement.execute("SELECT * FROM movies;"); // Como execute retorna un boolean debido que existen datos este retorna true osea hay un set de resultados
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("rate") + "=>" + resultSet.getString("title"));
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
