package com.example.stepapp.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.stepapp.R;
import com.example.stepapp.StepAppOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TempFragment extends Fragment {

    AnyChartView anyChartView;
    public Map<String, Double> tempByDay = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_temperature, container, false);

        // Create column chart
        anyChartView = root.findViewById(R.id.tempBarChart);
        anyChartView.setProgressBar(root.findViewById(R.id.loadingBar2));

        Cartesian cartesian = createColumnChart();
        anyChartView.setBackgroundColor("#00000000");
        anyChartView.setChart(cartesian);

        return root;
    }

    public Cartesian createColumnChart(){
        //Read data from SQLiteDatabase
        //stepsByHour = StepAppOpenHelper.loadStepsByDay(getContext());

        // Create and get the cartesian coordinate system for column chart
        Cartesian cartesian = AnyChart.column();

        // Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("30/11/2020", 20.5));
        data.add(new ValueDataEntry("01/12/2020", 10.8));
        data.add(new ValueDataEntry("02/12/2020", 12.0));
        data.add(new ValueDataEntry("03/12/2020", 5.6));
        data.add(new ValueDataEntry("04/12/2020", 1.8));
        //for (Map.Entry<String,Integer> entry : stepsByHour.entrySet())
            //data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));

        // Set the data for column chart and get the columns
        Column column = cartesian.column(data);

        //Modify the UI of the Column
        //Change the color of bar chart and its border
        column.fill("#1EB980");
        column.stroke("#1EB980");

        // Add tooltip to the bar charts and modify its properties
        column.tooltip()
                .titleFormat("Day: {%X}")
                .position(Position.RIGHT_TOP)
                .anchor(Anchor.RIGHT_TOP)
                .offsetX(0d)
                .offsetY(5)
                .format("{%Value}{groupsSeparator: } Steps");


        // Modify the UI of the Cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.background().fill("#00000000");
        cartesian.yScale().minimum(0);
        cartesian.yAxis(0).title("Temperature");
        cartesian.xAxis(0).title("Day");
        cartesian.animation(true);

        return cartesian;
    }
}