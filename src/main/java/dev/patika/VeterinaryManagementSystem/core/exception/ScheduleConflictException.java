package dev.patika.VeterinaryManagementSystem.core.exception;


/**
 * Exception thrown for schedule conflicts.
 */
public class ScheduleConflictException extends RuntimeException{

    public ScheduleConflictException(String message) {
        super(message);
    }

    public ScheduleConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
