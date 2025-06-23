package com.example.pagos.pagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private BigDecimal monto;

    private LocalDateTime fechaPago;

    private String metodoPago; // Ej: "Tarjeta", "PayPal", "Transferencia"

    private String estado; // Ej: "Pendiente", "Completado", "Fallido"
}
