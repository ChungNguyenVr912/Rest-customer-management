package com.rest_cm.controller;

import com.rest_cm.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping({"/","customers"})
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showList(ModelMap modelMap,@PageableDefault(size = 5) Pageable pageable){
//        modelMap.addAttribute("customers", customerService.findAll(pageable));
        return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(){
        return "create";
    }
}
