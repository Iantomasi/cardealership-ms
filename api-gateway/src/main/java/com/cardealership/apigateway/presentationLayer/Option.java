package com.cardealership.apigateway.presentationLayer;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;


public class Option {

    private String name;
    private String description;
    private Double cost;

    Option() {

    }

    public Option(@NotNull String name, @NotNull String description, @NotNull Double cost) {
        Objects.requireNonNull(this.name = name);
        Objects.requireNonNull(this.description = description);
        Objects.requireNonNull(this.cost = cost);
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public @NotNull Double getCost() {
        return cost;
    }
}
