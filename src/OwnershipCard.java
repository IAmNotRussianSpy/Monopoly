public  abstract class OwnershipCard {
    private int price;
    private String title;
    private int FieldNumber;
    public int Worth;

    //-----------------------------------------------------------------------------

    public OwnershipCard(int price, String title, int fieldNumber, int worth) {
        this.price = price;
        this.title = title;
        FieldNumber = fieldNumber;
        Worth = worth;
    }

    public OwnershipCard() {
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public int getFieldNumber() {
        return FieldNumber;
    }

    public int getWorth() {
        return Worth;
    }
}
