package gov.nist.healthcare.ttt.webapp.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public SimpleCORSFilter cors;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .addFilterBefore(cors, ChannelProcessingFilter.class)
			.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/#/login")
			.successHandler(new AjaxAuthenticationSuccessHandler())
			.failureHandler(new AjaxAuthenticationFailureHandler())
			.loginProcessingUrl("/login")
			.failureUrl("/#/login?error")
			.permitAll()
//		.and()
//			.logout()
//			.permitAll()
		.and()
			.csrf().disable();
//			.csrfTokenRepository(csrfTokenRepository()).and()
//			.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		
		http.logout()
	        .logoutUrl("/logout")
	        .logoutSuccessUrl("/")
	        .invalidateHttpSession( true )
	        .deleteCookies("JSESSIONID");
	}

//	private Filter csrfHeaderFilter() {
//		return new OncePerRequestFilter() {
//			@Override
//			protected void doFilterInternal(HttpServletRequest request,
//					HttpServletResponse response, FilterChain filterChain)
//							throws ServletException, IOException {
//				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
//						.getName());
//				if (csrf != null) {
//					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//					String token = csrf.getToken();
//					if (cookie == null || token != null
//							&& !token.equals(cookie.getValue())) {
//						cookie = new Cookie("XSRF-TOKEN", token);
//						cookie.setPath("/");
//						response.addCookie(cookie);
//					}
//				}
//				filterChain.doFilter(request, response);
//			}
//		};
//	}
//
//	private CsrfTokenRepository csrfTokenRepository() {
//		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		repository.setHeaderName("X-XSRF-TOKEN");
//		return repository;
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
		.withUser("user").password("password").roles("USER");
	}
}
