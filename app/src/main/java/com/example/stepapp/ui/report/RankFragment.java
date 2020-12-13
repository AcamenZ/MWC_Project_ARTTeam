package com.example.stepapp.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.stepapp.R;
import com.example.stepapp.StepAppOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RankFragment extends Fragment {

    AnyChartView rankingView;
    public Map<String, Integer> rankingByDay = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rankings, container, false);

        // Create ranking chart
        rankingView = root.findViewById(R.id.rankBarChart);
        APIlib.getInstance().setActiveAnyChartView(rankingView);

        Cartesian rankGraph = rankingGraph();
        rankingView.setBackgroundColor("#00000000");
        rankingView.setChart(rankGraph);

        return root;
    }

    public Cartesian rankingGraph(){

        //Read data from SQLiteDatabase
        rankingByDay = StepAppOpenHelper.loadRankingByDay(getContext());

        // Create and get the cartesian coordinate system for column chart
        Cartesian cartesian = AnyChart.column();

        // Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<String,Integer> entry : rankingByDay.entrySet())
            data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));

        // Set the data for column chart and get the columns
        Column column = cartesian.column(data);

        //Modify the UI of the Column
        //Change the color of bar chart and its border
        column.fill("#1EB980");
        column.stroke("#1EB980");

        // Add tooltip to the bar charts and modify its properties
        column.tooltip()
                .titleFormat("Activity: {%X}")
                .position(Position.RIGHT_TOP)
                .anchor(Anchor.RIGHT_TOP)
                .offsetX(0d)
                .offsetY(5)
                .format("{%Value}{groupsSeparator: } Score");


        // Modify the UI of the Cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.background().fill("#00000000");
        cartesian.yScale().minimum(0);
        cartesian.yAxis(0).title("Score");
        cartesian.xAxis(0).title("Activity");
        cartesian.animation(true);

        return cartesian;
    }
}
