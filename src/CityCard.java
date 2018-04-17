import Exceptions.*;

public class CityCard extends OwnershipCard {
    private int HousePrice;
    private int HotelPrice;
    private int RealEstateLevel;    //  liczba domkow/hotelu
    public int[] Rents;    //Tabela czynszow na danym polu gdzie 0-bez domkow, 1-z 1 domkiem itp. do 5-z hotelem i 6-podwojny czynsz z pustego pola jezeli gracz ma wszystkie pola danego koloru
    private int FieldType;
    private String Owner;
    //private int[] StateOfProperty; //0-nie ma domkow, 1-1domek itd. 5-hotel

    //-----------------------------------------------------------------------------------------------


    public CityCard(int fieldNumber, int fieldType, int price, String title, int worth, int housePrice, int hotelPrice, int rent0, int rent1, int rent2, int rent3, int rent4, int rent5, String owner) {
        super(price, title, fieldNumber, worth);
        HousePrice = housePrice;
        FieldType=fieldType;
        HotelPrice = hotelPrice;
        RealEstateLevel = 0;
        Rents = new int[]{rent0, rent1, rent2, rent3, rent4, rent5};
        Owner = owner;
    }



    public CityCard() {
    }

    public String getOwner() {  return Owner;  }

    public int getFieldType() { return FieldType; }

    public int getHousePrice() {
        return HousePrice;
    }

    public int getHotelPrice() {
        return HotelPrice;
    }

    public int getRealEstateLevel() {
        return RealEstateLevel;
    }

    //public int getStateOfProperty(int i) { return StateOfProperty[i]; }

    public int getRent(int i) { return Rents[i]; }

    //public void setStateOfProperty(int p, int i) { StateOfProperty[p] = i; }

    public void setRealEstateLevel(int realEstateLevel) {
        RealEstateLevel = realEstateLevel;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }



    //----------------------------------METHODS---------------------------------------

    public void BuildHouse() throws MaximumHousesException {
        if(this.getRealEstateLevel()<4)
            {
                this.RealEstateLevel++;
            }
            else throw new MaximumHousesException();
            }

    public void DemolishHouse() throws NoHousesToSellException {
        if(RealEstateLevel==0)
            throw new NoHousesToSellException();
        if(RealEstateLevel>0 && RealEstateLevel<=4)
            RealEstateLevel--;
    }

    public void BuildHotel() throws NotEnoughHousesException, HotelAlreadyBuiltException {
        if(RealEstateLevel==4)
            RealEstateLevel=RealEstateLevel+1;
        if(RealEstateLevel==5)
            throw new HotelAlreadyBuiltException();
        if(RealEstateLevel<4)
            throw new NotEnoughHousesException();
    }

    public void DemolishHotel() throws NoHotelException {
        if(RealEstateLevel!=5)
            throw new NoHotelException();
        else
            RealEstateLevel=0;
    }

    public void ExchangeHotel() throws NoHotelException{
        if(RealEstateLevel!=5)
            throw new NoHotelException();
        else
            RealEstateLevel=4;
    }
}

