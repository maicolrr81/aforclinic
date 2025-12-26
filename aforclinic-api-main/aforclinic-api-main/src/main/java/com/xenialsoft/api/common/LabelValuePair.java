package com.xenialsoft.api.common;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LabelValuePair {

    private final String label;
    private final Object value;

    @Override
    public String toString() {
        return String.valueOf(label) + "=\"" + String.valueOf(value) + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LabelValuePair that = (LabelValuePair) o;
        return Objects.equals(label, that.label) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, value);
    }

}
