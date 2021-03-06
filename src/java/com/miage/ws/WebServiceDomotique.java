/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.controller.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author olivier
 */
@WebService(serviceName = "WebService")
public class WebServiceDomotique {
    private final Controller wsd = new Controller();
    
    //chaque methode retourne un objet json de la forme : 
    /*
    {
        json : objet json de résultat
        code : code de retour si 0 tout ok si -1 erreur
        message : message d'erreur d'il y a lieu 
    }
    
    */
    
    @WebMethod(operationName = "getListSensors")
    public String getListSensors() {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", wsd.getListSensors());
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "getInfoSensor")
    public String getInfoSensor(@WebParam(name = "id") int id) {
        Map<String,Object> out = new HashMap();
        
        try {
            out.put("json", wsd.getInfoSensor(id));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "getStats")
    public String getStats(@WebParam(name = "id") int id, @WebParam(name = "beginDate") String beginDate, @WebParam(name = "endDate") String endDate) {
        Map<String,Object> out = new HashMap();
        
        try {
            out.put("json", wsd.getStats(id,beginDate,endDate));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "switchPower")
    public String switchPower(@WebParam(name = "id") int id) {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", wsd.switchPower(id));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "changeName")
    public String changeName(@WebParam(name = "id") int id, @WebParam(name = "name") String name) {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", wsd.changeName(id,name));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "changeColor")
    public String changeColor(@WebParam(name = "id") int id, @WebParam(name = "color") String color) {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", wsd.changeColor(id,color));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "changeLuminosity")
    public String changeLuminosity(@WebParam(name = "id") int id, @WebParam(name = "luminosity") int luminosity) {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", wsd.changeLuminosity(id, luminosity));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "changeTemperature")
    public String changeTemperature(@WebParam(name = "id") int id, @WebParam(name = "temperature") int temperature) {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", wsd.changeTemperature(id,temperature));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "addSensor")
    public String addSensor(@WebParam(name = "type") String type, @WebParam(name = "name") String name) {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", wsd.addSensor(name, type));
            out.put("code", 0);
            out.put("message", "");
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    @WebMethod(operationName = "deleteSensor")
    public String deleteSensor(@WebParam(name = "id") int id) {
        Map<String,Object> out = new HashMap();
        try {
            out.put("json", "");
            out.put("code", 0);
            out.put("message", wsd.deleteSensor(id));
        }catch (Exception e) {
            out.put("json", "");
            out.put("code", -1);
            out.put("message", e.getMessage());
        }finally{
            return getJsonFromObject(out);
        }
    }
    
    private String getJsonFromObject(Object o){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(WebServiceDomotique.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }
    
}
