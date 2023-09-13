package com.microsservico.geobaseconsult.controller;

import org.springframework.stereotype.Controller;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

@Controller
public class GeobaseConsultController {

    private static final JOpenCageGeocoder jOCG = new JOpenCageGeocoder("3DYOUR-API-KEY");

    public String APIConsult(double latitude, double longitude) {

        try {

            JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
            request.setLanguage("br");
            request.setLimit(1);

            JOpenCageResponse response = jOCG.reverse(request);

            String addressFormated = response.getResults().get(0).getFormatted();
            System.out.println(addressFormated);

            return addressFormated;
        } catch(Exception e) {
            return null;
        }
    }
    
}
