package com.handicraft.api.Security;

import com.handicraft.api.exception.NotAcceptableException;
import com.handicraft.api.utils.DecryptionUtil;
import org.aspectj.lang.NoAspectBoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
import java.util.Date;


@Component
public class SecurityProvider implements AuthenticationProvider{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityUserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String token = (String) authentication.getCredentials();

        // token decryption
        // pk 존재 유무
        // expired time 확인(30분 단위)

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

        String [] tokenizer = StringUtils.split(tokenDecryption , "/");



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(tokenizer[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date currentDate = new Date();

        logger.info(currentDate.getTime()+"");
        logger.info(date.getTime()+"");
        if(Math.abs(date.getTime() - currentDate.getTime()) / 60000 > 3000)    throw new AuthenticationServiceException("Exprired Exception" );


        SecurityUserDetails user =  userDetailService.loadUserByUsername(tokenizer[0]);

        return new SecurityAuthentication(user.getUser(), user.getAuthorities());
    }

    /*
    * authenticate 의 리턴값의 반환형을 확인
    * */
    @Override
    public boolean supports(Class<?> authenticationClass) {
        return SecurityAuthentication.class.isAssignableFrom(authenticationClass);
    }
}
