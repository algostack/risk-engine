package io.algostack.risk.model.domain.cds;

import java.security.SecureRandom;

public class RandomString {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final String UPPER_CASE_AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public static String upperCaseAlphabets( int len ) {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( UPPER_CASE_AB.charAt( rnd.nextInt(UPPER_CASE_AB.length()) ) );
        return sb.toString();
    }

}