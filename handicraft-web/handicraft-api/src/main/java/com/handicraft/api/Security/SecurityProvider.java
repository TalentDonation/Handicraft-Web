package com.handicraft.api.Security;

import com.handicraft.api.exception.NotAcceptableException;
import com.handicraft.api.utils.DecryptionUtil;
import com.handicraft.core.dto.User;
import org.aspectj.lang.NoAspectBoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Component
public class SecurityProvider implements AuthenticationProvider{

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${Authentication}")
    private String MASTER_KEY;

    @Autowired
    private SecurityUserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {



        String token = (String) authentication.getCredentials();

        // token decryption
        // pk 존재 유무
        // expired time 확인(30분 단위)

        logger.info(token);

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

            logger.info(tokenDecryption);

            String[] tokenizer = StringUtils.split(tokenDecryption, "/");

//            LocalDateTime localDateTime = LocalDateTime.parse(tokenizer[1]);
//
//            logger.info(localDateTime.getSecond() + "");
//
//            if (Math.abs(LocalDateTime.now().getSecond() - localDateTime.getSecond()) / 60000 > 3000)
//                throw new AuthenticationServiceException("Exprired Exception");



            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date tokenDate = null;
            try {
                tokenDate = simpleDateFormat.parse(tokenizer[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            logger.info(""+Math.abs(LocalDateTime.now().getSecond() - tokenDate.getSeconds()));
            logger.info(""+LocalDateTime.now().getSecond());
            logger.info(""+tokenDate.getSeconds());

            if (Math.abs(LocalDateTime.now().getSecond() - tokenDate.getSeconds()) / 60000 > 3000)
                throw new AuthenticationServiceException("Exprired Exception");

            SecurityUserDetails user = userDetailService.loadUserByUsername(tokenizer[0]);

            return new SecurityAuthentication(user.getUser(), user.getAuthorities());
        }
        else
        {
            return new SecurityAuthentication(new User(), AuthorityUtils.createAuthorityList());
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
