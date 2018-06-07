package csu.edu.ice.schoolcourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import csu.edu.ice.schoolcourse.R;

public class SplashActivity extends AppCompatActivity {

    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        AnimationSet animationSet = new AnimationSet(true);
        Animation scaleAnim = new ScaleAnimation(0,1f,0,1f,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        Animation rotateAnim = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        Animation alphaAnim = new AlphaAnimation(0,1);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(rotateAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(2000);
        textView1.startAnimation(animationSet);

        Animation alphaAnim2 = new AlphaAnimation(0,1);
        alphaAnim2.setDuration(2000);
        textView2.startAnimation(alphaAnim2);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this,NewsActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
