package com.handicraft.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
/**
 * Created by 고승빈 on 2017-07-10.
 */
public class ResponseStatus {


    public Map responseSuccess(Object object) {

        Map map = new HashMap<String , Object>();
        map.put("status" , StatusType.SUCCESS);
        map.put("result" , object);
        return map;
    }

    public Map responseError(String message) {

        Map map = new HashMap<String , Object>();
        map.put("status" , StatusType.ERROR);
        map.put("result" , message);
        return map;
    }
}
