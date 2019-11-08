package com.deboosere.myqr;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class AnimationManager {
    public void RotageImageView(ImageView imageView, int count, int duration){
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(count);
        anim.setDuration(duration);

        imageView.startAnimation(anim);

        // imageView.setAnimation(null);
    }


    public void StopRotationImageView(ImageView imageView){
         imageView.setAnimation(null);
    }

    // To animate view slide out from bottom to top
    public void slideToBottom(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0,0,-2*view.getHeight(),0);
        animate.setDuration(500);
        animate.setFillAfter(true);

        view.startAnimation(animate);
       }

    public void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-2*view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }
}
