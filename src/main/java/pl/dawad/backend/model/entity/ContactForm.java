package pl.dawad.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @Size(max = 1000, message = "Message must not exceed 1000 characters")
    private String message;

    @NotNull(message = "Contact date is required")
    private LocalDateTime contactDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_form_data_id")
    @NotNull(message = "CalculationFormDataId is required")
    private CalculationFormData calculationFormData;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_result_id")
    @NotNull(message = "CalculationResultId is required")
    private CalculationResult calculationResult;

    @Override
    public String toString() {
        return "ContactForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                ", contactDate=" + contactDate +
                ", calculationFormData=" + calculationFormData +
                ", calculationResult=" + calculationResult +
                '}';
    }
}

