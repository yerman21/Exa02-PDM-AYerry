package com.example.alumfial1.ventasaplication;

import android.content.Intent;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import com.airbnb.lottie.LottieAnimationView;

public class AnimationActivity extends AppCompatActivity {
    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        getSupportActionBar().hide();
        // PAra coulatar el toolbar, cuando es una programacioncon fragmentos
        // --> supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        animationView=(LottieAnimationView)findViewById(R.id.animation_world);
        IrAlMenu();
    }

    private void IrAlMenu(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                animationView.loop(false);
                animationView.pauseAnimation();
                Intent intent=new Intent(AnimationActivity.this,NavigationActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }

}
