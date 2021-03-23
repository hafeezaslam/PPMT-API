package com.hafeez.ppmtool.security;

import com.hafeez.ppmtool.domain.User;
import com.hafeez.ppmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.hafeez.ppmtool.security.SecurityConstants.HEADER_STRING;
import static com.hafeez.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

    public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwt = getJwtFromRequest(httpServletRequest);
            if(StringUtils.hasText(jwt)&& tokenProvider.validateToken(jwt)){
                Long userId = tokenProvider.getUserIdFromToken(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }catch (Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    // understand how to get benefit from Optional if it will be the return type
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;

    }
}
