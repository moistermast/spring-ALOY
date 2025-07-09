package com.sda.java3.ecommerce.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ec_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    protected UUID id;

    @Column(name = "quantity")
    protected Integer quantity;

    @Column(name = "price")
    protected BigDecimal price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    protected Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    protected Product product;
}
