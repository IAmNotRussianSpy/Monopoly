package Exceptions;

public class HotelPoolEmptyException extends Exception {
    public HotelPoolEmptyException() {super(" There is no available hotel at the moment \n Wait until one will become available ");
    }
}
