package com.letsgo.controller.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.letsgo.R;

public class LoginDialog extends Dialog {

    Context c;

    public LoginDialog(Context context) {
        super(context);
        c= context;

    }

    @Override
    public void show() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_login);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent_background_border);
        ImageView heart = (ImageView) findViewById(R.id.image_heart);

        Animation pulse = AnimationUtils.loadAnimation(c,android.R.anim.fade_in);
        pulse.setDuration(750);
        pulse.setRepeatCount(Animation.INFINITE);
        pulse.setRepeatMode(Animation.REVERSE);
        heart.startAnimation(pulse);

        super.show();
    }
}
