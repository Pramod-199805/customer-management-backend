package com.sb.customer.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.sb.customer.jwtservice.JWTService;
import com.sb.customer.jwtservice.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;
	@Autowired
	private UserDetailServiceImpl userServiceInmpl;
	
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Insoide Filter");
		String header = request.getHeader("Authorization");
		String jwt;
		String userEmail;
		
		try {
		if (StringUtils.isEmpty(header) || !StringUtils.startsWith(header, "Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwt = header.substring(7);
		userEmail = jwtService.extractUserName(jwt);
		System.out.println("Filter Class");
		if (StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userServiceInmpl.userDetailsService().loadUserByUsername(userEmail);
			if (jwtService.isTokenValid(jwt, userDetails)) { //&& jwtService.isTokenExpired(jwt, userDetails)
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				securityContext.setAuthentication(token);
				SecurityContextHolder.setContext(securityContext);

			}

			filterChain.doFilter(request, response);
		}
		}
		catch (Exception e) {
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
	}
	
 
}
