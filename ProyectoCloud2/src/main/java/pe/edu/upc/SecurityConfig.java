package pe.edu.upc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select * from (select user_usuario as username, password_usuario as password, "
						+ "estado_usuario as enabled from persona) as users where username = ? ")
				.authoritiesByUsernameQuery(
						"select * from (select user_usuario as username, tipo_usuario as AUTHORITY from persona)"
								+ " as authorities where username = ? ")
				.passwordEncoder(passwordEncoder);
	}

	public void configure(HttpSecurity http) throws Exception {

		try {
			http.authorizeRequests()			
			
			//Dueño
						
			.antMatchers("/dueno/listar/**").access("hasRole('ROLE_DUENO') or hasRole('ROLE_HOST')")
			.antMatchers("/dueno/modificar/**").access("hasRole('ROLE_DUENO')")			
			.antMatchers("/dueno/buscar/**").access("hasRole('ROLE_DUENO') or hasRole('ROLE_HOST')")
			.antMatchers("/dueno/eliminar/**").access("hasRole('ROLE_DUENO')")
			
			//Host
			
			.antMatchers("/host/listar/**").access("hasRole('ROLE_DUENO') or hasRole('ROLE_HOST')")
			.antMatchers("/host/modificar/**").access("hasRole('ROLE_HOST')")			
			.antMatchers("/host/buscar/**").access("hasRole('ROLE_DUENO') or hasRole('ROLE_HOST')")
			.antMatchers("/host/eliminar/**").access("hasRole('ROLE_HOST')")
			
			//Pet
			.antMatchers("/pet/nuevo/**").access("hasRole('ROLE_DUENO')")
			.antMatchers("/pet/listar/**").access("hasRole('ROLE_DUENO') or hasRole('ROLE_HOST')")
			.antMatchers("/pet/modificar/**").access("hasRole('ROLE_DUENO')")			
			.antMatchers("/pet/buscar/**").access("hasRole('ROLE_DUENO') or hasRole('ROLE_HOST')")
			.antMatchers("/pet/eliminar/**").access("hasRole('ROLE_DUENO')")
			
			.antMatchers("/bienvenido/**").access("hasRole('ROLE_DUENO') or hasRole('ROLE_HOST') ").and()

					.formLogin().loginPage("/login").loginProcessingUrl("/j_spring_security_check")
					.defaultSuccessUrl("/bienvenido/").failureUrl("/login?error").usernameParameter("usuario")
					.passwordParameter("clave").and().logout().logoutSuccessUrl("/login?logout")
					.logoutUrl("/j_spring_security_logout").and().exceptionHandling().accessDeniedPage("/403").and()
					.csrf().disable();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
