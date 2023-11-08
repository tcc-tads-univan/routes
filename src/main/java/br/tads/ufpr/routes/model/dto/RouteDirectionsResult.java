package br.tads.ufpr.routes.model.dto;

import java.util.List;

public record RouteDirectionsResult(List<String> waypoints, String destination) {
}
