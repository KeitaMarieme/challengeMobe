package m2dl.mobe.challengemobe;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Obstacle implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;


    private int color;



    public Rect getRectangle()
    {
        return rectangle;
    }



    public Obstacle(int rectHeigh, int color, int startX,int startY, int playerGrap)
    {

       // this.rectangle = rectangle;
        this.color = color;
       rectangle = new Rect(0, startY, startX,startY + rectHeigh);
       rectangle2 = new Rect(startX + playerGrap, startY, Constants.SCREEN_WIDTH, startY + rectHeigh);


    }

    public void incrementY(float y)
    {
        rectangle.top += y;
        rectangle.bottom += y;

        rectangle2.top += y;
        rectangle2.bottom += y;

    }

    public boolean playerCollide()
    {
        return false;

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint =  new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);
    }

    @Override
    public void update() {

    }
}
