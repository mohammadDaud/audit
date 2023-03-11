package com.ams.api.admin.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.ams.constants.TokenType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {
 
	public PasswordResetToken(String token,User user,LocalDateTime tokenDate, TokenType tokenType)
	{
		this.token =token;
		this.user =user;
		this.tokenDate =tokenDate;
		this.tokenType = tokenType;
	}
	
	
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASSWORD_RESET_TOKEN_SEQ_generator")
	@SequenceGenerator(name = "PASSWORD_RESET_TOKEN_SEQ_generator", sequenceName = "PASSWORD_RESET_TOKEN_SEQ", allocationSize = 1)
    private Long id;
 
    private String token;
 
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
 
    private LocalDateTime tokenDate;
    
    @Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private TokenType tokenType;
}