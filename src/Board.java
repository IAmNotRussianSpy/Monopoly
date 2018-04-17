public class Board{
    private String[] FieldOwner;
    public int[] FieldType; //0-nieruchomosc, 1-srodek transportu, 2-elektrownia, 3-start, 4-wiezienie, 5-wolne pole (parking, odwiedzajacy), 6-zaplac, 7-karta specjalna

    public Board(int[] fieldType) {
        String[] FieldOwner = new String[40];
        //int[] FieldType = new int[40];
        FieldType = fieldType;
        for (int i = 0; i < 40; i++) {
            FieldOwner[i] = new String();
            FieldOwner[i] = "Bank";
            //FieldType[i] = new int();
        }

        for (int i = 0; i < 40; i++) {
            if (i == 0)
                FieldType[i] = 3;
            if (i % 10 == 5)
                FieldType[i] = 1;
            if (i == 12 || i == 28)
                FieldType[i] = 2;
            if (i == 10 || i == 20)
                FieldType[i] = 5;
            if (i == 30)
                FieldType[i] = 4;
            if (i == 4 || i == 38)
                FieldType[i] = 6;
            if (i == 2 || i == 17 || i == 7 || i == 22 || i == 36 || i == 33)
                FieldType[i] = 7;
        }
    }


    public int getFieldType(int i) {
        return FieldType[i];
    }
}