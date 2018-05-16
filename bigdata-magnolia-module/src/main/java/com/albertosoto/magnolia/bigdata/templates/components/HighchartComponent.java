package com.albertosoto.magnolia.bigdata.templates.components;

import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.ui.form.config.OptionBuilder;
import info.magnolia.ui.form.config.SelectFieldBuilder;
import info.magnolia.ui.form.config.TabBuilder;
import info.magnolia.ui.framework.config.UiConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * backoffice-magnolia
 * com.deicos.magnolia.blossom.templates.components
 * Created by Alberto Soto Fernandez in 22/05/2017.
 * Description:
 * Dummy component for highcharts that will load R data from rest
 * TODO: By now, the script loaded is static, can be improved
 */
@Controller
@Template(title = "Highcharts BigData (Blossom)", id = "bigDataModule:components/highchart-bltk")
@TemplateDescription("Highcarts loading data from R")
public class HighchartComponent {

    @TabFactory("Content")
    public void contentTab(UiConfig cfg, TabBuilder tab) {
        SelectFieldBuilder chartType = cfg.fields.select("chartType").label("Chart Type");
        chartType.options(new OptionBuilder().label("Area").value("area"));
        chartType.options(new OptionBuilder().label("Column").value("column"));
        chartType.options(new OptionBuilder().label("Bar").value("bar"));
        tab.fields(
                cfg.fields.text("title").label("Title"),
                cfg.fields.text("subtitle").label("Subtitle"),
                cfg.fields.text("yaxisLabel").label("Vertical Label (Y Axis)"),
                chartType
        );
    }

    @RequestMapping(value = "/highchart-bltk", method = RequestMethod.GET)
    public String viewForm(ModelMap model, HttpSession session, HttpServletRequest request, Node content) {
        //model.put("shoppingCart", shoppingCart);
        return "components/highchart-btk.ftl";
    }
}
