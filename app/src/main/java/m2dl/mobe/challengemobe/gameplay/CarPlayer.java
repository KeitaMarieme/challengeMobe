package m2dl.mobe.challengemobe.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class CarPlayer implements GameObject {

    public static final int Y_OFFSET = 20;
    private Bitmap carBitmap;
    private int positionX;
    private int color;

    public CarPlayer(Bitmap car) {
        this.carBitmap = car;
        System.out.println("car = " + car.getHeight() + " - " + car.getWidth());
        this.positionX = Constants.SCREEN_WIDTH / 2;
    }

    public Bitmap getCarBitmap() {
        return carBitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        canvas.drawBitmap(carBitmap, null, position2Rect(), paint);
    }

    @Override
    public void update() {

    }

    public void update(int positionX) {
        this.positionX = positionX;
    }

    public Rect position2Rect() {
        return new Rect(positionX,
                Constants.SCREEN_HEIGHT - carBitmap.getHeight() - Y_OFFSET,
                Constants.SCREEN_WIDTH - positionX - carBitmap.getWidth(),
                Y_OFFSET);
    }

}
