package com.epac.vertical_inception.models;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RolConverter implements AttributeConverter<Rol, String> {

    @Override
    public String convertToDatabaseColumn(Rol attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public Rol convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Rol.fromValue(dbData);
    }
}