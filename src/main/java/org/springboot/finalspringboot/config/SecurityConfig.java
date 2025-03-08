package org.springboot.finalspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customer->customer.disable())
                .cors(c->c.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signin","/").permitAll()
                        .requestMatchers("/checkout","/logout").authenticated()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                ).logout(logout -> logout
                        .logoutUrl("/logout")  // Xác định URL logout
                        .logoutSuccessUrl("/login") // Định nghĩa trang thành công sau khi logout
                        .invalidateHttpSession(true) // Xóa session sau khi logout
                        .deleteCookies("JSESSIONID") // Xóa cookie phiên đăng nhập
                        .permitAll() // Cho phép tất cả người dùng thực hiện logout
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
