package com.albertosoto.magnolia.bigdata.data.generic;

/**
 * com.deicos.lince.math
 * Class HighChartsSerieBean
 * By berto. 24/04/2018
 *
 * Wrapper bean for highcharts, extendable to content2bean
 */
public class HighChartsSerieBean {
    String name;
    Double y;
    Double total;
    String drilldown;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDrilldown() {
        return drilldown;
    }

    public void setDrilldown(String drilldown) {
        this.drilldown = drilldown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
