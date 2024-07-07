package pl.dawad.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- user form data ---
    private String name;
    private String email;
    private String phone;
    private String userType;
    private String roofType;
    private String roofConstruction;
    private String message;
    private BigDecimal expectedPvPower;
    private BigDecimal energyConsumptionPerYear;
    private LocalDate calculationDate;

    // --- calculation data ---
    private BigDecimal proposedPvPower;
    private String mountType;
    private String inverter;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private BigDecimal investmentReturnInYears;
}

