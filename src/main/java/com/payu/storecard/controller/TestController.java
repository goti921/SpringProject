package com.payu.storecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by simrandeep.singh on 8/28/16.
 */
@Controller
public class TestController {
    @RequestMapping(value = "/miscTest", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    void kafkaTest() {
        System.out.println("YO YO");
    }
}
