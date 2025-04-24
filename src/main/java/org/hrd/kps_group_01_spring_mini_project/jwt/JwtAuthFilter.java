package org.hrd.kps_group_01_spring_mini_project.jwt;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.exception.InvalidTokenException;
import org.hrd.kps_group_01_spring_mini_project.service.AppUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AppUserService appUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Getting Authorization Token From Request
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);


            try {

                //Extracting Email from Token Claim for verification with Security Context Holder
                String Identifier = jwtUtils.extractIdentifier(token);

                //check if there is no identifier or haven't authenticated
                if (Identifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = appUserService.loadUserByUsername(Identifier);


                    if (jwtUtils.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()

                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                            throw new InvalidTokenException("Authentication required or token is invalid");
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}

