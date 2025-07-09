package com.sda.java3.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ec_cart")
public class Cart {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    protected UUID id;

    protected Integer quantity;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    protected Product product;
}
