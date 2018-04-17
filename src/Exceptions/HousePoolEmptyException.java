package Exceptions;

public class HousePoolEmptyException extends Exception {
    public HousePoolEmptyException() { super(" There are no more houses in the bank. \n Wait until spare one will become available ");}
}
