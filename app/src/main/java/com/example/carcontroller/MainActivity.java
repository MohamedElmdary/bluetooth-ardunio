package com.example.carcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BtnMoveType[] btnsMoveType = new BtnMoveType[4];
    private class BtnMoveType {
        public Button btn;
        public String type;
        public BtnMoveType(int btn, String type) {
            this.btn = findViewById(btn);
            this.type = type;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsMoveType[0] = new BtnMoveType(R.id.up, "u");
        btnsMoveType[1] = new BtnMoveType(R.id.down, "d");
        btnsMoveType[2] = new BtnMoveType(R.id.right, "r");
        btnsMoveType[3] = new BtnMoveType(R.id.left, "l");

        for (int i = 0; i < 4; i++) {
            addListenerToButtons(btnsMoveType[i]);
        }
    }

    private void addListenerToButtons(final BtnMoveType clickedButton) {
        clickedButton.btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        // bluetooth sends the char which indicates the action to the car
                        Log.d("ACTION Type: ", clickedButton.type);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("ACTION Type: ", "s");
                        break;
                }
                return false;
            }
        });
    }

}
