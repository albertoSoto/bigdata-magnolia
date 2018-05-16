package com.albertosoto.magnolia.bigdata.spring.service;

import com.albertosoto.magnolia.bigdata.spring.RenjinDataAttribute;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.renjin.primitives.matrix.StringMatrixBuilder;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.StringVector;
import org.renjin.sexp.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * lince-scientific-base
 * com.deicos.lince.app.service
 * Created by Alberto Soto Fernandez in 10/08/2017.
 * Description:
 *
 * Basic service for Rengine.
 * Can be improved!!!!
 *
 * <p>
 * http://docs.renjin.org/en/latest/library/execution-context.html
 * <p>
 * Include packages:
 * http://docs.renjin.org/en/latest/library/using-packages.html
 * http://packages.renjin.org/packages
 * <p>
 * <p>
 * JSON PACKAGE TO INCLUDE:
 * =======================
 * https://cran.r-project.org/web/packages/jsonlite/vignettes/json-apis.html
 * http://packages.renjin.org/package/org.renjin.cran/jsonlite
 * https://cran.r-project.org/web/packages/jsonlite/jsonlite.pdf
 * <p>
 * Encryption
 * https://www.guardsquare.com/en/proguard
 */
@Service
public class RenjinService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());


    private RenjinScriptEngineFactory factory = null;

    /**
     * This allows us to maintain one independent RenjinScriptEngine
     * per thread, allowing your server to handle concurrent requests.
     */
    private static final ThreadLocal<ScriptEngine> ENGINE = new ThreadLocal<>();

    /**
     * Loads renjin, via Lazy init
     * First time will be slow
     *
     * @return renjin script engine
     */
    private void saveInit() {
        // create a script engine manager:
        if (this.factory == null) {
            this.factory = new RenjinScriptEngineFactory();
        }
    }

    /**
     * Valid engine and context load to use with the console
     *
     * @param loadContext if context and all data mus be exposed or not
     * @return Valid ScriptEngine for renjin exposed via threads
     */
    public ScriptEngine getScriptEngine(boolean loadContext) {
        ScriptEngine engine = ENGINE.get();
        if (engine == null) {
            // Create a new ScriptEngine for this thread if one does not exist.
            saveInit();
            engine = factory.getScriptEngine();
            ENGINE.set(engine);
        }
        //context load
        if (loadContext) {
            //Vector data = new Vector() {};
            //engine.put("x", 4);
            //engine.put("y", new double[]{1d, 2d, 3d, 4d});
            //engine.put("z", new DoubleArrayVector(1, 2, 3, 4, 5));
            HashMap ctx = new HashMap();
            ctx.put("hi", "code");
            ctx.put("x", 4);
            engine.put(RenjinDataAttribute.DATA_MATRIX.getItemLabel()
                    , getExposedData(true));
            engine.put(RenjinDataAttribute.DATA_MATRIX_BY_CATEGORY.getItemLabel()
                    , getExposedData(false));
            engine.put(RenjinDataAttribute.CONTEXT.getItemLabel(), ctx);
        }
        return engine;
    }


    /**
     * Casts analysis data to R Data as a Matrix
     *
     * @param concatCategories all categories are join and separated by coma or not
     * @return Lince Data Matrix
     */
    public StringVector getExposedData(boolean concatCategories) {
        try {
            //videotime, name, frames, categories(concat code with ",")
            StringMatrixBuilder data = new StringMatrixBuilder(0, 3);
            List<String> colNames = new ArrayList<>();
            //could be bean attributes
            colNames.add("Col1");
            colNames.add("Col2");
            colNames.add("Col3");
            data.setColNames(colNames);
            String[] categoryNames = null;
            return data.build();
        } catch (Exception e) {
            log.error("Trying to convert lince data to R Vector", e);
        }
        return null;
    }

    /**
     * Tipo de elemento perfecto para retos
     *
     * @return
     */
    public Vector getCountExposedData() {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    /**
     * Loads a file from resource path
     *
     * @param resourcePath located resource file
     * @return FileReader for valid usage with renjin
     */
    private FileReader getFileReader(String resourcePath) {
        try {
            if (StringUtils.isNotEmpty(resourcePath)) {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource(resourcePath).getFile());
                FileReader reader = new FileReader(file);
                return reader;
            }
        } catch (Exception e) {
            log.error("Filereader", e);
        }
        return null;
    }


    /**
     * Loads a simple file with an r script
     */
    public void tryRFile() {
        try {
            ScriptEngine engine = getScriptEngine(false);
            engine.eval(getFileReader("integration/r-lang/function-script.r"));
        } catch (Exception e) {
            log.error("RenjinService", e);
        }
    }


    public void tryExecuteFileByLine() {
        try {
            ScriptEngine engine = getScriptEngine(false);
            Path path = Paths.get(this.getClass().getClassLoader().getResource("integration/r-lang/basic-script.r").toURI());
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {

                engine.eval(line);
            }
        } catch (Exception e) {
            log.error("RenjinService", e);
        }
    }

    /**
     * Creates context safely and inits pom librarys for kappa analysis and others
     * Python reticulate library it's setup too.
     *
     * @param engine null for new instance creation
     * @param output output writer for r-engine
     * @param err    err writer for r-engine
     * @return
     */
    public ScriptEngine setupEngine(ScriptEngine engine, StringWriter output, StringWriter err) {
        if (engine == null) {
            engine = getScriptEngine(true);
        }
        engine.getContext().setWriter(output);
        engine.getContext().setErrorWriter(err);
        try {
            engine.eval("import(java.util.HashMap)");
            engine.eval("library(e1071)");
            engine.eval("library('org.renjin.cran:KappaGUI')");
            engine.eval("library('org.renjin.cran:irr')");
            //engine.eval("library('org.renjin.cran:jsonlite')");
            //engine.eval("library('org.renjin.cran:reticulate')");
            //engine.eval("data(iris)");
            //engine.eval("svmfit <- svm(Species~., data=iris)");
            //((StringArrayVector)engine.eval("svmfit$levels")).values
        } catch (Exception e) {
            output.append(Throwables.getStackTraceAsString(e));
            log.error("RenjinService", e);
        }
        return engine;
    }

    /**
     * Executes renjin commands and returns output
     * Based on http://docs.renjin.org/en/latest/library/capture.html
     *
     * @param input Command content
     * @return console output
     */
    public String executeRenjin(String... input) throws ScriptException {
        String outputTxt = StringUtils.EMPTY;
        try {
            StringWriter output = new StringWriter();
            StringWriter err = new StringWriter();
            ScriptEngine engine = setupEngine(null, output, err);
            for (String line : input) {
                engine.eval(line);
            }
            outputTxt = output.toString();
        } catch (Exception e) {
            //output.append(Throwables.getStackTraceAsString(e));
            log.error("RenjinService", e);
            throw e;
        }
        return outputTxt;
    }

}
