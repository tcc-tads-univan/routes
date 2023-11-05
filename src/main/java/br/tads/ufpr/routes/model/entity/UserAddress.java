package br.tads.ufpr.routes.model.entity;

import jakarta.persistence.*;

@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String placeId;
    @Column
    private Long relatedTo = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Long getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(Long relatedTo) {
        this.relatedTo = relatedTo;
    }
}
