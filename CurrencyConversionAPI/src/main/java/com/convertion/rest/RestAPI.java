package com.convertion.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.convertion.services.QueryService;

@RestController
public class RestAPI {

    private static final Logger logger = LoggerFactory.getLogger(RestAPI.class);

    private QueryService queryService;
    private View view;

    @Autowired
    public RestAPI(QueryService queryService, View view) {
        this.queryService = queryService;
        this.view = view;
    }

    @RequestMapping(value="/supportedCurrencies", method=RequestMethod.GET)
    public ModelAndView supportedCurrencies() {
        try {
            return new ModelAndView(view, "data", queryService.supportedCurrencies());
        } catch (Exception e) {
            return createResponseError("400", e.getMessage());
        }
    }
    
    @RequestMapping(value="/convert/{from}/{to}", method=RequestMethod.GET)
    public ModelAndView convert(@PathVariable("from") String from, @PathVariable("to") String to) {
        try {
            return new ModelAndView(view, "data", queryService.convert(from, to));
        } catch (Exception e) {
            return createResponseError("400", e.getMessage());
        }
    }

    private ModelAndView createResponseError(String numero, String message) {
        Map<String, Object> modele = new HashMap<String, Object>();
        modele.put("error", numero);
        modele.put("message", message);
        return new ModelAndView(view, modele);
    }
    
}
