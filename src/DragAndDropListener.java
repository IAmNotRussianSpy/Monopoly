import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class DragAndDropListener implements MouseListener, MouseMotionListener {

    private List<Pawn> pawns;
    private BoardPanel monopolyGui;

    private Pawn dragPiece;
    private int dragOffsetX;
    private int dragOffsetY;

    public DragAndDropListener(List<Pawn> pawns, BoardPanel monopolyGui) {
        this.pawns = pawns;
        this.monopolyGui = monopolyGui;
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        int x = evt.getPoint().x;
        int y = evt.getPoint().y;

        // find out which piece to move.
        // we check the list from top to buttom
        // (therefore we itereate in reverse order)
        //
        for (int i = this.pawns.size()-1; i >= 0; i--) {
            Pawn pawn = this.pawns.get(i);

            if( mouseOverPiece(pawn,x,y)){
                // calculate offset, because we do not want the drag piece
                // to jump with it's upper left corner to the current mouse
                // position
                //
                this.dragOffsetX = x - pawn.getX();
                this.dragOffsetY = y - pawn.getY();
                this.dragPiece = pawn;
                break;
            }
        }

        // move drag piece to the top of the list
        if(this.dragPiece != null){
            this.pawns.remove( this.dragPiece );
            this.pawns.add(this.dragPiece);
        }
    }

    /*
     * check whether the mouse is currently over this piece
     * @param piece the playing piece
     * @param x x coordinate of mouse
     * @param y y coordinate of mouse
     * @return true if mouse is over the piece
     */
    private boolean mouseOverPiece(Pawn pawn, int x, int y) {
        return pawn.getX() <= x
                && pawn.getX()+pawn.getImg().getWidth(null)>= x
                && pawn.getY() <= y
                && pawn.getY()+pawn.getImg().getHeight(null)>= y;
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

        if(this.dragPiece.getX()<0 || this.dragPiece.getX() >=900 || this.dragPiece.getY()<0 || this.dragPiece.getY() >=900) {
            this.dragPiece.setX(this.dragPiece.getPrevX());
            this.dragPiece.setY(this.dragPiece.getPrevY());
            this.monopolyGui.repaint();
        }
        this.dragPiece.setPrevX(this.dragPiece.getX());
        this.dragPiece.setPrevY(this.dragPiece.getY());
        this.monopolyGui.repaint();
        this.dragPiece = null;
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        if(this.dragPiece != null){

                this.dragPiece.setX(evt.getPoint().x - this.dragOffsetX);
                this.dragPiece.setY(evt.getPoint().y - this.dragOffsetY);
                this.monopolyGui.repaint();
        }

    }
    @Override
    public void mouseClicked(MouseEvent arg0) {}

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mouseMoved(MouseEvent arg0) {}
}