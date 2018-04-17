import java.awt.*;

public class Pawn {
        private Image img;
        private int x;
        private int y;
        private int prevX;
        private int prevY;

    public Image getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public int getPrevX() {

        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public Pawn(Image img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.prevX=x;
        this.prevY=y;
    }
}

