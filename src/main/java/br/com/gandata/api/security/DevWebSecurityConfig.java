package br.com.gandata.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.gandata.api.repository.UsuarioRepository;

/**
 * Classe de configurações de segurança apenas para ambiente de desenvolvimento.
 * Estas configurações não exigem autenticação para consumir os endpoints
 * Adicionar as variaveis do ambiente da aplicação o argumento: -Dspring.profiles.active=dev
 */
@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired 
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		/*
		.antMatchers(HttpMethod.POST).hasAnyRole("ADMIN","CADASTROS")
		.antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/auth").permitAll()
		.antMatchers("/actuator/**").permitAll()
		.anyRequest().authenticated()
		*/
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated()
		
		.and()
		.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		.and()
		.addFilterBefore(new AutenticacaoViaTokenFilter(tokenService,usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs**","/v3/api-docs/**","/docs","/h2-console/**");
	}

}
