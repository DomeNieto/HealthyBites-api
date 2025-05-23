package com.healthybites.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing detailed user information related to health and lifestyle.
 * Maps to the "info_user" table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "info_user")
public class InfoUserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "height", nullable = false)
	private Double height;
	
	@Column(name = "weight", nullable = false)
	private Double weight;
	
	@Column(name = "sex", length = 30)
	private String sex;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	@Column(name = "activity_level", length = 30)
	private String activityLevel;
	
	
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
	
}
