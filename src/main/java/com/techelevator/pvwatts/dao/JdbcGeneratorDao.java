package com.techelevator.pvwatts.dao;

import com.techelevator.pvwatts.exception.DaoException;
import com.techelevator.pvwatts.model.Generator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcGeneratorDao implements GeneratorDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcGeneratorDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Generator getGeneratorById(int generatorId) {
        Generator generator = null;
        String sql = "SELECT generator_id, utility_id, name, street_address1, street_address2, city, state, zip_code, system_size, module_type, array_type, tilt\n" +
                "\tFROM public.generator WHERE generator_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, generatorId);
            if (results.next()) {
                generator = mapRowToGenerator(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to server or database");
        }
        return generator;
    }


    @Override
    public List<Generator> getGenerators() {
        List <Generator> generators = new ArrayList<>();
        String sql = "SELECT generator_id, utility_id, name, street_address1, street_address2, city,\n" +
                "state, zip_code, system_size, module_type, array_type, tilt\n" +
                "\tFROM public.generator;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Generator generator = mapRowToGenerator(results);
                generators.add(generator);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to server or database", e);
        }

        return generators;
    }

    @Override
    public List<Generator> getGeneratorByUtilityId(int utilityId) {
        List <Generator> generators = new ArrayList<>();
        String sql = "SELECT generator_id, utility_id, name, street_address1, street_address2, city,\n" +
                "state, zip_code, system_size, module_type, array_type, tilt\n" +
                "\tFROM public.generator WHERE utility_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, utilityId);
            while (results.next()) {
                Generator generator = mapRowToGenerator(results);
                generators.add(generator);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to server or database", e);
        }
        return generators;
    }

    @Override
    public Generator createGenerator(Generator generator) {
        Generator newGenerator = null;

        String sql = "INSERT INTO public.generator(\n" +
                "\tutility_id, name, street_address1, street_address2, city, state, zip_code, system_size, module_type, array_type, tilt)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            int newGeneratorId = jdbcTemplate.queryForObject(sql, int.class, generator.getUtilityId(), generator.getName(), generator.getStreetAddress1(), generator.getStreetAddress2(),
                    generator.getCity(), generator.getZipCode(), generator.getSystemSize(), generator.getModuleType(), generator.getArrayType(), generator.getTilt());

            // retrieve the newly created generator
            newGenerator = getGeneratorById(newGeneratorId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }

        return newGenerator;
    }

    @Override
    public Generator updateGenerator(Generator updatedGenerator) {
        return null;
    }

    @Override
    public int deleteGeneratorById(int generatorId) {
        return 0;
    }

    private Generator mapRowToGenerator(SqlRowSet results) {
        Generator generator = new Generator();
        generator.setGeneratorId(results.getInt("generator_id"));
        generator.setUtilityId(results.getInt("utility_id"));
        generator.setName(results.getString("name"));
        generator.setStreetAddress1(results.getString("street_address1"));
        generator.setStreetAddress2(results.getString("street_address2"));
        generator.setCity(results.getString("city"));
        generator.setState(results.getString("state"));
        generator.setZipCode(results.getString("zip_code"));
        generator.setSystemSize(results.getDouble("system_size"));
        generator.setModuleType(results.getInt("module_type"));
        generator.setArrayType(results.getInt("array_type"));
        generator.setTilt(results.getDouble("tilt"));

        return generator;

    }

}
