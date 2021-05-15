# 🗓️ Schedule Cleaner

[![NAK Tools](https://img.shields.io/badge/NAK%20Tools-member-blue)](https://nak.coderesting.dev/)
[![License](https://img.shields.io/badge/license-MIT-brightgreen)](https://github.com/jeff-saupe/ScheduleCleaner/blob/master/LICENSE)
[![Issues](https://img.shields.io/github/issues/jeff-saupe/ScheduleCleaner)](https://github.com/jeff-saupe/ScheduleCleaner/issues)
[![Tag](https://img.shields.io/github/v/release/jeff-saupe/ScheduleCleaner)](https://github.com/jeff-saupe/ScheduleCleaner/releases)

![Comparison](comparison.gif)

## 📖 Usage
There are two ways to use the **Schedule Cleaner**.

### Option 1: Import or download the schedule via URL
Adding the URL to your calendar application allows **live updates** on changes.

1. Within your calendar application, locate the settings where you can add a calendar by URL. <br>
   - [Guide for Google Calendar](https://support.google.com/calendar/answer/37100#:~:text=Use%20a%20link%20to%20add%20a%20public%20calendar)
   - **Note**: With Google Calendar, it can take up to [12 hours until the calendar is synchronised again.](https://support.google.com/calendar/answer/37100?hl=en&ref_topic=1672445/#:~:text=It%20might%20take%20up%20to%2012%20hours%20for%20changes%20to%20show%20in%20your%20Google%20Calendar.)
2. Use the following URL and replace **\<centuria\>** and **\<semester\>** with your corresponding details: <br>
   - **Alternative 1:** `http://schedulecleaner.nak.coderesting.dev/cleaned-schedule/<centuria>_<semester>.ics`
   - **Alternative 2:**
`https://schedule-cleaner.herokuapp.com/cleaned-schedule/<centuria>_<semester>.ics`

**The URL can also just be used to download the ICS file.**

### Option 2: Build the .ics file locally via Command Line
Download and start **run.bat** from the releases or execute the JAR via command line:

`java -jar ScheduleCleaner.jar`

The generated .ics file can be imported to your desired calendar application.

## 🛠️ Apply additional fixes
Within the class `ScheduleCleaner.java` there is a method called `initFixes()`.
There you can add two types of fixes (both are **case-insensitive**):

### Replace texts
This fix replaces a text of an event with another text.

```java
ReplaceText replaceText = new ReplaceText();
replaceText.setOldText("Tech.Grundlagen der Informatik 2");
replaceText.setNewText("TGdI");

addFix(replaceText);
```

This fix will check all attributes, e.g. the title, of all events all and replaces any occurrence
of the phrase `Tech.Grundlagen der Informatik 2` with `TGdI`.

### Exclude events
This fix is used to exclude specific events.

```java
ExcludeEvent excludeEvent = new ExcludeEvent();
excludeEvent.addParameter("Text1");
excludeEvent.addParameters("Text2", "Text3") // An infinite amount of parameters can be added

addFix(excludeEvent);
```

This fix will exclude any events that **contain** the phrases `Text1`,`Text2` **and** `Text3`.


## 💡 Contribute
Feel free to submit pull requests, open new issues or just spread the word about this [NAK Tool](https://nak.coderesting.dev/).
