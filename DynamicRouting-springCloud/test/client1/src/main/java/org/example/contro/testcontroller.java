package org.example.contro;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testcontroller {

    @RequestMapping("test")
    public String test(){
        return "test";
    }
}
