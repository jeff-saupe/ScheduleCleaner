package de.saupe.jeff.schedulecleaner.fixes;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Fix {
    @Getter
    private final String alias;
    @Getter
    private final int minParameters;
    @Getter
    private final int maxParameters;

    @Getter
    private List<String> parameters = new ArrayList<>();

    public Fix(String alias, int minParameters, int maxParameters) {
        this.alias = alias;
        this.minParameters = minParameters;
        this.maxParameters = maxParameters;
    }

    public FixResponse setParameters(String... parameters) {
        List<String> list = Arrays.asList(parameters);

        FixResponse response = check(list);
        if(response == FixResponse.OK) {
            this.parameters = list;
        }
        return response;
    }

    public abstract void apply(CalendarComponent event);

    /**
     * Used to check whether the parameters align with the requirements of the fix
     * @param parameters List of parameters
     * @return FixResponse
     */
    private FixResponse check(List<String> parameters) {
        if (parameters.size() < minParameters) {
            return FixResponse.TOO_FEW_PARAMETERS;
        }
        if (parameters.size() > maxParameters) {
            return FixResponse.TOO_MANY_PARAMETERS;
        }
        return FixResponse.OK;
    }

    public FixResponse check() {
        return check(this.parameters);
    }
}