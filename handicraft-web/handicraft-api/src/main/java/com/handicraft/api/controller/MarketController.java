package com.handicraft.api.controller;

import com.handicraft.core.service.MarketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@RestController
@Api(value = "market" , description = "Market API")
public class MarketController {

    @Autowired
    MarketService marketService;




}
