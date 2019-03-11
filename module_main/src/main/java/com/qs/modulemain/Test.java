package com.qs.modulemain;

import android.view.View;
import android.widget.Button;

public class Test {
    private void Test() {
        String rule = "^(http://|https://)[a-zA-Z0-9.]+:\\d*$";
        "".matches(rule);
        Button button = new Button(null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
