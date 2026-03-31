package com.btl.snaker.security;

import com.btl.snaker.entity.User;
import com.btl.snaker.repository.UserRepository;
import com.btl.snaker.utils.JwtUtilHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {

    private final JwtUtilHelper jwtUtilHelper;
    private final UserRepository userRepository;
    public CustomJwtFilter(final JwtUtilHelper jwtUtilHelper, final UserRepository userRepository) {
        this.jwtUtilHelper = jwtUtilHelper;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        if(token!=null){
            if(jwtUtilHelper.verifyToken(token)){
                String email = jwtUtilHelper.extractEmail(token);
                if(email!=null) {
                    User user = userRepository.findByEmail(email);
                    if(user!= null){
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName()));
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                email,
                                user.getPassword(),
                                authorities);
                        SecurityContext securityContext = SecurityContextHolder.getContext();
                        securityContext.setAuthentication(authentication);
                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token=null;
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }
        return token;
    }
}
