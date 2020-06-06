package ru.softmachine.odyssey.backend.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new Formatter<OffsetDateTime>() {
            @Override
            public OffsetDateTime parse(String text, Locale locale) {
                return OffsetDateTime.parse(text);
            }

            @Override
            public String print(OffsetDateTime object, Locale locale) {
                return object.toString();
            }
        });
        registry.addFormatter(new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return object.toString();
            }
        });
    }
}
