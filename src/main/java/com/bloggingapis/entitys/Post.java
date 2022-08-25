package com.bloggingapis.entitys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String title;
   @Column(length = 1000)
   private String content;
   private String image;
   private Date date;
   @ManyToOne
   private Category category;
   @ManyToOne
   private User user;
   @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
   private Set<Comment> comments = new HashSet<Comment>();
}
