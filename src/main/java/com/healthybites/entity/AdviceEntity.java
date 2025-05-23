package com.healthybites.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "advice")
/**
 * AdviceEntity
 * Represents an advice entity in the database.
 * Contains fields for id, title, description, and creation date.
 */
public class AdviceEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String title;
	
	@Column(nullable = false, length = 200)
	private String description;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;
	
}
