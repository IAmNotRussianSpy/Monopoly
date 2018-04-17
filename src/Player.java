import Exceptions.BankruptException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class Player {
    public String Name;
    private int AccountBalance;
    public CityCard[] PropertiesOwned = new CityCard[40];
    private int CurrentField;
    private boolean InJail;
    private boolean waited = true;
    private int lastPosition;


    //-------------------------------------------------------------------------------

    public Player( String name, int accountBalance, int currentField, int lastposition) {
        lastPosition = lastposition;
        Name = name;
        AccountBalance = accountBalance;
        CurrentField = currentField;
        InJail = false;
    }


    public Player(String name, int accountBalance) {
        Name = name;
        AccountBalance = accountBalance;
    }

    public String getName() {
        return Name;
    }

    public int getAccountBalance() {
        return AccountBalance;
    }

    public CityCard[] getPropertiesOwned() {
        return PropertiesOwned;
    }

    public int getCurrentField() {
        return CurrentField;
    }

    public int getLastPosition() { return lastPosition; }

    public boolean isWaited() { return waited; }

    public void setAccountBalance(int accountBalance) {
        AccountBalance = accountBalance;
    }

    public void setCurrentField(int currentField) {
        CurrentField = currentField;
    }

    public void setWaited(boolean waited) {
        this.waited = waited;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public boolean isInJail() {
        return InJail;
    }

    //---------------------------------METHODS--------------------------------------

    public void AccBalanceChange(int amount) throws BankruptException {
        AccountBalance = AccountBalance + amount;
        if (AccountBalance <= 0)
            throw new BankruptException();
    }

    public void UpdateCurrentField(int DiceRoll) {
        CurrentField = (CurrentField + DiceRoll) % 40;
    }

    public void MoveToPrison() {
        CurrentField = 10;
        InJail = true;
    }

    public void ReleaseFromPrison() {
        InJail = false;
    }

    public void LoadInitialList() throws IOException {
        for (int i = 0; i < 40; i++) {
            String[] args = ReadLine(i).split(";");
            PropertiesOwned[i] = new CityCard(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), Integer.parseInt(args[10]), Integer.parseInt(args[11]), Integer.parseInt(args[12]), args[13]);
        }
    }

    public String ReadLine(int linenr) throws IOException {
        FileInputStream fs = new FileInputStream("MonopolyInitialFieldList.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        for (int i = 0; i < linenr; ++i)
            br.readLine();
        String lineIWant = br.readLine();

        return lineIWant;
    }

    public void ChangeWaitedStatus() {
        if (this.waited == true)
            waited = false;
        else
            waited = true;
    }

  /*  public int CheckPlayerStatus()
    {
        if(this.waited == false)
            return 1;
        else
            return 0;
    }
*/

}