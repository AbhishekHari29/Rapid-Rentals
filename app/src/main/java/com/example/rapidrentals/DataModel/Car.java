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

    // Fields
    private String id;
    private String owner;
    private String brand;
    private String model;
    private String type;
    private String fuelType;
    private int numOfSeats;
    private int year;
    private String regNumber;

    // Constructors
    public Car() {
    }

    public Car(String id, String owner, String brand, String model, String type, String fuelType, int numOfSeats, int year, String regNumber) {
        this.id = id;
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.fuelType = fuelType;
        this.numOfSeats = numOfSeats;
        this.year = year;
        this.regNumber = regNumber;
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
                ", fuelType='" + fuelType + '\'' +
                ", numOfSeats=" + numOfSeats +
                ", year=" + year +
                ", regNumber='" + regNumber + '\'' +
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

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
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
}
