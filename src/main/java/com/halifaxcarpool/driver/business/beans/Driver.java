package com.halifaxcarpool.driver.business.beans;

public class Driver {

    private int driverId;
    private String driverEmail;
    private String driverPassword;
    private String driverLicense;
    private String driverName;
    private String registeredVehicleNumber;
    private String licenseExpiryDate;
    private String vehicleName;
    private String vehicleModel;
    private String vehicleColor;
    private int driverApprovalStatus;

    public Driver() {
        this.driverApprovalStatus = 0;
    }

    private Driver(Builder builder) {
        this.driverId = builder.driverId;
        this.driverEmail = builder.driverEmail;
        this.driverName = builder.driverName;
    }

    public Driver(int driverId, String driver_email, String driverPassword,
                  String driverLicense, String driverName, String registeredVehicleNumber,
                  String licenseExpiryDate, String vehicleName, String vehicleModel,
                  String vehicleColor, Integer driverApprovalStatus) {
        this.driverId = driverId;
        this.driverEmail = driver_email;
        this.driverPassword = driverPassword;
        this.driverLicense = driverLicense;
        this.driverName = driverName;
        this.registeredVehicleNumber = registeredVehicleNumber;
        this.licenseExpiryDate = licenseExpiryDate;
        this.vehicleName = vehicleName;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.driverApprovalStatus = driverApprovalStatus;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public String getDriverPassword() {
        return driverPassword;
    }

    public void setDriverPassword(String driverPassword) {
        this.driverPassword = driverPassword;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getRegisteredVehicleNumber() {
        return registeredVehicleNumber;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public Integer getDriverApprovalStatus() {
        return driverApprovalStatus;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driver_id=" + driverId +
                ", driver_email='" + driverEmail + '\'' +
                ", driver_password='" + driverPassword + '\'' +
                ", driver_license='" + driverLicense + '\'' +
                ", driver_name='" + driverName + '\'' +
                ", registered_vehicle_number='" + registeredVehicleNumber + '\'' +
                ", license_expiry_date='" + licenseExpiryDate + '\'' +
                ", vehicle_name='" + vehicleName + '\'' +
                ", vehicle_model='" + vehicleModel + '\'' +
                ", vehicle_color='" + vehicleColor + '\'' +
                ", driver_approval_status=" + driverApprovalStatus +
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
