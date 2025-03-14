package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry("Cars by manufacturer {}", manufacturerN);
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement prtStmt = con.prepareStatement("select * from cars where manufacturer = ?")) {
            prtStmt.setString(1, manufacturerN);
            try (ResultSet result = prtStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");

                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry("Cars between years {} and {}", min, max);
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement prtStmt = con.prepareStatement("select * from cars where year between ? and ?")) {
            prtStmt.setInt(1, min);
            prtStmt.setInt(2, max);
            try (ResultSet result = prtStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");

                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving car {} ",elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement prtStmt = con.prepareStatement("insert into cars(manufacturer,model,year) values (?,?,?)")){
            prtStmt.setString(1,elem.getManufacturer());
            prtStmt.setString(2,elem.getModel());
            prtStmt.setInt(3,elem.getYear());
            int result = prtStmt.executeUpdate();
            logger.trace("Saved {} instances",result);}
        catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB "+e);
        }
    }

    @Override
    public void update(Integer id, Car elem) {
        logger.traceEntry("Updating car with id {} to new values {}", id, elem);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement prtStmt = con.prepareStatement(
                "update cars set manufacturer = ?, model = ?, year = ? where id = ?")) {
            prtStmt.setString(1, elem.getManufacturer());
            prtStmt.setString(2, elem.getModel());
            prtStmt.setInt(3, elem.getYear());
            prtStmt.setInt(4, id);
            int result = prtStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement prtStmt = con.prepareStatement("select * from cars")){
            try(ResultSet result = prtStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(cars);
        return cars;
    }
}
