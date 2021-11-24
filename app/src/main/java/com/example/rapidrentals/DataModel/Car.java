package com.example.rapidrentals.DataModel;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Car {

    // Database
    private static final String CARS_DB = "Cars";
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(CARS_DB);

    // Cars Fields
    private String id;
    private String owner;
    private String brand;
    private String model;
    private String type;
    private String fuel;
    private String transmission;
    private int numOfSeats;
    private int year;
    private String regNumber;

    //Rental Fields
    private float rentPerDay;
    private boolean carAvailable;
    private boolean driverAvailable;
    private Location location;

    // Constructors
    public Car() {
    }

    public Car(String id, String owner, String brand, String model, String type, String fuel, String transmission, int numOfSeats, int year, String regNumber, float rentPerDay, boolean carAvailable, boolean driverAvailable, Location location) {
        this.id = id;
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.fuel = fuel;
        this.transmission = transmission;
        this.numOfSeats = numOfSeats;
        this.year = year;
        this.regNumber = regNumber;
        this.rentPerDay = rentPerDay;
        this.carAvailable = carAvailable;
        this.driverAvailable = driverAvailable;
        this.location = location;
    }

    public static String generateCarId() {
        return databaseReference.push().getKey();
    }

    // Main Functions
    public static void getCarsByOwner(String owner, CarDao carDao) {
        databaseReference.orderByChild("owner").equalTo(owner).addListenerForSingleValueEvent(getCarListListener(carDao));
    }

    public static void getCarsByType(String type, CarDao carDao) {
        databaseReference.orderByChild("type").equalTo(type).addListenerForSingleValueEvent(getCarListListener(carDao));
    }

    public static void getCarById(String carId, CarDao carDao) {
        Car car = new Car();
        car.setId(carId);
        car.getCarById(carDao);
    }

    public void getCarById(CarDao carDao) {
        databaseReference.child(id).addListenerForSingleValueEvent(getCarListener(carDao));
    }

    public void addCar(CarDao carDao) {
        databaseReference.child(id).setValue(this).addOnCompleteListener(getBooleanListener(carDao));
    }

    public void deleteCar(CarDao carDao) {
        databaseReference.child(id).removeValue().addOnCompleteListener(getBooleanListener(carDao));
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", fuel='" + fuel + '\'' +
                ", transmission='" + transmission + '\'' +
                ", numOfSeats=" + numOfSeats +
                ", year=" + year +
                ", regNumber='" + regNumber + '\'' +
                ", rentPerDay=" + rentPerDay +
                ", carAvailable=" + carAvailable +
                ", driverAvailable=" + driverAvailable +
                ", location=" + location +
                '}';
    }

    // Listeners
    private static OnCompleteListener<Void>  getBooleanListener(CarDao carDao) {
        return new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                carDao.getBoolean(task.isSuccessful());
            }
        };
    }

    private static ValueEventListener getCarListListener(CarDao carDao) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Car> carList = new ArrayList<>();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                    carList.add(snapshot1.getValue(Car.class));
                carDao.getCarList(carList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                carDao.getCarList(null);
            }
        };
    }

    private static ValueEventListener getCarListener(CarDao carDao) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carDao.getCar(snapshot.getValue(Car.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                carDao.getCar(null);
            }
        };
    }

    // Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public float getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(float rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public boolean isCarAvailable() {
        return carAvailable;
    }

    public void setCarAvailable(boolean carAvailable) {
        this.carAvailable = carAvailable;
    }

    public boolean isDriverAvailable() {
        return driverAvailable;
    }

    public void setDriverAvailable(boolean driverAvailable) {
        this.driverAvailable = driverAvailable;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
