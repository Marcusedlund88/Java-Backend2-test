package com.example.Rest.Controller;

import com.example.Rest.POJO.Customer;
import com.example.Rest.POJO.Item;
import com.example.Rest.POJO.Order;
import com.example.Rest.Repository.CustomerRepo;
import com.example.Rest.Repository.ItemRepo;
import com.example.Rest.Repository.OrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderRepo orderRepo, CustomerRepo customerRepo, ItemRepo itemRepo){
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
    }
    @RequestMapping("/orders")
    @ResponseBody
    public List<Order> getAllOrder(){
        return orderRepo.findAll();
    }
    @RequestMapping("orders/getById")
    public String getCustomersByIdForm(){
        return "getOrderById";
    }

    @RequestMapping("orders/{id}")
    public String findById(@PathVariable long id){
        Order orders = orderRepo.findById(id).get();
/*        Order result = orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);*/
        return "orders.html";
    }
    @RequestMapping("orders/{id}/delete")
    public String deleteById(@PathVariable long id){
        Order order = orderRepo.findById(id).get();
        orderRepo.deleteById(id);
        return "deleteOrder.html";
    }

    @PostMapping(value = "/orders/buy/")
    public String buyItem(@RequestBody Order o){
        try{
            orderRepo.save(o);
            log.info("New order was successfully created.");
        }catch (Exception e) {
            log.error("Could not create new orderr. " + e.getMessage());
        }
        return "orders";
    }
    @RequestMapping("orders/{id}/create")
    public String createNewOrderByCustomer(@PathVariable long id){
        Customer customer = customerRepo.findById(id).get();

        List<Item> items = itemRepo.findAll();
        return "placeOrderByCustomerId";
    }

    @PostMapping(value = "/orders/buy/{id}")
    public String testBuyItem(@PathVariable long id, @RequestBody String itemJson){
        try{
            log.info(itemJson);

            Customer customer = customerRepo.findById(id).get();

            ObjectMapper objectMapper = new ObjectMapper();
            List<Item> list = new ArrayList<>();
            List<Item> items = new ArrayList<>();
            try {
                list = objectMapper.readValue(itemJson, new TypeReference<List<Item>>(){});
                for(int i =0; i < list.size(); i++){
                    log.info(list.get(i).toString());
                    items.add(new Item(list.get(i).getId(), list.get(i).getName(), list.get(i).getPrice()));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


            LocalDate currentDate = LocalDate.now();
            Order order = new Order();
            order.setDate(currentDate);
            order.setCustomer(customer);
            order.setItems(items);
            orderRepo.save(order);
            log.info("New order was successfully created.");
        }catch (Exception e) {
            log.error("Could not create new orderr. " + e.getMessage());
        }
        return "redirect:/orders";
    }
}








