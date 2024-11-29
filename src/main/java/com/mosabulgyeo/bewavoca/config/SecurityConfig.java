package com.mosabulgyeo.bewavoca.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 설정 클래스
 *
 * 이 클래스는 Spring Security의 보안 설정을 정의합니다.
 * 주로 CORS 및 CSRF 정책과 같은 HTTP 보안 정책을 설정하며,
 * 요청 경로별 인증 및 접근 제어를 설정합니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * SecurityFilterChain 빈 설정
	 *
	 * Spring Security의 HTTP 요청 보안 체인을 설정합니다.
	 * - CSRF 비활성화
	 * - CORS 설정 적용
	 * - 모든 요청에 대해 접근을 허용 (추후 세부 설정 가능)
	 *
	 * @param http HttpSecurity 객체
	 * @return 설정된 SecurityFilterChain 객체
	 * @throws Exception 설정 중 발생하는 예외
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/**").permitAll()
			);
		return http.build();
	}

	/**
	 * CORS 설정 빈 정의
	 *
	 * 애플리케이션의 CORS(Cross-Origin Resource Sharing) 정책을 설정합니다.
	 * - 모든 도메인에 대한 접근 허용
	 * - 허용되는 HTTP 메서드 및 헤더 지정
	 * - 인증 정보 포함 허용 (AllowCredentials 설정)
	 *
	 * @return CORS 설정을 담은 CorsConfigurationSource 객체
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOriginPattern("*");
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}