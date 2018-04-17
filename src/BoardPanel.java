import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class BoardPanel extends JPanel {

    private Image imgBackground;
    private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
    private static final int Red = 0;
    private static final int Blue = 1;
    private static final int Green = 2;
    private static final int Yellow = 3;

    private void createAndAddPawn(int color, int x, int y) {
        Image img = this.getImageForPiece(color);
        Pawn pawn = new Pawn(img, x, y);
        this.pawns.add(pawn);
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    public Image getImgBackground() {
        return imgBackground;
    }

    public Image getImageForPiece(int color) {

        String filename = "";
        switch (color) {
            case 0:
                filename += "RedPawn";
                break;
            case 1:
                filename += "BluePawn";
                break;
            case 2:
                filename += "GreenPawn";
                break;
            case 3:
                filename += "YellowPawn";
                break;
        }

        ImageIcon icon = new ImageIcon(filename+".png");
        return icon.getImage();
    }

    public BoardPanel() {
        // load and set background image
        ImageIcon icon = new ImageIcon("MonopolyBoardRescaled.jpg");
        imgBackground = icon.getImage();

        createAndAddPawn(Red, 783,783);
        createAndAddPawn(Blue,843,783);
        createAndAddPawn(Green, 783,843);
        createAndAddPawn(Yellow,843,843);

        DragAndDropListener listener = new DragAndDropListener(this.pawns,this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);


    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(imgBackground, 0, 0, null);
        for (Pawn pawn : this.pawns) {
            g.drawImage(pawn.getImg(), pawn.getX(), pawn.getY(), null);
        }
        }
    }


