package pl.dawad.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // user form data
    private String name;
    private String email;
    private String message;
    private String userType;
    private String roofType;
    private String roofConstruction;
    private double expectedPvPower;
    private double energyConsumptionPerYear;

    // calculation data
    private double proposedPvPower;
    private String mountType;
    private String inverter;
    private double priceFrom;
    private double priceTo;
    private double investmentReturnInYears;
}

