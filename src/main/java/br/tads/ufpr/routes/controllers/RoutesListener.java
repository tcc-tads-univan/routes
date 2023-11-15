package br.tads.ufpr.routes.controllers;

import br.tads.ufpr.routes.model.dto.SaveUserAddressEvent;
import br.tads.ufpr.routes.services.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RoutesListener {
    private static final Logger log = LoggerFactory.getLogger(RoutesListener.class);
    private final AddressService service;

    public RoutesListener(AddressService service) {
        this.service = service;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${rabbit.listener.queue}", durable = "true"),
            exchange = @Exchange(value = "univan-messages", type = "direct"),
            key = "SaveUserAddressEvent"
    ))
    public void saveStudentAddress(@Payload SaveUserAddressEvent event) {
        log.info("{}", event);
        this.service.saveUserAddress(event.message());
    }
}
