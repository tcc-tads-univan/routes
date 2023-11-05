package br.tads.ufpr.routes.model.mapper;

import br.tads.ufpr.routes.model.dto.SaveStudentAddressEvent;
import br.tads.ufpr.routes.model.entity.UserAddress;

public class UserAddressMapper {
    public static UserAddress eventToEntity(SaveStudentAddressEvent event) {
        UserAddress entity = new UserAddress();

        entity.setPlaceId(event.placeId());
        entity.setStudentId(event.studentId());
        entity.setDriverId(event.driverId());

        return entity;
    }
}
