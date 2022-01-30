package logic.dao.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import logic.exception.DAOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class DAOImpl implements logic.dao.DAO {
    Connection connection;

    @Override
    public void connect() throws DAOException {
        try {
            JSONObject config = new JSONObject(new JSONTokener(
                    Objects.requireNonNull(this.getClass().getResourceAsStream("config.json"))));

            String url = config.getString("db_url");

            // create a connection to the database
            connection = DriverManager.getConnection(url);
        } catch (JSONException e) {
            throw new DAOException("DAO failed at reading config.json file.");
        } catch (SQLException e) {
            throw new DAOException("DAO failed connecting to db.");
        }
    }

    @Override
    public void disconnect() throws DAOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("DAO failed disconnecting from db");
        }
    }
}
