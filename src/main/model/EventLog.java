package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of system events.
// Uses Singleton Design Pattern
// Originally from CPSC210/AlarmSystem
public class EventLog implements Iterable<Event> {
	/** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;
	
	/** 
	 * Prevent external construction.
	 * (Singleton Design Pattern).
	 */
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: Gets instance of EventLog - creates it if it doesn't already exist.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the event log.
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
