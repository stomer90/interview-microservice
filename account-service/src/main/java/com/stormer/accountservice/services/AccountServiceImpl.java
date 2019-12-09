package com.stormer.accountservice.services;

import com.stormer.accountservice.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Book;
import java.util.Optional;

@Repository
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> getUserProfile(String username) {
        LOGGER.info("Call getUserProfile: {}" , username);
        return jdbcTemplate.queryForObject(
                "select * from users where username = ?",
                new Object[]{username},
                (rs, rowNum) ->
                        Optional.of(new User(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("username"),
                                rs.getString("phone_number")
                        ))
        );
    }
}
