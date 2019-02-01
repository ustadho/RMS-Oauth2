/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ustadho.oauth.appauth;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Ustadho_1218
 */
public class TestBCrypt {
    Logger logger = LoggerFactory.getLogger(this.getClass());
            
    @Test
    public void testPassword(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode("123");
        logger.debug("123 --> {}", result);
        
        String encoded = new BCryptPasswordEncoder().encode("123");
        logger.debug("123 --> {}", encoded);
    }
}
