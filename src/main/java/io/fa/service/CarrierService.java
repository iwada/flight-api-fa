package io.fa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fa.models.Carrier;
import io.fa.repository.CarrierRepository;

@Service
public class CarrierService {

    @Autowired
    CarrierRepository carrierRepository;

    /**
     * Gets and returns a CarrierId
     * 
     * @param carrierId carrierId whose details is required
     * @return Carrier matching that Id or null
     * 
     */
    public Carrier getCarrier(Long carrierId) {
        return carrierRepository.findById(carrierId).orElse(null);
    }

}
