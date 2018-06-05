package jp.ac.titech.itpro.sdl.xcolorname;

import java.util.ArrayList;
import java.util.List;
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


        resultView = findViewById(R.id.result_view);
        resultItems = new ArrayList<>();

        // for test
        ResultViewItem rvi1 = new ResultViewItem(200, "tesutoiro", "#999999");
        ResultViewItem rvi2 = new ResultViewItem(20000, "tesutoiro2", "#tesuto");
        ResultViewItem rvi3 = new ResultViewItem(2000000, "tesutoiro3", "#111111");
        resultItems.add(rvi1);

        resultAdapter = new ResultAdapter(this, R.layout.resultview_item, resultItems);

        // for test
        resultItems.add(rvi2);
        resultItems.remove(rvi1);

        resultView.setAdapter(resultAdapter);

        // for test
        resultItems.add(rvi3);
        resultItems.remove(rvi2);

        // call adapter.notifyDataSetChanged() when internal list is updated



        hueTextview = findViewById(R.id.hue_textview);
        saturationTextview = findViewById(R.id.saturation_textview);
        valueTextview = findViewById(R.id.value_textview);
        hueTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "hueTextview.onClick");
                // reset value of hue_seekbar
            }
        });
        saturationTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "saturationTextview.onClick");
                // reset value of saturation_seekbar
            }
        });
        valueTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "valueTextview.onClick");
                // reset value of value_seekbar
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


}
