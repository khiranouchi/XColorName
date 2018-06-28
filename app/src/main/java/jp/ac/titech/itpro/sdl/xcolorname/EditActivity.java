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
import android.widget.SeekBar;
import android.widget.TextView;

import jp.ac.titech.itpro.sdl.xcolorname.color.ColorSimilarity;
import jp.ac.titech.itpro.sdl.xcolorname.color.MyFilterableColor;
import jp.ac.titech.itpro.sdl.xcolorname.color.MyNameColor;
import jp.ac.titech.itpro.sdl.xcolorname.color.RgbrColorSimilarity;

public class EditActivity extends AppCompatActivity {
    private final static String TAG = "EditActivity";

    private View pickedColorView;
    private MyFilterableColor pickedColor;

    private ListView resultView;
    ResultAdapter resultAdapter;
    List<ResultViewItem> resultItems;

    private TextView hueTextView, saturationTextView, valueTextView;
    private SeekBar hueSeekBar, saturationSeekBar, valueSeekBar;
    private final int biasHueSeekBar = 15;
    private final int biasSaturationSeekBar = 15;
    private final int biasValueSeekBar = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        pickedColor = new MyFilterableColor(intent.getIntExtra(getString(R.string.key_picked_color), -1));
        pickedColorView = findViewById(R.id.picked_color_view);
        pickedColorView.setBackgroundColor(pickedColor.getIntColor());

        resultView = findViewById(R.id.result_view);
        resultItems = new ArrayList<>();
        resultAdapter = new ResultAdapter(this, R.layout.resultview_item, resultItems);
        resultView.setAdapter(resultAdapter);
        updateResultView();

        hueTextView = findViewById(R.id.hue_textview);
        saturationTextView = findViewById(R.id.saturation_textview);
        valueTextView = findViewById(R.id.value_textview);
        hueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "hueTextview.onClick");
                hueSeekBar.setProgress(biasHueSeekBar); // reset Hue
            }
        });
        saturationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "saturationTextview.onClick");
                saturationSeekBar.setProgress(biasSaturationSeekBar); // reset Saturation
            }
        });
        valueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "valueTextview.onClick");
                valueSeekBar.setProgress(biasValueSeekBar); // reset Value
            }
        });

        hueSeekBar = findViewById(R.id.hue_seekbar);
        saturationSeekBar = findViewById(R.id.saturation_seekbar);
        valueSeekBar = findViewById(R.id.value_seekbar);
        hueSeekBar.setMax(biasHueSeekBar * 2);
        saturationSeekBar.setMax(biasSaturationSeekBar * 2);
        valueSeekBar.setMax(biasValueSeekBar * 2);
        hueSeekBar.setProgress(biasHueSeekBar);
        saturationSeekBar.setProgress(biasSaturationSeekBar);
        valueSeekBar.setProgress(biasValueSeekBar);
        hueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "hueSeekBar.onProgressChanged");
                pickedColor.filterHue(progress - biasHueSeekBar);
                pickedColorView.setBackgroundColor(pickedColor.getIntColor());
                updateResultView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        saturationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "saturationSeekBar.onProgressChanged");
                pickedColor.filterSaturation(progress - biasSaturationSeekBar);
                pickedColorView.setBackgroundColor(pickedColor.getIntColor());
                updateResultView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "valueSeekBar.onProgressChanged");
                pickedColor.filterValue(progress - biasValueSeekBar);
                pickedColorView.setBackgroundColor(pickedColor.getIntColor());
                updateResultView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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





    private void updateResultView(){
        ColorSimilarity cs = new RgbrColorSimilarity();
        List<MyNameColor> similarColors = cs.getSimilarColor(pickedColor);

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
