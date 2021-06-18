package com.orchid.resource.converter;

import com.orchid.core.auth.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author biejunyang
 * @version 1.0
 * @date 2021/4/27 14:43
 */
public class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {

    /**
     * 从认证信息(主要是用户数据)中抽取数据，持久化到Token中
     * @param authentication
     * @return
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {        //默认只添加了，用户名username和权限信息authority到jwt token中
//        Map<String, Object> response= (Map<String, Object>) super.convertUserAuthentication(authentication);

        //添加用户的自定义信息
        AuthUser user=(AuthUser) authentication.getPrincipal();

        Map<String, Object> response = new LinkedHashMap();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }


        //其他自定义信息
        response.put("organId", user.getDetails().get("organId"));
//        response.putAll(user.getAdditionalInformation());
        return response;
    }


    /**
     * Token解析时，从Token中获取到数据后，还原成认证对象信息
     * @param map
     * @return
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey("username")) {
            String username=map.get("username").toString();
            Object authorities = map.get("authorities");

            AuthUser principal=new AuthUser();
            principal.setUsername(username);
            principal.setId(Long.valueOf(map.get("id").toString()));
            principal.setAdminType(Integer.valueOf(map.get("adminType").toString()));
            List<GrantedAuthority> grantedAuthorities=null;
            if(authorities!=null && authorities instanceof List){
                grantedAuthorities= AuthorityUtils.createAuthorityList(((List<String>)authorities).toArray(new String[]{}));
            }

            Map<String,Object> addtionalInformation=new HashMap<>(map);
            addtionalInformation.remove("username");
            addtionalInformation.remove("authorities");
            principal.setDetails(addtionalInformation);

            return new UsernamePasswordAuthenticationToken(principal, "N/A", grantedAuthorities);
        } else {
            return null;
        }
    }
}
