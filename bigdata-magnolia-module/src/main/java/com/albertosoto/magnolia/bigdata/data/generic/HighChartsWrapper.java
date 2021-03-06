package com.albertosoto.magnolia.bigdata.data.generic;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * lince-scientific-base
 * com.deicos.lince.app.base.common
 * Created by Alberto Soto Fernandez in 11/04/2017.
 * Description:
 * Wrapper bean for highcharts, extendable to content2bean
 */
public class HighChartsWrapper {
    String title;
    String subtitle;
    List<HighChartsSerie> series = new ArrayList<>();
    List<HighChartsWrapper> drilldown = new ArrayList<>(); //PIE NAVIGATION CHART
    List<String> xSeriesLabels = new ArrayList<>();
    List<Pair<Integer, String>> ySeriesLabels = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<HighChartsWrapper> getDrilldown() {
        return drilldown;
    }

    public void setDrilldown(List<HighChartsWrapper> drilldown) {
        this.drilldown = drilldown;
    }

    public List<HighChartsSerie> getSeries() {
        return series;
    }

    public void setSeries(List<HighChartsSerie> series) {
        this.series = series;
    }

    public List<String> getxSeriesLabels() {
        return xSeriesLabels;
    }

    public void setxSeriesLabels(List<String> xSeriesLabels) {
        this.xSeriesLabels = xSeriesLabels;
    }

    public List<Pair<Integer, String>> getySeriesLabels() {
        return ySeriesLabels;
    }

    public void setySeriesLabels(List<Pair<Integer, String>> ySeriesLabels) {
        this.ySeriesLabels = ySeriesLabels;
    }
}
