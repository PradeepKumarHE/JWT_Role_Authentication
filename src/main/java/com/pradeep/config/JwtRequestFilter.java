package com.pradeep.config;

import com.pradeep.service.JWTService;
import com.pradeep.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken=null;
        String username=null;

        String header=request.getHeader("Authorization");

        if(null!=header && header.startsWith("Bearer ")){
            jwtToken=header.substring(7);
            try {
                username= jwtUtil.getUsernameFromToken(jwtToken);
                System.out.println("******************* "+username);
            }catch (IllegalArgumentException illegalArgumentException){
                System.out.println("Un able to get JWT token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT token is expired");
            }
        }else{
            System.out.println("JWT token doesn't start bearer");
        }

        if(null!=username && null==SecurityContextHolder.getContext().getAuthentication()){
            UserDetails userDetails=jwtService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request,response);
    }
}
