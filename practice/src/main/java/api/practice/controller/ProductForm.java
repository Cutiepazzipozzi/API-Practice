package api.practice.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductForm {

    private UUID id;

    private String name;

    private int price;

    private int stock;
}
