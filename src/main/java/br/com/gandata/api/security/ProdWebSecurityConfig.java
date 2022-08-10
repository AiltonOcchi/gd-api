package br.com.gandata.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.gandata.api.repository.UsuarioRepository;

/**
 * Classe de configurações de segurança para ambiente de producao.
 * Adicionar as variaveis do ambiente da aplicação o argumento: -Dspring.profiles.active=prod
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)// Permite validar permissões no controller
@Profile("prod")
public class ProdWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
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
		
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.POST,"/clientes/**").hasAnyRole("ADMIN","CADASTROS")
				.antMatchers(HttpMethod.DELETE,"/clientes/**").hasAnyRole("ADMIN","CADASTROS")
				
				.antMatchers(HttpMethod.POST,"/categorias/**").hasAnyRole("ADMIN","CADASTROS")
				.antMatchers(HttpMethod.DELETE,"/categorias/**").hasAnyRole("ADMIN","CADASTROS")
				
				.antMatchers(HttpMethod.POST,"/produtos/**").hasAnyRole("ADMIN","CADASTROS")
				.antMatchers(HttpMethod.DELETE,"/produtos/**").hasAnyRole("ADMIN","CADASTROS")
				
				.antMatchers(HttpMethod.POST,"/auth").permitAll()
				
				.antMatchers("/docs","/swagger-ui/**","/v3/api-docs/**").permitAll()
				.antMatchers("/actuator/**").permitAll()
				
		
			.anyRequest()
				.authenticated()
			
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
