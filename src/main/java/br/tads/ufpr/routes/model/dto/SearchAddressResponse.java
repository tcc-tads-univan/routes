package br.tads.ufpr.routes.model.dto;

public record SearchAddressResponse(
        String placeId,
        String completeLineAddress
) {
}
