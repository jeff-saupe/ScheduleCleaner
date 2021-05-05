# ScheduleCleaner

![Comparison](comparison.gif)

## Usage
There are two ways to use the ScheduleCleaner.

### Option 1: Generate a local .ics file
Download and start **run.bat** from the releases or execute the JAR via command line:

> java -jar ScheduleCleaner.jar

The generated .ics file can be imported to your desired calendar application.

### Option 2: Import  the schedule via URL
This option enables **live updates** on changes.

1. Within your calendar application, locate the settings where you can add a calendar from URL. <br>
   [Guide for Google Calendar](https://support.google.com/calendar/answer/37100#:~:text=Use%20a%20link%20to%20add%20a%20public%20calendar)
2. Use the following URL and replace **\<centuria\>** and **\<semester\>** with your corresponding details: <br>
   >`http://schedulecleaner.nak.coderesting.dev/cleaned-schedule/<centuria>-<semester>.ics`

## Add exceptions for specific events
Soon...
