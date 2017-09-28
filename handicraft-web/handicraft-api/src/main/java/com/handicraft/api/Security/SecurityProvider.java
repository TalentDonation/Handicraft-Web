package com.handicraft.api.Security;

import com.handicraft.api.exception.NotAcceptableException;
import com.handicraft.api.exception.UnAuthorizedException;
import com.handicraft.api.utils.DecryptionUtil;
import com.handicraft.core.dto.Authority;
import com.handicraft.core.dto.User;
import com.handicraft.core.dto.UserToAuthority;
import com.handicraft.core.service.UserToAuthorityService;
import com.handicraft.core.utils.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.NoAspectBoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Component
@Slf4j
public class SecurityProvider implements AuthenticationProvider{

    @Value("${Authentication}")
    private String MASTER_KEY;

//    @Autowired
//    private SecurityUserDetailsService userDetailService;

    @Autowired
    private UserToAuthorityService userToAuthorityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getCredentials();

        // token decryption
        // pk 존재 유무
        // expired time 확인(30분 단위)
        log.info(token);

        UserToAuthority userToAuthority;
        Authority authority;

        if(!token.equals(MASTER_KEY)) {


            String tokenDecryption = null;
            try {
                tokenDecryption = DecryptionUtil.AES_Decrypt(token);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }

            log.info(tokenDecryption);

            String[] tokenizer = StringUtils.split(tokenDecryption, "/");


            userToAuthority = userToAuthorityService.loadUserByUsername(tokenizer[0]);

            if(userToAuthority == null) throw new UnAuthorizedException();

            log.info("UserToAuthority : " + userToAuthority.toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date tokenDate = dateFormat.parse(tokenizer[1]);
                Date curDate = new Date();

                log.info("authentication seconds : "+ (curDate.getTime() - tokenDate.getTime()));
                log.info("authentication now time :"+curDate.getTime());
                log.info("authentication token time :"+tokenDate.getTime());

                if (Math.abs(curDate.getTime() - tokenDate.getTime()) > 1800000)
                {
                    authority = userToAuthority.getAuthority();
                    authority.setCredentialsExpired(true);
                    authority.setEnabled(false);
                    userToAuthority.setAuthority(authority);

                    userToAuthorityService.update(userToAuthority);

                    throw new AuthenticationServiceException("Exprired Exception");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

//            SecurityUserDetails user = userDetailService.loadUserByUsername(tokenizer[0]);
//
//            return new SecurityAuthentication(user.getUser(), user.getAuthorities());


            return new SecurityAuthentication(userToAuthority);
        }
        else
        {
            authority = new Authority();
            userToAuthority = new UserToAuthority();

            authority.setRole(Role.MASTER);
            userToAuthority.setAuthority(authority);
            return new SecurityAuthentication(userToAuthority);
        }
    }

    /*
    * authenticate 의 리턴값의 반환형을 확인
    * */
    @Override
    public boolean supports(Class<?> authenticationClass) {
        return SecurityAuthentication.class.isAssignableFrom(authenticationClass);
    }
}
