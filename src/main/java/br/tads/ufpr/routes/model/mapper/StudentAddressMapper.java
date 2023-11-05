package br.tads.ufpr.routes.model.mapper;

import br.tads.ufpr.routes.model.dto.SaveUserAddressEvent;
import br.tads.ufpr.routes.model.entity.UserAddress;

public class StudentAddressMapper {
    public static UserAddress eventToEntity(SaveUserAddressEvent event) {
        UserAddress entity = new UserAddress();

        entity.setPlaceId(event.placeId());
        entity.setUserId(event.userId());
        entity.setRelatedTo(event.relatedTo());

        return entity;
    }
}
