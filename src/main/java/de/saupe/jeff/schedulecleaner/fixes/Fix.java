package de.saupe.jeff.schedulecleaner.fixes;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Fix {
    @Getter
    private final FixMethod method;
    @Getter
    private final int minParameters;
    @Getter
    private final int maxParameters;

    @Getter
    private List<String> parameters = new ArrayList<>();

    public Fix(FixMethod method, int minParameters, int maxParameters) {
        this.method = method;
        this.minParameters = minParameters;
        this.maxParameters = maxParameters;
    }

    public void setParameters(String... parameters) throws IllegalArgumentException {
        List<String> list = Arrays.asList(parameters);
        if (check(list)) {
            this.parameters = list;
        }
    }

    public abstract void apply(CalendarComponent event);

    /**
     * Used to check whether the parameters align with the requirements of the fix
     * @param parameters List of parameters
     * @return 'true' only if the check succeeds
     * @throws IllegalArgumentException if parameters do not align with the requirements
     */
    private boolean check(List<String> parameters) {
        if (parameters.size() < minParameters) {
            throw new IllegalArgumentException(String.format("Too few parameters for '%s'", method.name()));
        }
        if (parameters.size() > maxParameters) {
            throw new IllegalArgumentException(String.format("Too many parameters for '%s'", method.name()));
        }
        return true;
    }

    public boolean check() throws IllegalArgumentException {
        return check(this.parameters);
    }
}