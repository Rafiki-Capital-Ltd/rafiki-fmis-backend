package ke.co.rafiki.fmis.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.io.Serializable;

public interface PermissionEvaluator {

    boolean hasPermission(Authentication authentication, Object subject, Object permission);

    boolean hasPermission(Authentication authentication, Serializable id, String type, Object permission);

}
