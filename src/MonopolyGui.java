import Exceptions.BoughtHouseException;
import Exceptions.CannotBuyException;
import Exceptions.HaveToPayException;
import Exceptions.PassingStartException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.Random;


public class MonopolyGui implements Observer {
    private JPanel menu;
    private JPanel Board;
    private JButton rollTheDiceButton;
    private JTextField RollResult;
    private BoardPanel BoardPanel1;
    private JButton Exit;
    private JTextField PlayerText;
    private JButton BuyFieldButton;
    private JButton BuyHouseButton;
    private JButton BuyHotelButton;
    private JTextField Gdynia;
    private JTextField Taipei;
    private JTextField Rail;
    private JTextField Space;
    private JTextField Riga;
    private JTextField Montreal;
    private JTextField CapeTown;
    private JTextField Belgrade;
    private JTextField Jerusalem;
    private JTextField Tokyo;
    private JTextField Paris;
    private JTextField WindEnergy;
    private JTextField HongKong;
    private JTextField Beijing;
    private JTextField Barcelona;
    private JTextField Cruise;
    private JTextField London;
    private JTextField NewYork;
    private JTextField Sydney;
    private JTextField Vancouver;
    private JTextField Athens;
    private JTextField Shanghai;
    private JTextField SolarEnergy;
    private JTextField Rome;
    private JTextField Air;
    private JTextField Toronto;
    private JTextField Kyiv;
    private JTextField Istanbul;
    private JButton EndTurnButton;
    private JTextField PlayerMoney;
    private JTextField PlayerPosition;
    private JTextField clockField;
    private JTextField playerTurnText;
    private Game game;
    private Clock clock = new Clock();

    private boolean drawn =false;

    public MonopolyGui(Game g) {
        this.game = g;
        clock.subscribe(this);
        clock.start();
        PlayerText.setText("Player " + game.getPlayerTurn() + " turn");


        rollTheDiceButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
                try{
                    if(drawn == false) {
                        game.DiceRoll(game.getPlayerTurn());
                        try{
                            game.taxPayment(game.players[game.getPlayerTurn()].getCurrentField(), game.getPlayerTurn());
                        } catch (HaveToPayException e1){
                            JOptionPane.showMessageDialog(null, e1.getMessage());
                            refresh();
                        }

                        drawn = true;
                        refresh();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "You can not roll two times!");
                } catch (PassingStartException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
                refresh();
                checkField();
            }

        });

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        PlayerText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        BuyFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    game.BuyField(game.getPlayerTurn());
                    refresh();
                } catch (CannotBuyException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }

            }
        });
        BuyHouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    game.BuyHouse(game.getPlayerTurn());
                    refresh();
                } catch (CannotBuyException e2) {
                    JOptionPane.showMessageDialog(null, e2.getMessage());
                } catch (BoughtHouseException e3){
                    JOptionPane.showMessageDialog(null, e3.getMessage());
                }

            }
        });
        BuyHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    game.BuyHotel(game.getPlayerTurn());
                    refresh();
                } catch (CannotBuyException e3){
                    JOptionPane.showMessageDialog(null, e3.getMessage());
                }

            }
        });





        EndTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(drawn == true) {
                    game.NextTurn();
                    refresh();
                    if (game.CheckPlayerStatus(game.getPlayerTurn()) == 1)   //sprawdza czy gracz odczekal juz kolejke w wiezieniu
                    {
                        game.ChangeWaitedStatus(game.getPlayerTurn());      //jesli nie to zmiana na odczekanie i ruch kolejnego gracza
                        game.NextTurn();
                        refresh();
                    } else
                        game.players[game.getPlayerTurn()].ReleaseFromPrison();
                    //checkField();
                    drawn = false;
                }
                else
                    JOptionPane.showMessageDialog(null, "You need to Roll the Dice first!");
            }

        });
        refresh();
    }

    public void refresh() {
        RollResult.setText(game.getFieldsToMove() + "");
        PlayerText.setText(game.players[game.getPlayerTurn()].getName() + "'s" + " turn");
        this.updateFieldData();
        PlayerMoney.setText(game.players[game.getPlayerTurn()].getAccountBalance()+"");
        PlayerPosition.setText("Position: " + game.players[game.getPlayerTurn()].getCurrentField());

    }

    public void inform(){
        clockField.setText(clock.getTime());
        this.refresh();
    }




    public void updateFieldData(){
        Gdynia.setText(this.game.cityCards[1].getTitle()+" \t\t "+this.game.cityCards[1].getOwner()+" \t "+this.game.cityCards[1].getRealEstateLevel());
        Taipei.setText(this.game.cityCards[3].getTitle() + " \t\t " + this.game.cityCards[3].getOwner() + " \t " + this.game.cityCards[3].getRealEstateLevel());
        Rail.setText(this.game.cityCards[5].getTitle() + " \t\t " + this.game.cityCards[5].getOwner() + " \t " + this.game.cityCards[5].getRealEstateLevel());
        Tokyo.setText(this.game.cityCards[6].getTitle() + " \t\t " + this.game.cityCards[6].getOwner() + " \t " + this.game.cityCards[6].getRealEstateLevel());
        Barcelona.setText(this.game.cityCards[8].getTitle() + " \t\t " + this.game.cityCards[8].getOwner() + " \t " + this.game.cityCards[8].getRealEstateLevel());
        Athens.setText(this.game.cityCards[9].getTitle() + " \t\t " + this.game.cityCards[9].getOwner() + " \t " + this.game.cityCards[9].getRealEstateLevel());
        Istanbul.setText(this.game.cityCards[11].getTitle() + " \t\t " + this.game.cityCards[11].getOwner() + " \t " + this.game.cityCards[11].getRealEstateLevel());
        SolarEnergy.setText(this.game.cityCards[12].getTitle() + " \t " + this.game.cityCards[12].getOwner() + " \t " + this.game.cityCards[12].getRealEstateLevel());
        Kyiv.setText(this.game.cityCards[13].getTitle() + " \t\t " + this.game.cityCards[13].getOwner() + " \t " + this.game.cityCards[13].getRealEstateLevel());
        Toronto.setText(this.game.cityCards[14].getTitle() + " \t\t " + this.game.cityCards[14].getOwner() + " \t " + this.game.cityCards[14].getRealEstateLevel());
        Air.setText(this.game.cityCards[15].getTitle() + " \t\t " + this.game.cityCards[15].getOwner() + " \t " + this.game.cityCards[15].getRealEstateLevel());
        Rome.setText(this.game.cityCards[16].getTitle() + " \t\t " + this.game.cityCards[16].getOwner() + " \t " + this.game.cityCards[16].getRealEstateLevel());
        Shanghai.setText(this.game.cityCards[18].getTitle() + " \t\t " + this.game.cityCards[18].getOwner() + " \t " + this.game.cityCards[18].getRealEstateLevel());
        Vancouver.setText(this.game.cityCards[19].getTitle() + " \t\t " + this.game.cityCards[19].getOwner() + " \t " + this.game.cityCards[19].getRealEstateLevel());
        Sydney.setText(this.game.cityCards[21].getTitle() + " \t\t " + this.game.cityCards[21].getOwner() + " \t " + this.game.cityCards[21].getRealEstateLevel());
        NewYork.setText(this.game.cityCards[23].getTitle() + " \t\t " + this.game.cityCards[23].getOwner() + " \t " + this.game.cityCards[23].getRealEstateLevel());
        London.setText(this.game.cityCards[24].getTitle() + " \t\t " + this.game.cityCards[24].getOwner() + " \t " + this.game.cityCards[24].getRealEstateLevel());
        Cruise.setText(this.game.cityCards[25].getTitle() + " \t\t " + this.game.cityCards[25].getOwner() + " \t " + this.game.cityCards[25].getRealEstateLevel());
        Beijing.setText(this.game.cityCards[26].getTitle() + " \t\t " + this.game.cityCards[26].getOwner() + " \t " + this.game.cityCards[26].getRealEstateLevel());
        HongKong.setText(this.game.cityCards[27].getTitle() + " \t\t " + this.game.cityCards[27].getOwner() + " \t " + this.game.cityCards[27].getRealEstateLevel());
        WindEnergy.setText(this.game.cityCards[28].getTitle() + " \t " + this.game.cityCards[28].getOwner() + " \t " + this.game.cityCards[28].getRealEstateLevel());
        Jerusalem.setText(this.game.cityCards[29].getTitle() + " \t\t " + this.game.cityCards[29].getOwner() + " \t " + this.game.cityCards[29].getRealEstateLevel());
        Paris.setText(this.game.cityCards[31].getTitle() + " \t\t " + this.game.cityCards[31].getOwner() + " \t " + this.game.cityCards[31].getRealEstateLevel());
        Belgrade.setText(this.game.cityCards[32].getTitle() + " \t\t " + this.game.cityCards[32].getOwner() + " \t " + this.game.cityCards[32].getRealEstateLevel());
        CapeTown.setText(this.game.cityCards[34].getTitle() + " \t\t " + this.game.cityCards[34].getOwner() + " \t " + this.game.cityCards[34].getRealEstateLevel());
        Space.setText(this.game.cityCards[35].getTitle() + " \t\t " + this.game.cityCards[35].getOwner() + " \t " + this.game.cityCards[35].getRealEstateLevel());
        Riga.setText(this.game.cityCards[37].getTitle() + " \t\t " + this.game.cityCards[37].getOwner() + " \t " + this.game.cityCards[37].getRealEstateLevel());
        Montreal.setText(this.game.cityCards[39].getTitle() + " \t\t " + this.game.cityCards[39].getOwner() + " \t " + this.game.cityCards[39].getRealEstateLevel());
        }



    public void checkField() {
        int number = game.getPlayerTurn();
        int n=0;
        Random g = new Random();
        int a = g.nextInt(2);
        switch(game.board.getFieldType(game.players[number].getCurrentField())) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                if (n >= 4) {
                    JOptionPane.showMessageDialog(null, "You get 400$ for passing the start");
                    game.players[number].setAccountBalance(game.players[number].getAccountBalance() + 400);
                    break;
                } else
                    break;
            case 4:
                JOptionPane.showMessageDialog(null, "You go to prison!");
                game.players[number].MoveToPrison();
                game.players[number].ChangeWaitedStatus();
                break;
            case 5:
                JOptionPane.showMessageDialog(null, "Nothing to do here :(");
                break;
            /*case 6:
                JOptionPane.showMessageDialog(null, "You have to pay 1000$");
                game.players[number].setAccountBalance(game.players[number].getAccountBalance() - 1000);
                break;*/
            case 6:
                g = new Random();
                a = g.nextInt(2);
                if (a==1) {
                    JOptionPane.showMessageDialog(null, "The special card says that your wallet is lighter by 1000$");
                    game.players[number].setAccountBalance(game.players[number].getAccountBalance() - 1000);
                }
                else {
                    JOptionPane.showMessageDialog(null, "The special card says that your wallet is heavier by 1000$");
                    game.players[number].setAccountBalance(game.players[number].getAccountBalance() + 1000);
                }
                break;
            case 7:
                g = new Random();
                a = g.nextInt(2);
                if (a==1) {
                    JOptionPane.showMessageDialog(null, "The special card says that your wallet is lighter by 1000$");
                    game.players[number].setAccountBalance(game.players[number].getAccountBalance() - 1000);
                }
                else {
                    JOptionPane.showMessageDialog(null, "The special card says that your wallet is heavier by 1000$");
                    game.players[number].setAccountBalance(game.players[number].getAccountBalance() + 1000);
                }
                break;
        }
        n++;
    }



    //---------------------------------MAIN-----------------------------------------------

    public static void main(String[] arguments){ // DR RAPACZ TEAM
        int[] a = new int[40];
        int[] b = new int[40];
        for(int i=0; i<40; i++)
            a[i] = 0;

        for(int i=0;i<40;i++) {
            if (i == 0)
                b[i] = 3;
            if (i % 10 == 5)
                b[i] = 1;
            if (i == 12 || i == 28)
                b[i] = 2;
            if (i == 10 || i == 20)
                b[i] = 5;
            if (i == 30)
                b[i] = 4;
            if (i == 4 || i == 39)
                b[i] = 6;
            if (i == 2 || i == 17 || i == 22 || i == 36)
                b[i] = 7;
        }
        Board board = new Board(b);
        Bank bank = new Bank("random_name", 100000000, 32, 12);
        Game game = new Game(board, bank, 4, a);
        //System.out.println(Arrays.toString(game.players));


        try {
            game.LoadInitialList();
        } catch (IOException ex) {
            System.out.println("No such file");
        }

        Player[] players = new Player[5];
        for (int i = 1; i < 5; i++)
            players[i] = new Player( "Player", 14000000, 0,0);
        game.setPlayers(players);       //dopiero setterem przeszlo

            game.players[1].setName("RedPlayer");
            game.players[2].setName("BluePlayer");
            game.players[3].setName("GreenPlayer");
            game.players[4].setName("YellowPlayer");

            /*for(int i=0; i<40;i++){
                //game.cityCards[i].setRealEstateLevel(0);
                game.OwnedByPlayers[i]=1;
            }*/





        //System.out.println(Arrays.toString(game.board.FieldType));
        System.out.println(Arrays.toString(game.cityCards[12].Rents));

        JFrame frame2 = new JFrame("Monopoly");
        frame2.setExtendedState(JFrame.MAXIMIZED_BOTH); //TODO: wlaczyc przed oddawaniem!!!!!!!!1!one
        frame2.setUndecorated(true); //TODO: --||--
        frame2.setResizable(false);  //TODO: --||--
        ImageIcon icon = new ImageIcon("MonopolyIcon.png");
        frame2.setIconImage(icon.getImage());
        frame2.setContentPane(new MonopolyGui(game).menu);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.pack();
        frame2.setVisible(true);
    }
}