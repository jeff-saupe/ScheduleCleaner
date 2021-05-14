package de.saupe.jeff.schedulecleaner.calendar;

import de.saupe.jeff.schedulecleaner.calendar.exceptions.PropertyNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarComponent {
    public enum ComponentTypes {
        VCALENDAR, VTIMEZONE, VEVENT, VTODO, VJOURNAL, VFREEBUSY, VALARM, DAYLIGHT, STANDARD
    }

    @Getter
    private final ComponentTypes type;
    @Getter @Setter
    private boolean excluded = false;
    @Getter @Setter
    private Map<String, String> properties = new HashMap<>();
    @Getter @Setter
    private List<CalendarComponent> subComponents = new ArrayList<>();

    public CalendarComponent(ComponentTypes type) {
        this.type = type;
    }

    public Map.Entry<String, String> getEntry(String property) throws PropertyNotFoundException {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            if (StringUtils.containsIgnoreCase(entry.getKey(), property)) {
                return entry;
            }
        }
        throw new PropertyNotFoundException(property);
    }

    @Override
    public String toString() {
        if (!excluded) {
            StringBuilder builder = new StringBuilder();

            // Begin component
            builder.append("BEGIN:" + type.name() + "\n");

            // Properties
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                builder.append(entry.getKey() + ":" + entry.getValue() + "\n");
            }

            // Sub-Components
            for(CalendarComponent component : subComponents) {
                builder.append(component.toString());
            }

            // End component
            builder.append("END:" + type.name() + "\n");

            return builder.toString();
        }
        return "";
    }
}