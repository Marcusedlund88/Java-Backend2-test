package com.example.Rest.Controller;

import com.example.Rest.POJO.Customer;
import com.example.Rest.Repository.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {
    private final CustomerRepo customerRepo;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping("/test")
    public String test() {
        return "static";
    }

    public CustomerController(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }

    @RequestMapping("/customers/header")
    @ResponseBody
    public List<String> getHeaders(){
        List<String> headers = new ArrayList<>();

        Field[] fields = Customer.class.getDeclaredFields();
        for (Field field : fields) {
            headers.add(field.getName());
        }
        log.info(headers.toString());
        return headers;
    }

    @RequestMapping("/customers")
    @ResponseBody
    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }
    @GetMapping("/customers/fetch/{id}")
    @ResponseBody
    public Customer fetchCustomerId(@PathVariable long id) {
        Customer customer = customerRepo.findById(id).get();
        return customer;
    }
    @RequestMapping("customers/getById")
    public String getCustomersByIdForm(){
        return "getCustById";
    }

    @RequestMapping("customers/{id}")
    public String findById(@PathVariable long id){
        Customer customer = customerRepo.findById(id).get();
        return "customers";
    }
    @RequestMapping("customers/{id}/delete")
    @ResponseBody
    public List<Customer> deleteById(@PathVariable long id){
        Customer customer = customerRepo.findById(id).get();
        customerRepo.deleteById(id);

        return customerRepo.findAll();
    }

    @RequestMapping("customers/{id}/update")
    public String updateById(@PathVariable long id){
        Customer customer = customerRepo.findById(id).get();
        return "updateCustomer.html";
    }
    @RequestMapping("customers/{id}/update/form")
    public String updateCustomersByForm(@PathVariable long id){
        Customer customer = customerRepo.findById(id).get();
        return "updateCustomerForm.html";
    }

    @RequestMapping(value = "customers/{id}/update/form/execute", method = RequestMethod.PUT)
    public String proceedUpdate(@PathVariable Long id, @RequestParam String name,
                                           @RequestParam String ssn){
        Customer existingCustomer = customerRepo.findById(id).get();
        existingCustomer.setName(name);
        existingCustomer.setSsn(ssn);
        customerRepo.save(existingCustomer);
        return "redirect:/customers";
    }

    @RequestMapping("customers/add")
    public String addCustomersByForm(){
        return "addCust.html";
    }


    @PostMapping("customers/sd")
    public String addCustomer(@RequestParam String name,
                              @RequestParam String ssn, RedirectAttributes redirectAttributes) {
        try {
            Customer newCustomer = new Customer(name, ssn);
            customerRepo.save(newCustomer);
            log.info("POST request was successful.");
        } catch (Exception e) {
            log.error("POST request failed: " + e.getMessage());
            e.printStackTrace(); // Add this line to print the exception stack trace
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding customer. Please try again.");
            return "redirect:/customers/add"; // Change this line to redirect back to the add form in case of an error
        }
        return "redirect:/customers";
    }
}



