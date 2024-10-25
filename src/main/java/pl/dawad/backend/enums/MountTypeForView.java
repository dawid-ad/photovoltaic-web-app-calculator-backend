package pl.dawad.backend.enums;

import lombok.Getter;

@Getter
public enum MountTypeForView {
    SLANT_ROOF("System montażowy dla dachów skośnych"),
    FLAT_ROOF("System balastowy (dla dachów płaskich)"),
    GROUND("System montażowy dla montażu na gruncie");

    private final String displayedName;

    MountTypeForView(String displayedName) {
        this.displayedName = displayedName;
    }

}

