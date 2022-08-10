package br.com.gandata.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Classe de configurações de segurança apenas para ambiente de desenvolvimento.
 * Estas configurações não exigem autenticação para consumir os endpoints
 * Adicionar as variaveis do ambiente da aplicação o argumento: -Dspring.profiles.active=dev
 */
@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/**").permitAll()
			.and()
				.csrf().disable().sessionManagement();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/swagger-ui/**","/v3/api-docs/**","/docs", "/h2-console/**");
	}
}
