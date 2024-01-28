package com.sb.customer.jwtservice;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	@Autowired
	private UserDetailServiceImpl userServiceInmpl;

	public String generateToken(UserDetails userDetails) {
		long expiryTimeInMilliseconds = 1000 * 60 * 30; // 30 minutes
		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiryTimeInMilliseconds))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

	}

	private Key getSignKey() {
		String secretKey = "28afefa532692520935301de4ee004b06abbb16e7337cc5ea7321ae65d82e961";
		byte[] keys = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keys);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()));
	}

	public boolean isTokenValid(String token) {
		String userName = extractUserName(token);
		UserDetails userDetails = userServiceInmpl.userDetailsService().loadUserByUsername(userName);
		return (userName.equals(userDetails.getUsername()));

	}

	public boolean isTokenExpired(String token, UserDetails userDetails) {
		String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
