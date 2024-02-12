package com.ticketapplication.controller;

import com.ticketapplication.entity.aCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import com.ticketapplication.repositories.aCustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class aCustomerController {

    @Autowired
    aCustomerRepository aCustomerRepository;
    @Autowired
    exportBranchList exportBranchList;

    @GetMapping("/addbranch")
    public String showaddbranchForm(Model model) {

        model.addAttribute("aCustomer", new aCustomer());
        return "add_aCustomer";
    }

    @PostMapping("/addbranch")
    public String registerNewNranch(@ModelAttribute("aCustomer") aCustomer aCustomer) {

        aCustomerRepository.save(aCustomer);
        return "redirect:/branch_list?success";

    }

    @GetMapping("/branch_list")
    public String showBranches(@RequestParam(name = "searchValue", required = false) String searchValue,
                               @RequestParam(name = "searchid", required = false) Integer id,
                               Model model
    ) {
        List<aCustomer> allbranches;

        if (id != null && id != 0) {
            Optional<aCustomer> customerOptional = aCustomerRepository.findById(id);
            aCustomer singleCustomer = customerOptional.orElse(null);
            List<aCustomer> aCustomerList =
                    singleCustomer != null ? Collections.singletonList(singleCustomer) : new ArrayList<>();
            model.addAttribute("resultList", aCustomerList);
        }
        if (searchValue != null && !searchValue.isEmpty()) {
            List<aCustomer> allbranchesbysearch = aCustomerRepository.findBranches(searchValue);
            model.addAttribute("aCustomer", allbranchesbysearch);
        } else {
            allbranches = aCustomerRepository.findAll();
            model.addAttribute("aCustomer", allbranches);
        }
        return "branch_list";
    }

    @GetMapping("branch_list/{id}")
    public String showBranchUpdateForm(@PathVariable(name = "id") Integer id, Model model) {

        try {
            Optional<aCustomer> customerOptional = aCustomerRepository.findById(id);
            if (customerOptional.isPresent()) {
                aCustomer aCustomer = customerOptional.get();
                model.addAttribute("aCustomer", aCustomer);
                return "update_branch";
            } else {
                throw new RuntimeException("customer not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error_id";
        }
    }

    @PostMapping("/branch_list")
    public String updateBranch(@ModelAttribute("aCustomer") aCustomer aCustomerToUpdate) {

        aCustomer existingcustomer = aCustomerRepository.findById(aCustomerToUpdate.getId()).orElse(null);
        if (existingcustomer != null) {
            existingcustomer.setaCustomerId(aCustomerToUpdate.getaCustomerId());
            existingcustomer.setaCustomerAddress(aCustomerToUpdate.getaCustomerAddress());
            existingcustomer.setaCustomerCity(aCustomerToUpdate.getaCustomerCity());
            existingcustomer.setaCustomerEmail(aCustomerToUpdate.getaCustomerEmail());
            existingcustomer.setaCustomerName(aCustomerToUpdate.getaCustomerName());
            existingcustomer.setaCustomerafm(aCustomerToUpdate.getaCustomerafm());
            existingcustomer.setaCustomerPhoneNumber(aCustomerToUpdate.getaCustomerPhoneNumber());
            aCustomerRepository.save(existingcustomer);
        }

        return "redirect:/branch_list?success2";


    }

    @GetMapping("branch_list/export")
    public String exportBranches() {
        exportBranchList.exportbranchList_txt();
        return "redirect:/branch_list";
    }


    @PostMapping("/branch_list/create")
    public String addextraphone(@ModelAttribute("aCustomer") aCustomer updatephone,Model model) {

        aCustomer existingcustomer = aCustomerRepository.findById(updatephone.getId()).orElse(null);
        if (existingcustomer != null) {
            existingcustomer = new aCustomer();
            existingcustomer.setaCustomerId(updatephone.getaCustomerId());
            existingcustomer.setaCustomerAddress(updatephone.getaCustomerAddress());
            existingcustomer.setaCustomerCity(updatephone.getaCustomerCity());
            existingcustomer.setaCustomerEmail(updatephone.getaCustomerEmail());
            existingcustomer.setaCustomerName(updatephone.getaCustomerName());
            existingcustomer.setaCustomerafm(updatephone.getaCustomerafm());
            existingcustomer.setaCustomerPhoneNumber(updatephone.getaCustomerPhoneNumber());
            aCustomerRepository.save(existingcustomer);
        }
        return "redirect:/branch_list?successNew";

    }


    @GetMapping("branch_list/")
    public String showerrorpage() {
        return "error_id";
    }





}
