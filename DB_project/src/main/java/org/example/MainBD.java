package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainBD {
    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            System.out.println((new File(".")).getAbsolutePath());
        }

        /// add new car
        CarRepository carRepo=new CarsDBRepository(props);
        carRepo.add(new Car("Tesla","Model S", 2019));

        /// update car
        carRepo.update(1,new Car("Tesla","Model 3", 2020));
        System.out.println("Toate masinile din db");
        for(Car car:carRepo.findAll())
            System.out.println(car);
       String manufacturer="Tesla";
        System.out.println("Masinile produse de "+manufacturer);
        for(Car car:carRepo.findByManufacturer(manufacturer))
            System.out.println(car);

        System.out.println("Masinile produse de intre 2010 si 2020");
        for(Car car:carRepo.findBetweenYears(2010,2020))
            System.out.println(car);

    }
}
