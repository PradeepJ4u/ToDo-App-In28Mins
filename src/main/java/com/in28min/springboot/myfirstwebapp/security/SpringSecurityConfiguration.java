package com.in28min.springboot.myfirstwebapp.security;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public InMemoryUserDetailsManager createLoginDetails() {

		UserDetails userDetails1 = createNewUser("Pradeep", "a");
		UserDetails userDetails2 = createNewUser("Swasti", "s");
		UserDetails userDetails3 = createNewUser("Vedansh", "v");
		UserDetails userDetails4 = createNewUser("Mona", "m");
		UserDetails userDetails5 = createNewUser("Krishna", "k");

		return new InMemoryUserDetailsManager(userDetails1, userDetails2, userDetails3, userDetails4, userDetails5);
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		UserDetails userDetails = User.builder().passwordEncoder(passwordEncoder).username(username).password(password)
				.roles("USER", "ADMIN").build();
		return userDetails;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return http.authorizeHttpRequests(
				auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.anyRequest().authenticated())
		//		.formLogin(withDefaults())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf().disable()
		//		.headers().frameOptions().disable()
				.build();
		
	}

}
