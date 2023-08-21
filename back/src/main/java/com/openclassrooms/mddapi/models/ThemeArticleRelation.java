package com.openclassrooms.mddapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "THEMEARTICLERELATION")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class ThemeArticleRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "ID_Theme", referencedColumnName = "id")
    private Theme theme;

    @OneToOne
    @JoinColumn(name = "ID_Article", referencedColumnName = "id")
    private Article article;
}
