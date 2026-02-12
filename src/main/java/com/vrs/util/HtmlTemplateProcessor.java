package com.vrs.util;

import org.springframework.core.io.ClassPathResource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HtmlTemplateProcessor {

    public static String fillTemplateFromClasspath(String classpathTemplate, Map<String,String> values) {
        try {
            ClassPathResource r = new ClassPathResource(classpathTemplate);
            byte[] bytes = r.getInputStream().readAllBytes();
            String html = new String(bytes, StandardCharsets.UTF_8);

            for (Map.Entry<String,String> e : values.entrySet()) {
                String placeholder = "{{" + e.getKey() + "}}";
                html = html.replace(placeholder, e.getValue() == null ? "" : e.getValue());
            }
            return html;
        } catch (Exception ex) {
            throw new RuntimeException("Error reading template: " + ex.getMessage(), ex);
        }
    }
}
