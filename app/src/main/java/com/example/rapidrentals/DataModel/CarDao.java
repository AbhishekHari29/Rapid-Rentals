package com.example.rapidrentals.DataModel;

import java.util.List;

public interface CarDao {
    void getCar(Car car);
    void getCarList(List<Car> carList);
    void getBoolean(Boolean result);
}
