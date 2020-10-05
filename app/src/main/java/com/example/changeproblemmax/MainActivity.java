package com.example.changeproblemmax;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView tv,tv_ans;
    EditText Value,Coins;
    Button btn_Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.tv);
        tv_ans=findViewById(R.id.tv_ans);
        Value=findViewById(R.id.edTxt_Value);
        Coins=findViewById(R.id.edTxt_Coins);
        btn_Submit=findViewById(R.id.btn_submit);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                tv_ans.setText("");
                int VALUE = Integer.valueOf(Value.getText().toString());
                String[] val=Coins.getText().toString().split(",");
                long[] COIN_VALUES =new long[val.length];
                for (int i=0;i<val.length;i++)
                {
                    COIN_VALUES[i]=Long.valueOf(val[i]);

                }
                long[][] CACHE = new long[COIN_VALUES.length][VALUE + 1];
                Arrays.sort(COIN_VALUES);

                if (VALUE < COIN_VALUES[0]) {
                    tv_ans.append(String.format("0 possible combinations.\n"));
                }
                long solution = 0;
                long currentCoin = COIN_VALUES[0];

                for (int col = 0; col < CACHE[0].length; col++) {
                    if (col % currentCoin == 0) {
                        CACHE[0][col] = 1;
                        solution = 1;
                    }
                }

                for (int row = 1; row < CACHE.length; row++) {
                    currentCoin = COIN_VALUES[row];
                    for (int col = 0; col < CACHE[0].length; col++) {
                        if (currentCoin > VALUE) {
                            CACHE[row][col] = 0;
                        } else if (currentCoin > col) {
                            CACHE[row][col] = CACHE[row - 1][col];
                        } else {
                            CACHE[row][col] = CACHE[row - 1][col] + CACHE[row][col - (int) currentCoin];
                            if (CACHE[row][col] != 0) {
                                solution = CACHE[row][col];
                            }
                        }
                    }
                }
                tv_ans.append(String.format("Max number of combinations to make " + VALUE + " with coin values :"
                        + Arrays.toString(COIN_VALUES) + " : " + solution + "\n"));
                tv.append("        ");
                for (int row = 0; row <= VALUE; row++) {
                    tv.append(String.format("%-5d", row));
                }
                StringBuilder string = new StringBuilder();

                string.append("\n   +");
                for (int row = 0; row <= VALUE * 5; row++) {
                    string.append("-");
                }
                string.append(String.format("-------+\n"));

                tv.append(string);

                for (int row = 0; row < CACHE.length; row++) {
                    tv.append(String.format("%-2d", COIN_VALUES[row]));
                    tv.append(String.format("%-5s", " |"));
                    for (int col = 0; col < CACHE[0].length; col++) {
                        tv.append(String.format("%-5d", CACHE[row][col]));
                    }
                    tv.append(String.format("|\n"));
                    System.out.println();
                }
                tv.append(String.format("\n\n"));
            }
        });
    }
}
