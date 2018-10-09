package com.example.danilwelter.ninjapizzacourier.Model.Entities;

public class SheduleItem {
    private int _id;

    private String _userLogin;

    private int _year;

    private int _weekOfYear;

    private String _monday, _tuesday, _wednesday, _thursday, _friday, _saturday, _sunday;

    public SheduleItem(int _id, String _userLogin, int _year, int _weekOfYear, String _monday, String _tuesday, String _wednesday, String _thursday, String _friday, String _saturday, String _sunday) {
        this._id = _id;
        this._userLogin = _userLogin;
        this._year = _year;
        this._weekOfYear = _weekOfYear;
        this._monday = _monday;
        this._tuesday = _tuesday;
        this._wednesday = _wednesday;
        this._thursday = _thursday;
        this._friday = _friday;
        this._saturday = _saturday;
        this._sunday = _sunday;
    }

    //region getters and setters
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_userLogin() {
        return _userLogin;
    }

    public void set_userLogin(String _userLogin) {
        this._userLogin = _userLogin;
    }

    public int get_year() {
        return _year;
    }

    public void set_year(int _year) {
        this._year = _year;
    }

    public int get_weekOfYear() {
        return _weekOfYear;
    }

    public void set_weekOfYear(int _weekOfYear) {
        this._weekOfYear = _weekOfYear;
    }

    public String get_monday() {
        return _monday;
    }

    public void set_monday(String _monday) {
        this._monday = _monday;
    }

    public String get_tuesday() {
        return _tuesday;
    }

    public void set_tuesday(String _tuesday) {
        this._tuesday = _tuesday;
    }

    public String get_wednesday() {
        return _wednesday;
    }

    public void set_wednesday(String _wednesday) {
        this._wednesday = _wednesday;
    }

    public String get_thursday() {
        return _thursday;
    }

    public void set_thursday(String _thursday) {
        this._thursday = _thursday;
    }

    public String get_friday() {
        return _friday;
    }

    public void set_friday(String _friday) {
        this._friday = _friday;
    }

    public String get_saturday() {
        return _saturday;
    }

    public void set_saturday(String _saturday) {
        this._saturday = _saturday;
    }

    public String get_sunday() {
        return _sunday;
    }

    public void set_sunday(String _sunday) {
        this._sunday = _sunday;
    }
    //endregion



}
