package Exceptions;

public class HotelAlreadyBuiltException extends Exception {
    public HotelAlreadyBuiltException() {
        super(" You already have a hotel here ");
    }
}
