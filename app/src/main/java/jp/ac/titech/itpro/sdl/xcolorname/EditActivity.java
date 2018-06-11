package jp.ac.titech.itpro.sdl.xcolorname;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import jp.ac.titech.itpro.sdl.xcolorname.color.MyColor;
import jp.ac.titech.itpro.sdl.xcolorname.color.MyNameColor;
import jp.ac.titech.itpro.sdl.xcolorname.color.RgbrColorSimilarity;

public class EditActivity extends AppCompatActivity {
    private final static String TAG = "EditActivity";

    private View pickedColorView;
    private MyColor pickedColor;

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

        pickedColor = new MyColor(intent.getIntExtra(getString(R.string.key_picked_color), -1));
        pickedColorView = findViewById(R.id.picked_color_view);
        pickedColorView.setBackgroundColor(pickedColor.getIntColor());

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





    private void updateResultView(MyColor pickedColor){
        RgbrColorSimilarity rcs = new RgbrColorSimilarity();
        List<MyNameColor> similarColors = rcs.getSimilarColor(pickedColor);

        resultItems.clear();
        for(MyNameColor similarColor: similarColors){
            int intColor = similarColor.getIntColor();
            String colorName = similarColor.getColorName();
            String colorRgb = similarColor.getColorRgb();
            resultItems.add(new ResultViewItem(intColor, colorName, colorRgb));
        }

        resultAdapter.notifyDataSetChanged();
    }



}
