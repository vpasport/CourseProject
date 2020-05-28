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
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

//    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CustomAuthenticationProvider( UserRepository userRepository ){
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println( authentication.getName() );
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        System.out.println( token.getName() );
        System.out.println( userRepository );
        User user = userRepository.getByLogin( token.getName() );
        System.out.println( user );
        if (user==null || !user.getPassword().equalsIgnoreCase( getHash( token.getCredentials().toString()) ) ){
            throw new BadCredentialsException("No user or password");
        }
        UsernamePasswordAuthenticationToken token2 = new UsernamePasswordAuthenticationToken(user,user.getPassword(), user.getAuthorities());

        System.out.println( token2 );
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