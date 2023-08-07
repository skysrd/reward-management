package com.skysrd.marketboro.api.history.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skysrd.marketboro.api.history.domain.entity.History;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HistoryRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    EntityManager entityManager;

    @BeforeEach
    void init() {
        entityManager = testEntityManager.getEntityManager();
    }

    @Test
    void findPageByRewardTypeAndMember() {

    }
}