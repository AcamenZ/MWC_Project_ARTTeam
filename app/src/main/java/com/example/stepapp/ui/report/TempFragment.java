package com.example.stepapp.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.data.Set;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.stepapp.R;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.enums.MarkerType;
import com.anychart.graphics.vector.Stroke;
import com.example.stepapp.StepAppOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TempFragment extends Fragment {

    AnyChartView tempView;
    AnyChartView rainView;
    AnyChartView pressureView;
    AnyChartView windSpeedView;
    AnyChartView humidityView;
    public Map<String, Double> tempByDay = null;
    public Map<String, Integer> pressureByDay = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_temperature, container, false);

        // Create temperature chart
        tempView = root.findViewById(R.id.tempBarChart);
        APIlib.getInstance().setActiveAnyChartView(tempView);
        Cartesian tempGraph = temperatureGraph();
        tempView.setBackgroundColor("#00000000");
        tempView.setChart(tempGraph);

        // Create rain column chart
        rainView = root.findViewById(R.id.rainBarChart);
        APIlib.getInstance().setActiveAnyChartView(rainView);
        Cartesian rainGraph = rainGraph();
        rainView.setBackgroundColor("#00000000");
        rainView.setChart(rainGraph);

        // Create pressure line chart
        pressureView = root.findViewById(R.id.pressureLineChart);
        APIlib.getInstance().setActiveAnyChartView(pressureView);
        Cartesian pressureGraph = pressureGraph();
        pressureView.setBackgroundColor("#00000000");
        pressureView.setChart(pressureGraph);

        // Create wind speed line chart
        windSpeedView = root.findViewById(R.id.windSpeedLineChart);
        APIlib.getInstance().setActiveAnyChartView(windSpeedView);
        Cartesian windSpeedGraph = windSpeedGraph();
        windSpeedView.setBackgroundColor("#00000000");
        windSpeedView.setChart(windSpeedGraph);

        // Create humidity line chart
        humidityView = root.findViewById(R.id.humidityLineChart);
        APIlib.getInstance().setActiveAnyChartView(humidityView);
        Cartesian humidityGraph = humidityGraph();
        humidityView.setBackgroundColor("#00000000");
        humidityView.setChart(humidityGraph);

        return root;
    }

    public Cartesian temperatureGraph(){
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


        // Modify the UI of twindSpeedGraphhe Cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.background().fill("#00000000");
        cartesian.yScale().minimum(0);
        cartesian.yAxis(0).title("Temperature");
        cartesian.xAxis(0).title("Day");
        cartesian.animation(true);

        return cartesian;
    }

    public Cartesian rainGraph() {

        // Create and get the cartesian coordinate system for column chart
        Cartesian cartesian = AnyChart.column();

        // Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("30/11/2020", 4.1));
        data.add(new ValueDataEntry("01/12/2020", 2.5));
        data.add(new ValueDataEntry("02/12/2020", 1.8));
        data.add(new ValueDataEntry("03/12/2020", 3.5));
        data.add(new ValueDataEntry("04/12/2020", 0.7));

        // Add the data to column chart and get the columns
        Column column = cartesian.column(data);

        // Modify the UI of the chart
        column.fill("#1EB980");
        column.stroke("#1EB980");

        // Modifying properties of tooltip
        column.tooltip()
                .titleFormat("At day: {%X}")
                .format("{%Value}{groupsSeparator: } Temps")
                .anchor(Anchor.RIGHT_TOP);

        // Modify column chart tooltip properties
        column.tooltip()
                .position(Position.RIGHT_TOP)
                .offsetX(0d)
                .offsetY(5);

        // Modifying properties of cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.yScale().minimum(0);

        // Modify the UI of the cartesian
        cartesian.yAxis(0).title("Millimeters");
        cartesian.xAxis(0).title("Day");
        cartesian.background().fill("#00000000");
        cartesian.animation(true);

        return cartesian;
    }

    public Cartesian pressureGraph() {

        //Read data from SQLiteDatabase
        pressureByDay = StepAppOpenHelper.loadPressureByDay(getContext());

        // Create and get the cartesian coordinate system for line chart
        Cartesian cartesian = AnyChart.line();

        // Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : pressureByDay.entrySet())
            data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));

        // Set the data for line chart
        cartesian.data(data);
        Set set = Set.instantiate();
        set.data(data);

        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");
        Line series = cartesian.line(seriesMapping);
        series.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        // Modifying properties of cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        // Modify the UI of the cartesian
        cartesian.yAxis(0).title("hPa");
        cartesian.xAxis(0).title("Day");
        cartesian.background().fill("#00000000");
        cartesian.animation(true);

        return cartesian;
    }

    public Cartesian windSpeedGraph() {

        // Create and get the cartesian coordinate system for line chart
        Cartesian cartesian = AnyChart.line();

        // Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("30/11/2020", 1.7));
        data.add(new ValueDataEntry("01/12/2020", 1.5));
        data.add(new ValueDataEntry("02/12/2020", 3.5));
        data.add(new ValueDataEntry("03/12/2020", 2.7));
        data.add(new ValueDataEntry("04/12/2020", 4.1));

        // Set the data for line chart
        cartesian.data(data);
        Set set = Set.instantiate();
        set.data(data);

        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");
        Line series = cartesian.line(seriesMapping);
        series.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        // Modifying properties of cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        // Modify the UI of the cartesian
        cartesian.yAxis(0).title("m/s");
        cartesian.xAxis(0).title("Day");
        cartesian.background().fill("#00000000");
        cartesian.animation(true);

        return cartesian;
    }

    public Cartesian humidityGraph() {

        // Create and get the cartesian coordinate system for line chart
        Cartesian cartesian = AnyChart.line();

        // Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("30/11/2020", 75));
        data.add(new ValueDataEntry("01/12/2020", 68));
        data.add(new ValueDataEntry("02/12/2020", 89));
        data.add(new ValueDataEntry("03/12/2020", 78));
        data.add(new ValueDataEntry("04/12/2020", 85));

        // Set the data for line chart
        cartesian.data(data);
        Set set = Set.instantiate();
        set.data(data);

        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");
        Line series = cartesian.line(seriesMapping);
        series.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        // Modifying properties of cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        // Modify the UI of the cartesian
        cartesian.yAxis(0).title("%");
        cartesian.xAxis(0).title("Day");
        cartesian.animation(true);
        cartesian.background().fill("#00000000");

        return cartesian;
    }
}
