package com.healthybites.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	
	@Builder.Default
	@OneToMany(
			mappedBy = "user", 
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@JsonManagedReference
	private List<RecipeEntity> recipes = new ArrayList<>();
	
	public void addRecipe(RecipeEntity recipe) {
		recipes.add(recipe);
		recipe.setUser(this);
		
	}
	
	public void removeRecipe(RecipeEntity recipe) {
		recipes.remove(recipe);
		recipe.setUser(this);
		
	}
   
   // InfoUser
   @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
   private InfoUserEntity infoUser;
   
   public void addInfoUser(InfoUserEntity infoUser) {
	   this.infoUser = infoUser;
	   infoUser.setUser(null);
   }
   
   public void removeInfoUser(InfoUserEntity infoUser) {
	   if (this.infoUser != null) {
		this.infoUser.setUser(null);
	   }
	   this.infoUser = null;
   }
}
