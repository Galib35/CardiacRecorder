package com.example.cardiacrecorder;

/**
 * Represents a data model for cardiac recordings.
 */
public class DataModel {
    private String systolic;
    private String diastolic;
    private String heart_rate;
    private String date;
    private String time;
    private String comment;

    /**
     * Default constructor.
     */
    public DataModel() {
    }

    /**
     * Parameterized constructor.
     *
     * @param systolic    The systolic value.
     * @param diastolic   The diastolic value.
     * @param heart_rate  The heart rate value.
     * @param date        The date of the recording.
     * @param time        The time of the recording.
     * @param comment     Additional comment for the recording.
     */
    public DataModel(String systolic, String diastolic, String heart_rate, String date, String time, String comment) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heart_rate = heart_rate;
        this.date = date;
        this.time = time;
        this.comment = comment;
    }

    /**
     * Get the systolic value.
     *
     * @return The systolic value.
     */
    public String getSystolic() {
        return systolic;
    }

    /**
     * Set the systolic value.
     *
     * @param systolic The systolic value to set.
     */
    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    /**
     * Get the diastolic value.
     *
     * @return The diastolic value.
     */
    public String getDiastolic() {
        return diastolic;
    }

    /**
     * Set the diastolic value.
     *
     * @param diastolic The diastolic value to set.
     */
    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    /**
     * Get the heart rate value.
     *
     * @return The heart rate value.
     */
    public String getHeart_rate() {
        return heart_rate;
    }

    /**
     * Set the heart rate value.
     *
     * @param heart_rate The heart rate value to set.
     */
    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    /**
     * Get the date of the recording.
     *
     * @return The date of the recording.
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the recording.
     *
     * @param date The date of the recording to set.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the time of the recording.
     *
     * @return The time of the recording.
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the time of the recording.
     *
     * @param time The time of the recording to set.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Get the additional comment for the recording.
     *
     * @return The comment for the recording.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the additional comment for the recording.
     *
     * @param comment The comment for the recording to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
