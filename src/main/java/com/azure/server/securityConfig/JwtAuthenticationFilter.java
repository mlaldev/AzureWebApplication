package com.azure.server.securityConfig;

import com.azure.server.ServicesImpl.UserDetailsServiceImpl;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceimpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String header=request.getHeader("Authorization");
        String username=null;
        String token = null;
        //Checking if header is null
        if (header!=null && header.startsWith("bearer ")){
          token = header.substring(7);
          try {
              username = this.jwtUtil.extractUsername(token);
          }catch (ExpiredJwtException e){
              e.printStackTrace();
              System.out.println("Token has Expired!!");

          }catch (Exception e){
              e.printStackTrace();
          }
        }else {
            System.out.println("Invalid token,not start with bearer");
        }
        //Checking if username is null
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userDetails = this.userDetailsServiceimpl.loadUserByUsername(username);
            //Checking if token is valid
            if(this.jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                System.out.println("Token is not Valid!!");
            }

        }
        filterChain.doFilter(request,response);
    }
}
