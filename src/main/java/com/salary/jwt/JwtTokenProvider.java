package com.salary.jwt;

import com.salary.member.entity.Member;
import com.salary.member.repository.MemberRepository;
import com.salary.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private static final long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public JwtToken createToken(Member member) {
        Claims claims = getClaims(member);
        Date now = new Date();
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = UUID.randomUUID().toString();

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .key(member.getSub())
                .build();
    }

    public String createAccessToken(String sub){
        Claims claims = getClaims(sub);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createShortAccessToken(String sub, long second){
        Claims claims = getClaims(sub);
        Date now = new Date();
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + second * 1000L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Member member = memberService.getMemberInfo(this.getUserPk(token)).orElse(new Member());
        return new UsernamePasswordAuthenticationToken(member, "", List.of(new SimpleGrantedAuthority(member.getRole())));
    }

    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getAccessToken(HttpServletRequest request) {
        return request.getHeader("access-token");
    }

    public boolean validateAccessToken(String jwtToken) {
        if(jwtToken == null) return false;
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("access-token 검증 시 오류 발생");
            return false;
        }
    }

    private Claims getClaims(String sub){
        Claims claims = Jwts.claims().setSubject(sub);
        Member member = memberRepository.findBySub(sub).orElse(new Member());

        claims.put("role", member.getRole());
        return claims;
    }

    private Claims getClaims(Member member){
        String role = member.getRole();
        String sub = member.getSub();

        Claims claims = getClaims(sub);
        claims.put("role", role);
        return claims;
    }

    private Date getExpiration(){
        Date now = new Date();
        return new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);
    }
}
