package de.saupe.jeff.schedulecleaner.calendar;

import de.saupe.jeff.schedulecleaner.calendar.exceptions.AttributeNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalendarComponent {
    public enum ComponentType {
        VCALENDAR, VTIMEZONE, VEVENT, VTODO, VJOURNAL, VFREEBUSY, VALARM, DAYLIGHT, STANDARD
    }

    @Getter
    private final ComponentType type;
    @Getter
    @Setter
    private boolean excluded = false;
    @Getter
    @Setter
    private List<CalendarAttribute> attributes = new ArrayList<>();
    @Getter
    @Setter
    private List<CalendarComponent> components = new ArrayList<>();

    public CalendarComponent(ComponentType type) {
        this.type = type;
    }

    public CalendarAttribute getAttribute(String name) throws AttributeNotFoundException {
        for (CalendarAttribute attribute : attributes) {
            if (StringUtils.containsIgnoreCase(attribute.getName(), name)) {
                return attribute;
            }
        }
        throw new AttributeNotFoundException(name);
    }

    @Override
    public String toString() {
        if (!excluded) {
            StringBuilder builder = new StringBuilder();

            // Begin component
            builder.append("BEGIN:")
                    .append(type.name())
                    .append("\n");

            // Properties
            for (CalendarAttribute attribute : attributes) {
                builder.append(attribute.getName())
                        .append(":")
                        .append(attribute.getValue())
                        .append("\n");
            }

            // Sub-Components
            for (CalendarComponent component : components) {
                builder.append(component.toString());
            }

            // End component
            builder.append("END:")
                    .append(type.name())
                    .append("\n");

            return builder.toString();
        }
        return "";
    }
}