package com.halifaxcarpool.driver.business.beans;

import com.halifaxcarpool.commons.business.authentication.encrypter.IPasswordEncrypter;
import com.halifaxcarpool.commons.business.authentication.encrypter.PasswordEncrypterImpl;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.dao.IUserDao;

public class Driver extends User {

    private final IPasswordEncrypter passwordEncrypter = new PasswordEncrypterImpl();
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
    private Integer driverApprovalStatus;

    public Driver() {
        this.driverApprovalStatus = 0;
    }

    public Driver(Builder builder) {
        this.driverEmail = builder.driverEmail;
        this.driverName = builder.driverName;
        this.driverId = builder.driverId;
    }

    public Driver(Integer driverId, String driverEmail, String driverPassword, String driverLicense,
                  String driverName, String registeredVehicleNumber, String licenseExpiryDate, String vehicleName,
                  String vehicleModel, String vehicleColor, Integer driverApprovalStatus) {
        this.driverId = driverId;
        this.driverEmail = driverEmail;
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

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getRegisteredVehicleNumber() {
        return registeredVehicleNumber;
    }

    public void setRegisteredVehicleNumber(String registeredVehicleNumber) {
        this.registeredVehicleNumber = registeredVehicleNumber;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
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


    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public void setDriverApprovalStatus(Integer driverApprovalStatus) {
        this.driverApprovalStatus = driverApprovalStatus;
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

    @Override
    public void registerUser(IUserDao userDao) throws Exception {
        String driverEmailUnique = "driver_email_UNIQUE";
        String errorMessage = "Some error has occurred";
        String exceptionMessage = "Driver already exists";
        String encryptedPassword = passwordEncrypter.encrypt(this.driverPassword);
        setDriverPassword(encryptedPassword);
        try {
            userDao.registerUser(this);
        } catch (Exception e) {
            if(e.getMessage().contains(driverEmailUnique)) {

                throw new Exception(exceptionMessage);
            }
            else {
                throw new Exception(errorMessage);
            }
        }
    }

    @Override
    public boolean updateUser(IUserDao userDao) {
        return userDao.updateUser(this);
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
