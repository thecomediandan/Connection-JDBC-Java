package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://192.168.101.2:3306/myfavoritemovies?useTimeZone=true&serverTimeZone=UTC","laptop-windows","bender868");
        listar(c);
        insertar(c);
        eliminar(1,c);
        c.close();
    }

    // Manejo de transacciones
    public static Connection conexionManejable()  {
        // Manejo de try-catch-resource para cerrar automaticamente las conexiones, sirve para cerrar tambien los ResultSet, Statement y PreparedStatement tambien
        // aunque este falle o no este recurso se cerrara al finalizar el try-catch-resource
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://192.168.101.2:3306/myfavoritemovies?useTimeZone=true&serverTimeZone=UTC", "laptop-windows", "bender868");
            try (PreparedStatement statement = c.prepareStatement("SELECT * FROM movies;")) {
                c.setAutoCommit(false); // Con esto indicamos que no queremos que el guardado automatico a la base de datos se aplique
                statement.execute();

                System.out.println("Conexion establecida.");
                // TODO: Realizamos las tareas con la conexion.
                c.commit(); // Guardamos los cambios relizados durante la conexion
                return c;
            } catch (SQLException e) {
                c.rollback(); // Restauramos a los valores antes de haber realizado cambios
                throw new RuntimeException(e);
            }
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public static void listar(Connection c) throws SQLException {
        Statement statement = c.createStatement();
        statement.execute("SELECT * FROM movies;"); // Como execute retorna un boolean debido que existen datos este retorna true osea hay un set de resultados
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("rate") + "=>" + resultSet.getString("title"));
        }
    }
    public static void insertar(Connection c) throws SQLException {
        Statement statement = c.createStatement();
        // Para poder mostrar el dato insertado utililizamos el segundo parametro de execute para indicar que capture los datos autogenerados, es decir la clave primaria es un auto increment normalmente y podemos utilizar ese efecto
        statement.execute("INSERT INTO movies(title, rate) VALUES('Joker', 9);", Statement.RETURN_GENERATED_KEYS); // A difernecia de listar, en este caso retorna false porque no hay ningun dato de retorno es decir no hay un set de resultados
        // Obtenemos los datos con claves generadas
        ResultSet resultSet = statement.getGeneratedKeys();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("rate") + "=>" + resultSet.getString("title"));
        }
    }

    // Este insert es para evitar las SQL Injection, se puede aplicar a cualquier operacion de CRUD
    public static void insertarSeguro(Connection c) throws SQLException {
        //Statement statement = c.createStatement();
        // Con prepareStatement preparamos una query para evitar inyecciones
        // Tambien podemos capturar la tupla insertada
        // Con los ? y los sets de statement podemos insertar los datos en la query
        PreparedStatement statement = c.prepareStatement("INSERT INTO movies(title, rate) VALUES(?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, "Joker");
        statement.setInt(2, 9);
        // Para poder mostrar el dato insertado utililizamos el segundo parametro de execute para indicar que capture los datos autogenerados, es decir la clave primaria es un auto increment normalmente y podemos utilizar ese efecto
        statement.execute(); // A difernecia de listar, en este caso retorna false porque no hay ningun dato de retorno es decir no hay un set de resultados
        // Obtenemos los datos con claves generadas
        ResultSet resultSet = statement.getGeneratedKeys();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("rate") + "=>" + resultSet.getString("title"));
        }
    }
    public static void eliminar(int id, Connection c) throws SQLException {
        Statement statement = c.createStatement();
        statement.execute("DELETE FROM movies WHERE id_movie = "+ id +";");
        System.out.printf("Se eliminaron %d fila(s).", statement.getUpdateCount());
    }
    public static void actualizar(int id, String title, int rate, Connection c) throws SQLException{
        Statement statement = c.createStatement();
        statement.execute("UPDATE movies SET title = '" + title + "', rate = '" + rate + "' WHERE id_movie = '"+ id +"';");
        System.out.printf("Se actualizaron %d fila(s).", statement.getUpdateCount());
    }
}