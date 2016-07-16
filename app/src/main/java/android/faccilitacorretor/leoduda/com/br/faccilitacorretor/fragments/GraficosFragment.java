package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments;

import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Duda on 15/08/2015.
 */
public class GraficosFragment extends Fragment {

    private PieChart mChart;
    private float[] ydata = {5, 1, 3, 4, 6};
    private String[] xData = {"Porto", "Tokio", "Azul", "Maritima", "Outras"};
    private FloatingActionMenu fab;

    AddSeguradoFragment fragAddSegurado = new AddSeguradoFragment();
    AddSeguradoraFragment fragAddSeguradora = new AddSeguradoraFragment();
    AddApoliceFragment fragAddApolice = new AddApoliceFragment();
    FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Floating Action Button com menu
        FloatingActionButton fabSegurado = (FloatingActionButton) view.findViewById(R.id.fab2);
        FloatingActionButton fabSeguradora = (FloatingActionButton) view.findViewById(R.id.fab3);
        FloatingActionButton fabApolice = (FloatingActionButton) view.findViewById(R.id.fab4);

        fab = (FloatingActionMenu) view.findViewById(R.id.fab);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
            }
        });

        //Sub Menu - FloatingAction Button - addSegurado
        fabSegurado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_main, fragAddSegurado, "fragAddSegurado");
                ft.commit();
            }
        });

        //Sub Menu - FloatingAction Button - addSeguradora
        fabSeguradora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_main, fragAddSeguradora, "fragAddSeguradora");
                ft.commit();
            }
        });

        //Sub Menu - FloatingAction Button - addApolice
        fabApolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_main, fragAddApolice, "fragAddApolice");
                //ft.addToBackStack("backStack");
                ft.commit();
            }
        });

        fab.close(true);


        //Gráfico
        mChart = (PieChart) view.findViewById(R.id.mChart);
        mChart.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        mChart.setDescription("");

        //configurando o gráfico
        mChart.setUsePercentValues(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(30);
        mChart.setTransparentCircleRadius(10);
        mChart.animateX(2000);

        //rotação do gráfico
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        //set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(getActivity(), xData[e.getXIndex()] +
                        " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        return view;
    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < ydata.length; i++)
            yVals1.add(new Entry(ydata[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        //create a pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(3);

        //add colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //instanciate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.highlightValue(null);
        mChart.invalidate();

    }
}
