package org.voo.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import org.voo.api.models.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        System.out.print("TESTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean savePersonWithPreparedStatement(final Person person) {
        String query = "INSERT INTO tbl_teste(id, nome) values (?, ?);";

        PreparedStatementCallback<Boolean> preparedStatement = new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, person.getId());
                ps.setString(2, person.getName());
                return ps.execute();
            }
        };
        return jdbcTemplate.execute(query, preparedStatement);
    }
}
