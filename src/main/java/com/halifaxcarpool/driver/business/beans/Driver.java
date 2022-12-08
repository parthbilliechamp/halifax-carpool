package com.halifaxcarpool.driver.business.beans;

public class Driver {

    public int driver_id;
    public String driver_email;
    public String driver_password;
    public String driver_license;
    public String driver_name;
    public String registered_vehicle_number;
    public String license_expiry_date;
    public String vehicle_name;
    public String vehicle_model;
    public String vehicle_color;
    public Integer driver_approval_status;

    public Driver() {
        this.driver_approval_status = 0;
    }

    public Driver(Builder builder) {
        this.driver_email = builder.driverEmail;
        this.driver_name = builder.driverName;
        this.driver_id = builder.driverId;
    }

    public Driver(Integer driver_id, String driver_email, String driver_password, String driver_license, String driver_name, String registered_vehicle_number, String license_expiry_date, String vehicle_name, String vehicle_model, String vehicle_color, Integer driver_approval_status) {
        this.driver_id = driver_id;
        this.driver_email = driver_email;
        this.driver_password = driver_password;
        this.driver_license = driver_license;
        this.driver_name = driver_name;
        this.registered_vehicle_number = registered_vehicle_number;
        this.license_expiry_date = license_expiry_date;
        this.vehicle_name = vehicle_name;
        this.vehicle_model = vehicle_model;
        this.vehicle_color = vehicle_color;
        this.driver_approval_status = driver_approval_status;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_email() {
        return driver_email;
    }

    public void setDriver_email(String driver_email) {
        this.driver_email = driver_email;
    }

    public String getDriver_password() {
        return driver_password;
    }

    public void setDriver_password(String driver_password) {
        this.driver_password = driver_password;
    }

    public String getDriver_license() {
        return driver_license;
    }

    public void setDriver_license(String driver_license) {
        this.driver_license = driver_license;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getRegistered_vehicle_number() {
        return registered_vehicle_number;
    }

    public void setRegistered_vehicle_number(String registered_vehicle_number) {
        this.registered_vehicle_number = registered_vehicle_number;
    }

    public String getLicense_expiry_date() {
        return license_expiry_date;
    }

    public void setLicense_expiry_date(String license_expiry_date) {
        this.license_expiry_date = license_expiry_date;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }

    public void setVehicle_color(String vehicle_color) {
        this.vehicle_color = vehicle_color;
    }

    public Integer getDriver_approval_status() {
        return driver_approval_status;
    }

    public void setDriver_approval_status(Integer driver_approval_status) {
        this.driver_approval_status = driver_approval_status;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driver_id=" + driver_id +
                ", driver_email='" + driver_email + '\'' +
                ", driver_password='" + driver_password + '\'' +
                ", driver_license='" + driver_license + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", registered_vehicle_number='" + registered_vehicle_number + '\'' +
                ", license_expiry_date='" + license_expiry_date + '\'' +
                ", vehicle_name='" + vehicle_name + '\'' +
                ", vehicle_model='" + vehicle_model + '\'' +
                ", vehicle_color='" + vehicle_color + '\'' +
                ", driver_approval_status=" + driver_approval_status +
                '}';
    }

    public static class Builder {
        private int driverId;
        private String driverEmail;
        private String driverName;

        public Builder withDriverId(int driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder withDriverEmail(String driverEmail) {
            this.driverEmail = driverEmail;
            return this;
        }

        public Builder withDriverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        public Driver build() {
            return new Driver(this);
        }
    }
}
