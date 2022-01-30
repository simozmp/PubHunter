package logic.exception;

public class DBInconsistencyException extends DAOException {
    public DBInconsistencyException(String message) {
        super(message);
    }
}
