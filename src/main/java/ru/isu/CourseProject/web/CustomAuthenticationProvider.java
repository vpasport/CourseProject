package ru.isu.CourseProject.web;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println( authentication.getDetails() );

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println( name );
        System.out.println( password );

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        User user = userRepository.getByLogin( token.getName() );
        System.out.println( user );
        if (user==null || !user.getPassword().equalsIgnoreCase( getHash( token.getCredentials().toString()) ) ){
            throw new BadCredentialsException("No user or password");
        }
        UsernamePasswordAuthenticationToken token2 = new UsernamePasswordAuthenticationToken(user,user.getPassword(), user.getAuthorities());

        System.out.println(SecurityContextHolder.getContext());
        return token2;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }

    public static String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return bytesToHexString(md.digest(pass.getBytes()));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        System.out.println( aClass );
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}