package Final.Project.dodo.utils;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;


@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.authKey}")
    private String authKey;

    public String generateToken(Long userId) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiration);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, authKey)
//                .claim("role", "Client")
                .claim("userId",userId)
                .compact();
    }

    public String generateAccessToken(Long userId) {
        return generateToken( userId);
    }



    public Long validateToken(String token, Integer lang) {

        try {

            Claims claims;
            claims = Jwts.parser().setSigningKey(authKey)
                    .parseClaimsJws(token).getBody();
            if (claims != null) {

                return Long.parseLong(String.valueOf(claims.get("userId")));

            } else {
                throw new JwtException(ResourceBundelLanguage.periodMessage
                        (language.getLanguage(lang),"tokenIsEmpty"));
            }
        } catch (ExpiredJwtException e) {
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"expiredToken"));
        } catch (MalformedJwtException e) {
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"hackedToken"));
        } catch (ResponseStatusException e) {
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"tokenIsEmpty"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"notValidatedToken"));
        }
    }

    public String getClaim(String token, Integer lang) {
        try {

            int i = token.lastIndexOf('.');
            String withoutSignature = token.substring(0, i + 1);

            Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);

            Claims claims = untrusted.getBody();

            if (claims != null) {
                return String.valueOf(claims.get("claim"));
            } else {
                throw new JwtException(ResourceBundelLanguage.periodMessage
                        (language.getLanguage(lang),"tokenIsEmpty"));
            }
        } catch (ExpiredJwtException e) {
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"expiredToken"));
        } catch (MalformedJwtException e) {
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"hackedToken"));
        } catch (ResponseStatusException e) {
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"tokenIsEmpty"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JwtException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(lang),"notValidatedToken"));
        }
    }
}
