package com.example.william.droll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int[] prev = new int[10];
    String prevStr="";
    public void prevUpdate(int num){
        for(int i=9;i>0;i--){
            prev[i]=prev[i-1];
        }
        prev[0]=num;
        //
        prevStr="Previous Rolls";
        for (int i=9;i>=0;i--){
            if(prev[i]!=0){
                prevStr+= " "+prev[i]+",";
            }
        }
        prevStr= prevStr.substring(0,prevStr.length()-1);
        TextView prev10 = findViewById(R.id.Previous10);
        prev10.setText(prevStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button four = findViewById(R.id.D4Button);
        four.setOnClickListener(this);

        Button six = findViewById(R.id.D6Button);
        six.setOnClickListener(this);

        Button eight = findViewById(R.id.D8Button);
        eight.setOnClickListener(this);

        Button ten = findViewById(R.id.D10Button);
        ten.setOnClickListener(this);

        Button twelve = findViewById(R.id.D12Button);
        twelve.setOnClickListener(this);

        Button twenty = findViewById(R.id.D20Button);
        twenty.setOnClickListener(this);

        Button hundred = findViewById(R.id.D100Button);
        hundred.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.D4Button:
                TextView d4 = findViewById(R.id.d4text);
                int out=(int) Math.ceil(Math.random()* 4);
                d4.setText(Integer.toString(out));
                prevUpdate(out);
                break;
            case R.id.D6Button:
                TextView d6 = findViewById(R.id.d6text);
                out=(int) Math.ceil(Math.random()* 6);
                d6.setText(Integer.toString(out));
                prevUpdate(out);
                break;
            case R.id.D8Button:
                TextView d8 = findViewById(R.id.d8text);
                out=(int) Math.ceil(Math.random()* 8);
                d8.setText(Integer.toString(out));
                prevUpdate(out);
                break;
            case R.id.D10Button:
                TextView d10 = findViewById(R.id.d10text);
                out=(int) Math.ceil(Math.random()* 10);
                d10.setText(Integer.toString(out));
                prevUpdate(out);
                break;
            case R.id.D12Button:
                TextView d12 = findViewById(R.id.d12text);
                out=(int) Math.ceil(Math.random()* 12);
                d12.setText(Integer.toString(out));
                prevUpdate(out);
                break;
            case R.id.D20Button:
                TextView d20 = findViewById(R.id.d20text);
                out=(int) Math.ceil(Math.random()* 20);
                d20.setText(Integer.toString(out));
                prevUpdate(out);
                break;
            case R.id.D100Button:
                TextView d100 = findViewById(R.id.d100text);
                out=(int) Math.ceil(Math.random()* 100);
                d100.setText(Integer.toString(out));
                prevUpdate(out);
                break;

            default:
                break;
        }

    }
}
