package com.csse.Config;

import com.csse.Service.Imp.JWTService;
import com.csse.Service.Imp.MyUserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {//add parent class to get filter ability

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context; // you need to get memo for this


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //This is what you get from the client side
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW51IiwiaWF0IjoxNzMyMjYxMzQzLCJleHAiOjE3MzIyNjE0NTF9.VxIdc0E-nxrbgz8eIAnCZnddLHaZiN3IfWEFq0Jcvus
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7); //removing "bearer_" to get token
            username = jwtService.extractUsername(token);
        }

        // check username not null and check its not already authenicated; if it authenticate why we need to authentic again???
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = context.getBean(MyUserDetailsServiceImp.class).loadUserByUsername(username);

            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }
        filterChain.doFilter(request,response);
    }
}
