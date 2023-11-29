package com.techelevator.pvwatts.dao;

import com.techelevator.pvwatts.exception.DaoException;
import com.techelevator.pvwatts.model.Utility;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtilityDao implements UtilityDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcUtilityDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Utility getUtilityById(int utilityId) {
        Utility utility = null;
        String sql = "SELECT utility_id, name\n" +
                "\tFROM public.utility WHERE utility_id = ?;";
        try {
            SqlRowSet results  = jdbcTemplate.queryForRowSet(sql, utilityId);
            if (results.next()) {
                utility = mapToRowSet (results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Can't connect to server or database");
        }

        return utility;
    }


    @Override
    public List<Utility> getUtilities() {
        List<Utility> utilities = new ArrayList<>();
        String sql = "SELECT utility_id, name\n" +
                "\tFROM public.utility ORDER BY utility_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Utility utility = mapToRowSet(results);
                utilities.add(utility);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Can't connect to server or database", e);
        }

        return utilities;
    }

    @Override
    public Utility createUtility(Utility utility) {
        Utility newUtility = null;
        String sql = "INSERT INTO public.utility(name) " +
                "\tVALUES (?) RETURNING utility_id;";

       try {
           int newUtilityId = jdbcTemplate.queryForObject(sql, int.class, utility.getName());
           newUtility = getUtilityById(newUtilityId);
       } catch (CannotGetJdbcConnectionException e){
           throw new DaoException("Cannot connect to server or database", e);
       } catch (DataIntegrityViolationException e){
           System.out.println(e.getMessage());
           throw new DaoException("Data integrity violation", e);
       }
        return newUtility;
    }

    @Override
    public Utility updateUtility(Utility updatedUtility) {
        return null;
    }

    @Override
    public void deleteUtilityById(int utilityId) {
        String reassignGeneratorsSql = "UPDATE public.generator\n" +
                "\tSET utility_id = 0\n" +
                "\tWHERE utility_id = ?;";
        String sql = "DELETE FROM utility WHERE utility_id = ?;";

        try {
            // assign utility_id of all generators from this utility to zero.
            jdbcTemplate.update(reassignGeneratorsSql, utilityId);
            jdbcTemplate.update(sql, utilityId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Utility mapToRowSet(SqlRowSet results) {
        Utility utility = new Utility();
        utility.setUtilityId(results.getInt("utility_id"));
        utility.setName(results.getString("name"));
        return utility;
    }

}
