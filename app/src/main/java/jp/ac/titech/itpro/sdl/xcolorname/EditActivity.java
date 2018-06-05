package jp.ac.titech.itpro.sdl.xcolorname;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private final static String TAG = "EditActivity";

    private View pickedColorView;
    private int pickedColor;

    private ListView resultView;
    ResultAdapter resultAdapter;
    List<ResultViewItem> resultItems;

    private TextView hueTextview;
    private TextView saturationTextview;
    private TextView valueTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        pickedColor = intent.getIntExtra(getString(R.string.key_picked_color), -1);
        pickedColorView = findViewById(R.id.picked_color_view);
        pickedColorView.setBackgroundColor(pickedColor);

        resultView = findViewById(R.id.result_view);
        resultItems = new ArrayList<>();
        resultAdapter = new ResultAdapter(this, R.layout.resultview_item, resultItems);
        resultView.setAdapter(resultAdapter);
        updateResultView(pickedColor);







        hueTextview = findViewById(R.id.hue_textview);
        saturationTextview = findViewById(R.id.saturation_textview);
        valueTextview = findViewById(R.id.value_textview);
        hueTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "hueTextview.onClick");
                // reset value of hue_seekbar TODO
            }
        });
        saturationTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "saturationTextview.onClick");
                // reset value of saturation_seekbar TODO
            }
        });
        valueTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "valueTextview.onClick");
                // reset value of value_seekbar TODO
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.item_nameset:

                // TODO

                break;
            case R.id.item_diffmethod:

                // TODO

                break;
        }
        return true;
    }





    private void updateResultView(int pickedColor){


        // TODO
        // update list resultItems (by analyzing color diff)






        // for test
        int ic_red = Color.parseColor("#FF0000");
        int ic_blue = Color.parseColor("#00FF00");
        int ic_green = Color.parseColor("#0000FF");
        ResultViewItem rvi1 = new ResultViewItem(ic_red, "tesutoiro", "#tesuto");
        ResultViewItem rvi2 = new ResultViewItem(ic_blue, "tesutoiro2", "#tesuto");
        ResultViewItem rvi3 = new ResultViewItem(ic_green, "tesutoiro3", "#tesuto");
        resultItems.add(rvi1);

        resultItems.add(rvi2);
        //resultItems.remove(rvi1);

        resultItems.add(rvi3);
        //resultItems.remove(rvi2);



        resultAdapter.notifyDataSetChanged();
    }



}
