package com.onejae.placesearch.adapter.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Data
@Table(name = "Keyword")
public class KeywordData extends BaseTimeEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    private Integer id;

    @Basic
    @Column(unique = true)
    private String name;

    @Basic
    @Column
    private int count;

    public KeywordData() {
    }

    public KeywordData(String name) {
        this.name = name;
        this.count = 1;
    }

    public void increaseCount() {
        this.count += 1;
    }
}
