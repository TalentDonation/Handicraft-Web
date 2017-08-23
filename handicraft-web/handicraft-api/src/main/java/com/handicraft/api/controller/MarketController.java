package com.handicraft.api.controller;

import com.handicraft.core.dto.Market;
import com.handicraft.core.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@RestController
@Api(value = "market" , description = "Market API")
public class MarketController {

    @Autowired
    MarketService marketService;

    @GetMapping("/market/{mid}")
    @ApiOperation(value = "" , notes = "find Market By Mid")
    public Market findMarketByMid(@PathVariable("mid") long mid)
    {
        return marketService.findMarketByMid(mid);
    }


}
