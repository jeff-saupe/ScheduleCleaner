# ScheduleCleaner

![Comparison](comparison.gif)

## Usage
There are two ways to use the ScheduleCleaner.

### Option 1: Generate a local .ics file
Download and start **run.bat** from the releases or execute the JAR via command line:

> `java -jar ScheduleCleaner.jar`

The generated .ics file can be imported to your desired calendar application.

### Option 2: Import  the schedule via URL
This option enables **live updates** on changes.

1. Within your calendar application, locate the settings where you can add a calendar from URL. <br>
   [Guide for Google Calendar](https://support.google.com/calendar/answer/37100#:~:text=Use%20a%20link%20to%20add%20a%20public%20calendar)
2. Use the following URL and replace **\<centuria\>** and **\<semester\>** with your corresponding details: <br>
   >`http://schedulecleaner.nak.coderesting.dev/cleaned-schedule/<centuria>-<semester>.ics`

## Apply fixes for specific events
Within the class **`ScheduleCleaner.java`** there is a method called `initOptionalFixes`.
There you can add two kind of fixes:

### TitleUpdate
This fix is used to update or rename the title of an event. _(In the ICS context, this is known as the summary.)_

```java
TitleUpdate titleUpdate = new TitleUpdate(FixMethod.CONTAINS,
        "Tech.Grundlagen der Informatik 2",
        "TGdI");
        
titleUpdates.add(titleUpdate);
```

This fix will check for any events whose title (summary) **contains** the phrase `Tech.Grundlagen der Informatik 2` and replaces it with `TGdI`.

### EventExclusion
This fix is used to exclude a specific event.

```java
EventExclusion eventExclusion = new EventExclusion(FixMethod.CONTAINS,
        "DozentXY");
        
eventExclusions.add(eventExclusion);
```

This fix will check for any events that **contain** the phrase `DozentXY` and exclude it from the file.


### Methods
The following methods can be used for fixes:
``        CONTAINS, EQUALS, ENDS_WITH, STARTS_WITH``