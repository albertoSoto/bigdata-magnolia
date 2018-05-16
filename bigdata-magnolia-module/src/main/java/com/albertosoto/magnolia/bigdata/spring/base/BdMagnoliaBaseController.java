package com.albertosoto.magnolia.bigdata.spring.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * backoffice-magnolia
 * com.deicos.magnolia.spring.base
 * Created by Alberto Soto Fernandez in 22/05/2017.
 * Description:
 * Common uses in spring rest controllers
 */
public abstract class BdMagnoliaBaseController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected <T> ResponseEntity<T> getResponse(T object){
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> getErrorResponse(T object, Exception e){
        log.error("Bigdata-magnolia: Controller error at: ",e);
        return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
