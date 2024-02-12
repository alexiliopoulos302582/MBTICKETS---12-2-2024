package com.ticketapplication.controller;

import com.ticketapplication.entity.User;
import com.ticketapplication.entity.aCustomer;
import com.ticketapplication.entity.device;
import com.ticketapplication.entity.ticket;
import com.ticketapplication.repositories.UserRepository;
import com.ticketapplication.repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import com.ticketapplication.repositories.aCustomerRepository;
import com.ticketapplication.repositories.deviceRepository;
import com.ticketapplication.repositories.ticketRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ticketController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    aCustomerRepository aCustomerRepository;
    @Autowired
    deviceRepository deviceRepository;
    @Autowired
    ticketRepository ticketRepository;

    @Autowired
    exportClosedTickets exportClosedTickets;

    @Autowired
    exportOpenTickets exportOpenTickets;

    @GetMapping("/newticket")
    public String showNewTicketPage(
            @RequestParam(name = "phoneNumber",required = false) String phoneNumber,
            @RequestParam(name = "serialNumber",required = false) String serialNumber,
            @RequestParam(name = "id",required = false) Integer id,
            @RequestParam(name = "searchName",required = false) String searchName,
            Model model) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String loggedUser = authentication.getName();
            model.addAttribute("assignedTo", loggedUser);
        } else {
            System.out.println("no logged user");
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            List<aCustomer> aCustomerList = aCustomerRepository.findAllByPhoneNumber(phoneNumber);
            if (aCustomerList.size()==1) {
                model.addAttribute("aCustomerInfo", aCustomerList.get(0));
                model.addAttribute("phonenumber", phoneNumber);
            } else if (aCustomerList.size() > 1) {
                model.addAttribute("aCustomerInfoList", aCustomerList);
                model.addAttribute("multipleResults", true);
                model.addAttribute("phonenumber", phoneNumber);
            }
        }
        if (id != null) {
            aCustomer customer = aCustomerRepository.findById(id).orElse(null);
            if (customer != null) {
                model.addAttribute("aCustomerInfo", customer);
            }
        }
        if (searchName != null && !searchName.isEmpty()) {
            List<aCustomer> aCustomerListbyname = aCustomerRepository.findAllByaCustomerName(searchName);
            model.addAttribute("customersbyname", aCustomerListbyname);
            model.addAttribute("customerbynameDIV", true);
        }

        if (serialNumber != null && !serialNumber.isEmpty()) {
            List<device> deviceList = deviceRepository.findByserialNumber(serialNumber);
            if (deviceList.size() == 1) {
                device deviceModel = deviceList.get(0);
                deviceModel.getDeviceModel();
                model.addAttribute("deviceModelresult", deviceModel.getDeviceModel());
                model.addAttribute("serialNumber", serialNumber);

            }
        }

        List<String> deviceModel = Arrays.asList(
                          "device type one",
                "device type two with wifi",
                "device type three with ethernet",
                "device type four no internet");
        model.addAttribute("deviceModel", deviceModel);

        List<String> category = Arrays.asList(
                "category one", "category 2", "category 3", "category 4", "category 5");
        model.addAttribute("category", category);

        List<String> solution = Arrays.asList(
                "solved", "pending", "response from client");
        model.addAttribute("solution", solution);

        List<String> solutionType = Arrays.asList(
                "replacement", "remotely", "some other");
        model.addAttribute("solutionType", solutionType);



        model.addAttribute("ticket", new ticket());
        return "newticket";
    }

    @PostMapping("/newticket")
    public String saveNewTicket(@ModelAttribute("ticket") ticket ticket   ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        ticket.setCreationDate(dateFormat.format(new Date()));
        ticketRepository.save(ticket);
        return "ticketcreated";

    }

    @GetMapping("/closedtickets")
    public String showClosedTicketsTable(
            @RequestParam(name = "searchValue",required = false) String searchValue,
            Model model,Authentication authentication) {
        UserDetails userDetails2 = (UserDetails) authentication.getPrincipal();
        String loggeduser = userDetails2.getUsername();
        model.addAttribute("username", loggeduser);
        Optional<User> loggedInUser = userRepository.findByEmail(loggeduser);
        User user = loggedInUser.orElseThrow(() -> new RuntimeException("user not found"));
        Integer userid = user.getId();
        model.addAttribute("userid", userid);
        if (searchValue != null && !searchValue.isEmpty()) {
            List<ticket> resulttickets = ticketRepository.findClosedTicketsBySearch(searchValue);
            model.addAttribute("ticket", resulttickets);
        } else {
         List<ticket> tickets = ticketRepository.findAllClosedTickets();
        model.addAttribute("ticket", tickets);
        }
        return "closedtickets";
    }



    @GetMapping("/opentickets")
    public String showOpenTicketsTable(
            @RequestParam(name = "searchValue",required = false) String searchValue,
            Model model,Authentication authentication) {
        UserDetails userDetails1 = (UserDetails) authentication.getPrincipal();
        String loggeduser = userDetails1.getUsername();
        model.addAttribute("username", loggeduser);
        Optional<User> loggedInUser = userRepository.findByEmail(loggeduser);
        User user = loggedInUser.orElseThrow(() -> new RuntimeException("user not found"));
        Integer userid = user.getId();
        model.addAttribute("userid", userid);
        if (searchValue != null && !searchValue.isEmpty()) {
            List<ticket> resulttickets = ticketRepository.findOpenTicketsBySearch(searchValue);
            model.addAttribute("tickets", resulttickets);
        } else {
            List<ticket> tickets = ticketRepository.findAllOpenTickets();
            model.addAttribute("tickets", tickets);
        }
        return "opentickets";
    }

    @GetMapping("/closedtickets/export")
    public String exportclosedtickets() {
        exportClosedTickets.exportClosedTickets_txt();
        return "redirect:/closedtickets";
    }

    @GetMapping("/opentickets/export")
    public String exportopentickets() {
        exportOpenTickets.exportOpenTickets_txt();
        return "redirect:/opentickets";
    }

    @GetMapping("/updateTicket/{id}")
    public String updateticketForm(@PathVariable("id") Long id , Model model) {
        Optional<ticket> optionalTicket = ticketRepository.findById(id);
        ticket ticket = optionalTicket.orElse(null);

        model.addAttribute("ticket", ticket);
        return "update_Ticket";

    }

    @PostMapping("/update")
    public String updateTicket(@ModelAttribute ticket updateticket) {
        ticketRepository.save(updateticket);
        return "redirect:/home";
    }


}
