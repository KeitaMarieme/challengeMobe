package m2dl.mobe.challengemobe.gameplay;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Eric on 5/20/2017.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
