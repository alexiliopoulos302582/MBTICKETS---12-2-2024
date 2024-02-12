package com.ticketapplication.controller;

import com.ticketapplication.entity.device;
import com.ticketapplication.repositories.deviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class deviceController {


    @Autowired
    deviceRepository deviceRepository;

    @Autowired
    exportSerialNumbers exportSerialNumbers;

    @GetMapping("/device_list")
    public String showDevicesTable(
            @RequestParam(name = "searchValue", required = false) String searchValue, Model model) {

        if (searchValue != null && !searchValue.isEmpty()) {
            List<device> deviceList = deviceRepository.findBySearch(searchValue);
            model.addAttribute("devices", deviceList);
        } else {
            List<device> alldevices = deviceRepository.findAll();
            model.addAttribute("devices", alldevices);
        }
        return "device_list";
    }

    @GetMapping("add_device")
    public String addNewDevice(Model model) {
        model.addAttribute("device", new device());
        return "add_device";
    }

    @PostMapping("/add_device")
    public String saveNewDevice(@ModelAttribute(name = "device") device newdevice) {
        deviceRepository.save(newdevice);
        return "redirect:/device_list?success";
    }

    @GetMapping("/device_list/{id}")
    public String showUpdateForm(@PathVariable(name = "id") Integer id, Model model) {
        Optional<device> optionalDevice = deviceRepository.findById(id);
        device deviceToUpdate = optionalDevice.orElse(null);
        model.addAttribute("device", deviceToUpdate);
        return "update_device";

    }

    @PostMapping("/update_device")
    public String updateDevice(@ModelAttribute("device") device deviceToUpdate) {

     device existingDevice = deviceRepository.findById(deviceToUpdate.getId()).orElse(null);
        if (existingDevice != null) {
            existingDevice.setComments(deviceToUpdate.getComments());
            existingDevice.setDeviceModel(deviceToUpdate.getDeviceModel());
            existingDevice.setBranch(deviceToUpdate.getBranch());
            existingDevice.setSerialNumber(deviceToUpdate.getSerialNumber());
            deviceRepository.save(existingDevice);
        }
        return "redirect:/device_list?success2";


    }

    @GetMapping("/device/export")
    public String exportSerialNumbers() {
        exportSerialNumbers.exportSerialNumbers_txt();
        return "redirect:/device_list";
    }



}
