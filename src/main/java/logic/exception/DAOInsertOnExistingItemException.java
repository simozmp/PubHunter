package logic.exception;

public class DAOInsertOnExistingItemException extends DAOException {
    public DAOInsertOnExistingItemException(String message) {
        super(message);
    }
}
