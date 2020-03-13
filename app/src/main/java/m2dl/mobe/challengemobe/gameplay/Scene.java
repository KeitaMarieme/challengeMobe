package m2dl.mobe.challengemobe.gameplay;

import android.graphics.Canvas;
import android.view.MotionEvent;
public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
