package com.azure.server.Model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private BigDecimal price;
    private String imageUrl;
//    @Min(value = 0, message = "Price should be positive value.")
//    @Min(value = 0, message = "Total Count should be positive value.")
//    private int totalCount;
//    @Min(value = 0, message = "Total sell should be positive value.")
//    private int sold;

}
