import Exceptions.HotelPoolEmptyException;
import Exceptions.HousePoolEmptyException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bank extends Player {
    private int HousePool;
    private int HotelPool;
    private CityCard[] Mortgages;
    private int MaxHousePool = 32;   //dopisac liczbe domkow - zrobione
    private int MaxHotelPool = 12;   //dopisac liczbe hoteli - zrobione

    //------------------------------------------------------------------

/*
    public Bank(String name, int accountBalance, CityCard[] propertiesOwned, int currentField, int housePool, int hotelPool, CityCard[] mortgages) {
        super(name, accountBalance, currentField);
        HousePool = housePool;
        HotelPool = hotelPool;
        Mortgages = mortgages;                              // konstrukt raczej do wyrzucenia
                                                            //TODO zapytac Piotrka po co jest Mortgages
    }
*/

    public Bank(String name, int accountBalance,  int HousePool, int HotelPool) {             //tymczasowy konstruktor zeby ruszyc Maina, bo nie ogarniam jak wypelnic CityCardy,
        super(name, accountBalance);                                                                //w sumie to nie wiem po co banku currentField i housepool
        MaxHousePool = HousePool;
        MaxHotelPool = HotelPool;
    }

    public int getHousePool() {
        return HousePool;
    }

    public int getHotelPool() {
        return HotelPool;
    }

    public int getMaxHousePool() {
        return MaxHousePool;
    }

    public int getMaxHotelPool() {
        return MaxHotelPool;
    }

    //----------------------------METHODS---------------------------------

    public void  TakeHouse() throws HousePoolEmptyException{
        if(HousePool!=0)
            HousePool--;
        else
            throw new HousePoolEmptyException();
    }

    public void ReturnHouse(){
        if(HousePool<MaxHousePool)
            HousePool++;
    }

    public void TakeHotel() throws HotelPoolEmptyException{
        if(HotelPool!=0)
            HotelPool--;
        else
            throw new HotelPoolEmptyException();
    }

    public void ReturnHotel(){
        if(HotelPool<MaxHotelPool)
            HotelPool++;
    }


}