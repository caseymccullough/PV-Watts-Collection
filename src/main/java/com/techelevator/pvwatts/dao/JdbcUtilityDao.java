package com.techelevator.pvwatts.dao;

import com.techelevator.pvwatts.exception.DaoException;
import com.techelevator.pvwatts.model.Utility;
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
    public Utility createUtility(Utility newUtility) {
        return null;
    }

    @Override
    public Utility updateUtility(Utility updatedUtility) {
        return null;
    }

    private Utility mapToRowSet(SqlRowSet results) {
        Utility utility = new Utility();
        utility.setUtilityId(results.getInt("utility_id"));
        utility.setName(results.getString("name"));
        return utility;
    }

}
