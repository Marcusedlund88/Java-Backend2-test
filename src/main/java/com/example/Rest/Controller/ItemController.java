package com.example.Rest.Controller;

import com.example.Rest.POJO.Item;
import com.example.Rest.Repository.ItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ItemController {
    private final ItemRepo itemRepo;
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    @RequestMapping("/items")
    @ResponseBody
    public List<Item> getAllItems(){
        return itemRepo.findAll();
    }
    @GetMapping("/items/fetch/{id}")
    @ResponseBody
    public Item fetchItemId(@PathVariable long id) {
        Item item = itemRepo.findById(id).get();
        return item;
    }
    @RequestMapping("items/getById")
    public String getCustomersByIdForm(){
        return "getItemById";
    }
    @RequestMapping("items/{id}")
    public String findById(@PathVariable long id){
        Item item = itemRepo.findById(id).get();
        return "items.html";
    }

    @RequestMapping("items/{id}/update")
    public String updateById(@PathVariable long id){
        Item item = itemRepo.findById(id).get();
        return "updateItem.html";
    }
    @RequestMapping("items/{id}/update/form")
    public String updateItemByForm(@PathVariable long id){
        Item item = itemRepo.findById(id).get();

        return "updateItemForm.html";
    }

    @RequestMapping(value = "items/{id}/update/form/execute", method = RequestMethod.PUT)
    public String proceedUpdate(@PathVariable Long id, @RequestParam String name,
                                @RequestParam Double price){
        Item existingItem = itemRepo.findById(id).get();
        existingItem.setName(name);
        existingItem.setPrice(price);
        itemRepo.save(existingItem);
        return "redirect:/items";
    }

    @RequestMapping("items/{id}/delete")
    public String deleteById(@PathVariable long id){
        Item item = itemRepo.findById(id).get();
        itemRepo.deleteById(id);
        return "deleteItem.html";
    }

    @RequestMapping("items/add")
    public String addCustomersByForm(){
        return "addItem.html";
    }


    @PostMapping("items/sd")
    public String addItem(@RequestParam String name,
                              @RequestParam Double price, RedirectAttributes redirectAttributes) {
        try {
            Item newItem = new Item(name, price);
            itemRepo.save(newItem);
            log.info("POST request was successful.");
        } catch (Exception e) {
            log.error("POST request failed: " + e.getMessage());
            e.printStackTrace(); // Add this line to print the exception stack trace
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding customer. Please try again.");
            return "redirect:/items/add"; // Change this line to redirect back to the add form in case of an error
        }
        return "redirect:/items";
    }

}










