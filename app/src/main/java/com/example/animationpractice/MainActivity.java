package com.example.animationpractice;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {


    AtomicBoolean vectorFlag = new AtomicBoolean(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button finishButton = findViewById(R.id.changeButton);


        final AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.singal2_anim);
        final AnimatedVectorDrawableCompat animatedVectorDrawableCompat1 = AnimatedVectorDrawableCompat.create(this, R.drawable.signal_anim);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageDrawable(animatedVectorDrawableCompat);
        final Handler mainHandler = new Handler(Looper.getMainLooper());


        animatedVectorDrawableCompat.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (vectorFlag.get()) {
                            imageView.setImageDrawable(animatedVectorDrawableCompat1);
                            animatedVectorDrawableCompat1.start();
                        }
                    }
                });
            }
        });
        animatedVectorDrawableCompat.start();

        animatedVectorDrawableCompat1.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (vectorFlag.get()) {
                            imageView.setImageDrawable(animatedVectorDrawableCompat);
                            animatedVectorDrawableCompat.start();
                        }
                    }
                });
            }
        });
        final AnimatedVectorDrawableCompat animatedVectorDrawableCompatCheckMark = AnimatedVectorDrawableCompat.create(this, R.drawable.check_anim);


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vectorFlag.set(false);
                if (animatedVectorDrawableCompat.isRunning() || animatedVectorDrawableCompat1.isRunning()) {
                    animatedVectorDrawableCompat.stop();
                    animatedVectorDrawableCompat1.stop();
                    imageView.setImageDrawable(animatedVectorDrawableCompatCheckMark);
                    animatedVectorDrawableCompatCheckMark.start();
                }
            }
        });
    }




    public void animate(View view) {
        ImageView v = (ImageView) view;
        Drawable drawable = v.getDrawable();
        if (drawable instanceof AnimatedVectorDrawableCompat) {
            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        } else if (drawable instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) drawable;
            avd.start();
        }



    }
}
