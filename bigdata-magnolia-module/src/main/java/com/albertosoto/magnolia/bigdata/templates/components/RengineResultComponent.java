package com.albertosoto.magnolia.bigdata.templates.components;

import com.albertosoto.magnolia.bigdata.spring.RenjinHelper;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.ui.form.config.OptionBuilder;
import info.magnolia.ui.form.config.SelectFieldBuilder;
import info.magnolia.ui.form.config.TabBuilder;
import info.magnolia.ui.framework.config.UiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@Controller
@Template(title = "R-Script Result (Blossom)", id = "bigDataModule:components/rscript")
@TemplateDescription("Executes selected r-script")
public class RengineResultComponent {
    private static final Logger log = LoggerFactory.getLogger(RengineResultComponent.class);

    @TabFactory("Content")
    public void contentTab(UiConfig cfg, TabBuilder tab) {
        SelectFieldBuilder optionField = cfg.fields.select("script").label("Stats Repository");
        RenjinHelper data = RenjinHelper.getInstance();
        try {
            for (Node node : data.getScriptNodes()) {
                //String name = PropertyUtil.getPropertyOrNull(node, RenjinHelper.R_SCRIPT_NAME_PROPERTY).getString();
                String name = node.getPath();
                String path = node.getPath();
                optionField.options(new OptionBuilder().label(name).value(path));
            }
        } catch (Exception e) {
            log.error("No hay scripts!!!", e);
        }
        tab.fields(
                cfg.fields.text("title").label("Title"),
                cfg.fields.text("subtitle").label("Subtitle"),
                cfg.fields.checkbox("showScript").label("Show executed Script"),
                optionField
        );
    }

    @RequestMapping(value = "/rscript-bltk", method = RequestMethod.GET)
    public String viewForm(ModelMap model, HttpSession session, HttpServletRequest request, Node content) {
        try{
            String scriptPath = PropertyUtil.getPropertyOrNull(content,"script").getString();
            boolean showScript = PropertyUtil.getBoolean(content,"showScript",false);
            RenjinHelper data = RenjinHelper.getInstance();
            model.put("result",data.executeScript(scriptPath));
            if (showScript){
                model.put("scriptContent",data.loadScript(scriptPath));
            }
        }catch (Exception e){
            log.error("No script selected or error executing it");
            model.put("result","No valid script selected");
        }
        return "components/rscript-btk.ftl";
    }
}
