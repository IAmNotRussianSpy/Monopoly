package Exceptions;

public class NotEnoughHousesException extends Exception {
    public NotEnoughHousesException() {
        super(" You don't have enough houses to build a hotel here ");
    }
}
