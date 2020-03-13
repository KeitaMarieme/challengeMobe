package m2dl.mobe.challengemobe.gameplay;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Eric on 5/15/2017.
 */

public class Obstacle implements GameObject{

    private Rect rectangle;
    private int color;
    private Rect rectangle2;

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;

    }

    public Obstacle(int rectHeight, int color, int xStart, int yStart, int playerGap){
        this.color = color;
        // l,t,r,b
        rectangle = new Rect(0,yStart,xStart,yStart+rectHeight);
        rectangle2 = new Rect(xStart+playerGap, yStart, Constants.SCREEN_WIDTH, yStart+rectHeight);
    }

    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rectangle, player.getRectangle())
        || Rect.intersects(rectangle, player.getRectangle());
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    public void update(){

    }


}
