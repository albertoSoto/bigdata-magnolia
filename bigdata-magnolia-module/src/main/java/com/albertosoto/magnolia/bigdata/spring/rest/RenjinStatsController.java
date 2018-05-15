package com.albertosoto.magnolia.bigdata.spring.rest;

import com.albertosoto.magnolia.bigdata.data.generic.HighChartsSerie;
import com.albertosoto.magnolia.bigdata.data.generic.HighChartsWrapper;
import com.albertosoto.magnolia.bigdata.spring.base.BdMagnoliaBaseController;
import com.albertosoto.magnolia.bigdata.spring.service.RenjinService;
import org.renjin.primitives.matrix.Matrix;
import org.renjin.sexp.Vector;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngine;

/**
 * backoffice-magnolia
 * com.deicos.magnolia.spring.rest
 * Created by Alberto Soto Fernandez in 22/05/2017.
 * Description:
 * Basic dummy stat controller to analyze via R-Engine
 * More info at:
 * http://docs.renjin.org/en/latest/library/moving-data-between-java-and-r-code.html
 */
@RestController
@RequestMapping("/stats")
public class RenjinStatsController extends BdMagnoliaBaseController {

    @RequestMapping(value = "/example", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HighChartsWrapper> getStats() {
        try {
            HighChartsWrapper data = new HighChartsWrapper();
            data.setTitle("R-Engine matrix example data");
            data.setSubtitle("Powering magnolia CMS with R-data");
            RenjinService srv = new RenjinService();
            ScriptEngine engine = srv.getScriptEngine(false);
            //Matrix example mapping to convert for highcharts wrapper and use in presentation
            Vector res = (Vector) engine.eval("matrix(sample.int(100, 30, TRUE), nrow=3)");

            if (res.hasAttributes()) {
                Matrix m = new Matrix(res);
                String message = String.format("Matrix content for %s rows and %s cols", m.getNumRows(), m.getNumCols());
                log.info(message);
                for (int i = 0; i < m.getNumRows(); i++) {
                    HighChartsSerie serie = new HighChartsSerie();
                    serie.setName("Serie " + i);
                    for (int j = 0; j < m.getNumCols(); j++) {
                        serie.getData().add(m.getElementAsDouble(i, j));
                    }
                    data.getSeries().add(serie);
                }
                /*
                Example for getting coeficients from result -  Check renjin API!
                ListVector model = (ListVector)engine.eval("x <- 1:10; y <- x*3; lm(y ~ x)");
                Vector coefficients = model.getElementAsVector("coefficients");
                System.out.println("intercept = " + coefficients.getElementAsDouble(0));
                System.out.println("slope = " + coefficients.getElementAsDouble(1));
                 */
            }
            return getResponse(data);
        } catch (Exception e) {
            return getErrorResponse(new HighChartsWrapper(), e);
        }
    }
}
