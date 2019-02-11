package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Animation extends SavableFragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_animation, container, false);
        rootView.setBackgroundColor(Color.rgb(153, 204, 255));

        ImageView ball = (ImageView) rootView.findViewById(R.id.myball);
        LayoutParams params = (LayoutParams) ball.getLayoutParams();
        params.width = 100;
        params.height = 100;



// Redundant
  /*  protected void onDraw(Canvas c) {
        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ic_yellowball);
        if (x < 0 && y < 0) {
            x = getWidth() / 2;
            y = this.getHeight() / 2;
        } else {
            x += xVelocity;
            y += yVelocity;
            if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {
                xVelocity = xVelocity * -1;
            }
            if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {
                yVelocity = yVelocity * -1;
            }
        }
        c.drawBitmap(ball.getBitmap(), x, y, null);
        h.postDelayed(r, FRAME_RATE);
    }
*/







        return rootView;
}




    @Override
    public void saveState(Bundle bundle) {
    }

    @Override
    public void restoreState(Bundle bundle) {
    }


}
