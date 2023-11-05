package br.tads.ufpr.routes.model.dto;

public record SaveStudentAddressEvent(
        Long studentId,
        String placeId,
        Long driverId
) {
}
