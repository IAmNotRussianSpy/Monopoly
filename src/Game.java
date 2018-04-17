import Exceptions.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    public CityCard[] cityCards;
    public Board board;
    private Bank bank;
    public Player[] players;
    private int playerNumber;
    private int fieldsToMove;
    private int playerTurn=1;

    private int [] OwnedByPlayers;          //do pokazywania przez ktorego gracza dane pole zostalo kupione
                                            //przypisana liczba odpowiada numerowi gracza


    public int getFieldsToMove() {
        return fieldsToMove;
    }

    public int getPlayerTurn() { return playerTurn; }

    public Game(Board board, Bank bank, int PlayerNumber, int[] ownedByPlayers) {
        this.board = board;
        this.bank = bank;
        this.OwnedByPlayers = ownedByPlayers;
        //this.bank = new Bank("Bank",Integer.MAX_VALUE, InitialList,  );

        Player[] players = new Player[PlayerNumber+1];
        //CityCard[] noProperties = new CityCard[40];
        //int[] OwnedByPlayers = new int[40];
        //for(int j=0; j<40; j++)                           // nie wiem dlaczego, ale w ten sposob wyskakiwal
        //    OwnedByPlayers[j] = 0;                        // nullpointerexception, pomoglo przerzucenie tego do maina


        for (int i = 1; i < PlayerNumber+1; i++) {
            players[i] = new Player( "Player", 14000000, 0, 0);
            /*for(int j=0; j<40; j++) {
                noProperties[j] = new CityCard();
            }*/
        }

        //System.out.println(Arrays.toString(players));
    }

    public void DiceRoll(int i) throws PassingStartException{
        Random generator = new Random();
        this.fieldsToMove = generator.nextInt(11) + 2;
        //this.fieldsToMove = 7;
        this.players[i].setLastPosition(this.players[i].getCurrentField());
        //this.players[i].setCurrentField(players[i].getCurrentField()+this.fieldsToMove);
        this.players[i].UpdateCurrentField(this.fieldsToMove);
        if (players[i].getLastPosition()+this.fieldsToMove > 40)    //TODO EXCEPTION START
            throw new PassingStartException("You get 400$ for passing the start");
    }

    public void NextTurn(){
        playerTurn = (playerTurn)%4+1;
    }


    /* public void MakeTurn(){
        for(int i=0;i<playerNumber;i++)

    }*/

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void BuyField(int i) throws CannotBuyException
    {
        int position = this.players[i].getCurrentField();
        //porownuje: stan konta, czy danepole nalezy do nikogo nie nalezy (do gracza 0), czy moge to pole kupic
        if (this.players[i].getAccountBalance() >= cityCards[position].getPrice() && OwnedByPlayers[position] == 0 &&
                (this.cityCards[position].getFieldType() == 0 || this.cityCards[position].getFieldType() == 1 || this.cityCards[position].getFieldType() == 2))
        {
            this.players[i].setAccountBalance(this.players[i].getAccountBalance() - cityCards[position].getPrice());
            OwnedByPlayers[position] = i;
            this.cityCards[position].setOwner(this.players[i].getName());
            JOptionPane.showMessageDialog(new JFrame(), cityCards[position].getTitle()+" has been bought!");
        }
        else
            throw new CannotBuyException("Can not buy");
    }

    public void BuyHouse(int i) throws CannotBuyException, BoughtHouseException
    {
        int position = this.players[i].getCurrentField();
        //porownuje: stan konta, czy danepole do niego nalezy, czy pole to nieruchomosc i ile jest domkow na tym
        if(this.players[i].getAccountBalance() >= cityCards[position].getHousePrice() && OwnedByPlayers[position] == i && this.cityCards[position].getFieldType() == 0 && this.cityCards[position].getRealEstateLevel() <4 )
        {
            this.players[i].setAccountBalance(this.players[i].getAccountBalance() - cityCards[position].getHousePrice());
            try {
                this.bank.TakeHouse();
            }catch(HousePoolEmptyException e){}
            this.cityCards[position].setRealEstateLevel(this.cityCards[position].getRealEstateLevel()+1);
            this.cityCards[position].setOwner(this.players[i].getName());
            throw new BoughtHouseException("You have bought a house");
        }
        else
            throw new CannotBuyException("Can not buy a house here");
    }

    public void BuyHotel(int i) throws CannotBuyException
    {
        int position = this.players[i].getCurrentField();
        //porownuje: stan konta, czy danepole do niego nalezy, czy pole to nieruchomosc
        if(this.players[i].getAccountBalance() >= cityCards[position].getHotelPrice() && OwnedByPlayers[position] == i && this.board.getFieldType(i) == 0 && this.cityCards[position].getRealEstateLevel() ==4)
        {
            this.players[i].setAccountBalance(this.players[i].getAccountBalance() - cityCards[position].getHotelPrice());
            this.bank.ReturnHotel();
            this.cityCards[position].setRealEstateLevel(5);
        }
        else
            throw new CannotBuyException("Can not buy a hotel here");
    }


    public void LoadInitialList() throws IOException {
        cityCards = new CityCard[40];
        for(int i=0;i<40;i++){
            String[] args= ReadLine(i).split(";");
            cityCards[i]= new CityCard(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),args[3],Integer.parseInt(args[4]),Integer.parseInt(args[5]),Integer.parseInt(args[6]),Integer.parseInt(args[7]),Integer.parseInt(args[8]),Integer.parseInt(args[9]),Integer.parseInt(args[10]),Integer.parseInt(args[11]),Integer.parseInt(args[12]), args[13]);
            //System.out.println(cityCards[i].getFieldType());
        }
    }

    public String ReadLine(int linenr) throws IOException {
        FileInputStream fs= new FileInputStream("MonopolyInitialFieldList.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        for(int i = 0; i < linenr; ++i)
            br.readLine();
        String lineIWant = br.readLine();

        return lineIWant;
    }

    public int CheckPlayerStatus(int i)
    {
        if(this.players[i].isWaited() == false)
            return 1;
        else
            return 0;
    }

    public void ChangeWaitedStatus(int i) {
        if (this.players[i].isWaited() == true)
            this.players[i].setWaited(false);
        else
            this.players[i].setWaited(true);
    }

    public void taxPayment(int position, int i) throws HaveToPayException{                              //oplaty za stanie na polu
        if(OwnedByPlayers[position]>0 && OwnedByPlayers[position] != i){
            int tax = this.cityCards[position].getRent(this.cityCards[position].getRealEstateLevel());
            System.out.println((Arrays.toString(this.players)));
            this.players[i].setAccountBalance(this.players[i].getAccountBalance() - tax);                               //odebranie kasy
            this.players[OwnedByPlayers[position]].setAccountBalance(this.players[this.OwnedByPlayers[position]].getAccountBalance() + tax);     //danie wlascicielowi
            throw new HaveToPayException("You stepped on someone's field. You have to pay for what you have done!");
        }
    }


}
