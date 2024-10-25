package pl.dawad.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnergyStorageModelDto {
    private Long id;
    private String model;
    private String power;
}
