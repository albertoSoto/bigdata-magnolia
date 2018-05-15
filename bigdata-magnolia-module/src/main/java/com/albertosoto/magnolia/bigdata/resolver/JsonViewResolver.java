package com.albertosoto.magnolia.bigdata.resolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

/**
 * backoffice-magnolia
 * com.deicos.magnolia
 * Created by Alberto Soto Fernandez in 21/05/2017.
 * Description:
 * http://websystique.com/springmvc/spring-4-mvc-contentnegotiatingviewresolver-example/
 */
public class JsonViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

}