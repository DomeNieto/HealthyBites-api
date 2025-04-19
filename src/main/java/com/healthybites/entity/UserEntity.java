package com.healthybites.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false) 
    private String password;

	@Column(name = "account_no_expired")
	private Boolean accountNoExpired;
	
	@Column(name = "account_no_locked")
	private Boolean accountNoLocked;
	
	@Column(name = "credential_no_expired")
	private Boolean credentialNoExpired;
	
	@Column(name = "is_enable")
	private Boolean isEnable;
	
   @Column(name = "registration_date")
    private LocalDateTime registrationDate;
   
   // Role 
   @ManyToOne
   @JoinColumn(name = "role_id", nullable = false)
   @JsonBackReference
   private RoleEntity role;
   
	
}
