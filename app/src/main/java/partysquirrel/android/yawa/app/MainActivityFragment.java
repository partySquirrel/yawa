package partysquirrel.android.yawa.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> forecasts = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            String cast = "Day " + (i + 1) + " - ";

            if (i % 2 == 0) {
                cast += " sunny - ";
            } else if (i % 3 == 0) {
                cast += " rainy - ";
            } else if (i % 5 == 0) {
                cast += " cloudy - ";
            } else if (i % 7 == 0) {
                cast += " windy - ";
            } else {
                cast += " snowy - ";
            }

            cast += random.nextInt(30) + "/" + random.nextInt(20);

            forecasts.add(cast);
        }

        ArrayAdapter<String> forecastAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecasts
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);

        return rootView;
    }


}
