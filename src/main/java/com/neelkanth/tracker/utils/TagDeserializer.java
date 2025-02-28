package com.neelkanth.tracker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neelkanth.tracker.model.Tag;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.HashSet;

public class TagDeserializer extends JsonDeserializer<Tag> {
    @Override
    public Tag deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // Read the JSON value as a string
        String name = p.getText();

        // Create a new Tag object with the string value
        // You can set default values for `id` and `notes` or fetch them from a service
        Tag tag = new Tag();
        tag.setName(name);
        tag.setId(null); // Set to null or fetch from a service
        tag.setNotes(new HashSet<>()); // Initialize an empty set of notes

        return tag;
    }
}
