package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {

    @GetMapping("/hello")
        public void hello(Model model){
        log.info("hello..................");

        model.addAttribute("msg", "Hello World!!!");

        }

        //  thymeleaf 연습
    @GetMapping("/ex/ex1")
    public void ex1(Model model){
        List<String> list = Arrays.asList("AAA", "BBB", "CCC", "DDD");

        model.addAttribute("list", list);
    }

    class SampleDTO{

        private String p1, p2, p3;

        public String getP1() {
            return p1;
        }

        public String getP2() {
            return p2;
        }

        public String getP3() {
            return p3;
        }
    }

    @GetMapping("/ex/ex2")
    public void ex2(Model model){
        log.info("ex/ex2............");

        List<String> strList = IntStream.range(1,10)    //문자열 List
                .mapToObj(i -> "Data" + i)
                .collect(Collectors.toList());

        model.addAttribute("list", strList);

        Map<String, String> map = new HashMap<>();
        map.put("A", "AAAA");
        map.put("B", "BBBB");

        model.addAttribute("map", map);

        SampleDTO dto= new SampleDTO();
        dto.p1 = "Value -- p1";
        dto.p2 = "Value -- p2";
        dto.p3 = "Value -- p3";

        model.addAttribute("sampleDTO", dto);
    }

    @GetMapping("/ex/ex3")
    public void ex3(Model model){

        model.addAttribute("arr", Arrays.asList("AAA", "BBB", "CCC"));
    }

}
