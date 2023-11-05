package br.tads.ufpr.routes.controllers;

import br.tads.ufpr.routes.model.dto.SaveUserAddressEvent;
import br.tads.ufpr.routes.services.AddressService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${rabbit.listener.queue}")
public class RoutesListener {
    private static final Logger log = LoggerFactory.getLogger(RoutesListener.class);
    private final AddressService service;

    public RoutesListener(AddressService service) {
        this.service = service;
    }

    @RabbitHandler
    public void saveStudentAddress(@Payload SaveUserAddressEvent event) {
        log.info("{}", event);
        this.service.saveUserAddress(event);
    }
}