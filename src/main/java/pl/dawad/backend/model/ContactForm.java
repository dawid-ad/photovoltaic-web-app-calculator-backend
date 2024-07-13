package pl.dawad.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pl.dawad.backend.enums.MountType;
import pl.dawad.backend.enums.CustomerType;
import pl.dawad.backend.validation.ValidateForCalculation;
import pl.dawad.backend.validation.ValidateForSubmission;

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
    @ValidateForSubmission
    private String name;
    @ValidateForSubmission
    private String email;
    @ValidateForSubmission
    private String phone;
    private String message;
    @ValidateForCalculation
    @ValidateForSubmission
    private BigDecimal expectedPvPower;
    @ValidateForCalculation
    @ValidateForSubmission
    private BigDecimal energyConsumptionPerYear;
    @ValidateForSubmission
    private LocalDate calculationDate;
    @ValidateForCalculation
    @ValidateForSubmission
    private CustomerType customerType;
    @ValidateForCalculation
    @ValidateForSubmission
    private MountType mountType;

    // --- calculation data ---
    @ValidateForSubmission
    private BigDecimal proposedPvPower;
    @ValidateForSubmission
    private String inverter;
    @ValidateForSubmission
    private BigDecimal priceFrom;
    @ValidateForSubmission
    private BigDecimal priceTo;
    @ValidateForSubmission
    private BigDecimal vatTax;
    @ValidateForSubmission
    private BigDecimal investmentReturnInYears;
    @ValidateForSubmission
    private BigDecimal pricePerKw;
    private BigDecimal energyPricePerKwh;

    @Override
    public String toString() {
        return "ContactForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", customerType=" + customerType +
                ", mountType=" + mountType +
                ", message='" + message + '\'' +
                ", expectedPvPower=" + expectedPvPower +
                ", energyConsumptionPerYear=" + energyConsumptionPerYear +
                ", calculationDate=" + calculationDate +
                ", proposedPvPower=" + proposedPvPower +
                ", inverter='" + inverter + '\'' +
                ", priceFrom=" + priceFrom +
                ", priceTo=" + priceTo +
                ", vatTax=" + vatTax +
                ", investmentReturnInYears=" + investmentReturnInYears +
                ", pricePerKw=" + pricePerKw +
                ", energyPricePerKwh=" + energyPricePerKwh +
                '}';
    }
}

