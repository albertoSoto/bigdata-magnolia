package com.albertosoto.magnolia.bigdata.spring.rest;

import com.albertosoto.magnolia.bigdata.data.generic.DataTableWrapper;
import com.albertosoto.magnolia.bigdata.spring.RenjinHelper;
import com.albertosoto.magnolia.bigdata.spring.base.BdMagnoliaBaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

/**
 * backoffice-magnolia
 * com.deicos.magnolia.spring
 * Created by Alberto Soto Fernandez in 21/05/2017.
 * Description:
 * Spring Rest End point to list and execute actions in the r-engine
 */
@RestController
@RequestMapping("/actions")
public class RenjinActionsController extends BdMagnoliaBaseController {

    @RequestMapping(path = "/list-xml", method = RequestMethod.GET)
    public ResponseEntity<DataTableWrapper<String>> getActionsXml() {
        try {
            RenjinHelper renjin = RenjinHelper.getInstance();
            DataTableWrapper<String> response = new DataTableWrapper<>();
            response.setData(renjin.getScriptPaths());
            return getResponse(response);
        } catch (RepositoryException e) {
            return getErrorResponse(new DataTableWrapper<String>(), e);
        }
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataTableWrapper<String>> getActions() {
        return getActionsXml();
    }


    @RequestMapping(value = "/execute-xml/{fileRQ}", method = RequestMethod.GET)
    public ResponseEntity<DataTableWrapper<String>> executeXML(@PathVariable String fileRQ) {
        try {
            List<String> result = new ArrayList<>();
            RenjinHelper renjin = RenjinHelper.getInstance();
            DataTableWrapper<String> response = new DataTableWrapper<>();
            CollectionUtils.addAll(result, renjin.executeScript(fileRQ));
            response.setData(result);
            return getResponse(response);
        } catch (Exception e) {
            return getErrorResponse(new DataTableWrapper<String>(), e);
        }
    }

    @RequestMapping(value = "/execute/{fileRQ}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataTableWrapper<String>> execute(@PathVariable String fileRQ) {
        return executeXML(fileRQ);
    }
}
