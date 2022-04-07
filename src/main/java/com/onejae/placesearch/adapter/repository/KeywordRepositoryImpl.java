package com.onejae.placesearch.adapter.repository;

import com.onejae.placesearch.domain.port.KeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.ArrayList;

interface JpaInterface extends JpaRepository<KeywordData, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    KeywordData findByname(String name);

    ArrayList<KeywordData> findTop10ByOrderByCountDesc();
}

@Slf4j
@Repository
public class KeywordRepositoryImpl implements KeywordRepository {

    @Lazy
    @Autowired
    private JpaInterface jpaInterface;

    @Override
    @Transactional
    public void increaseKeywordCount(String keyword) {
        KeywordData existOne = this.jpaInterface.findByname(keyword);

        if (existOne == null) {
            this.jpaInterface.save(new KeywordData(keyword));
        } else {
            existOne.increaseCount();
            KeywordData after = this.jpaInterface.save(existOne);
        }
    }

    @Override
    public ArrayList<KeywordData> findPopularKeywords() {
        return this.jpaInterface.findTop10ByOrderByCountDesc();
    }
}
