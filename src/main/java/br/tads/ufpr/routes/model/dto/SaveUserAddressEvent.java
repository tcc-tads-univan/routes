package br.tads.ufpr.routes.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaveUserAddressEvent(
        @NotNull @Positive Long userId,
        @NotBlank String placeId,
        Long relatedTo
) {
}
