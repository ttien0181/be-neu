package com.example.neu.config;

import com.example.neu.security.JwtAuthenticationFilter;
import com.example.neu.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration // Đánh dấu class này là một lớp cấu hình của Spring
@EnableMethodSecurity // Cho phép dùng @PreAuthorize, @Secured, v.v. trong Controller/Service
@RequiredArgsConstructor // Lombok tự tạo constructor cho các field final
public class SecurityConfig implements WebMvcConfigurer {

    // Các dependency cần tiêm vào
    private final JwtAuthenticationFilter jwtFilter; // Filter kiểm tra JWT trong mỗi request
    private final UserDetailsServiceImpl userDetailsService; // Dịch vụ load thông tin user từ DB


    /**
     * Cấu hình chuỗi filter chính của Spring Security
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Bật CORS và dùng cấu hình từ corsConfigurationSource()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Tắt CSRF vì ta dùng JWT (stateless, không cần session)
                .csrf(csrf -> csrf.disable())
                // Cho phép hiển thị frame (hữu ích khi dùng H2 console)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // Cấu hình session: STATELESS (mỗi request độc lập, không lưu session)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Cấu hình quyền truy cập
                .authorizeHttpRequests(auth -> auth
                        // Cho phép preflight request của CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Các API liên quan đến đăng nhập / đăng ký được truy cập công khai
                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/api/questions/**").permitAll()
//                        .requestMatchers("/api/appointments/**").permitAll()
                        // Chỉ ADMIN mới có quyền POST/PUT/DELETE
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        // Các request còn lại cần xác thực (JWT hợp lệ)
                        .anyRequest().authenticated()
                )
                // Đăng ký AuthenticationProvider tùy chỉnh
                .authenticationProvider(authenticationProvider())
                // Thêm JwtAuthenticationFilter chạy TRƯỚC UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Trả về chuỗi filter đã cấu hình
        return http.build();
    }

    /**
     * Cấu hình AuthenticationProvider — xác định cách xác thực username/password
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Dùng dịch vụ userDetailsService để truy xuất thông tin người dùng từ DB
        provider.setUserDetailsService(userDetailsService);
        // Dùng BCrypt để mã hóa mật khẩu
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Bean mã hóa mật khẩu
     * BCrypt là thuật toán băm an toàn, được Spring khuyến khích dùng
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean AuthenticationManager
     * Dùng trong AuthController để thực hiện authenticate(username, password)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Lấy manager từ cấu hình mặc định của Spring
    }

    /**
     * Cấu hình CORS để cho phép frontend (React, Angular, v.v.) gọi API backend
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cho phép frontend ở địa chỉ này truy cập
//        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "https://*.usercontent.goog"
        ));

        // Cho phép gửi cookie, JWT hoặc header Authorization
        configuration.setAllowCredentials(true);

        // Cho phép các phương thức HTTP cụ thể
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Cho phép tất cả các header (vd: Authorization, Content-Type, ...)
        configuration.setAllowedHeaders(List.of("*"));

        // Cho phép client đọc các header này từ response
        configuration.setExposedHeaders(List.of("Authorization"));

        // Đăng ký cấu hình này cho tất cả endpoint
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Cho phép ứng dụng xử lý multipart request (upload file)
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
