package com.vibsoft.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vibsoft.base.model.UserInfo;
import com.vibsoft.base.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService
        implements UserDetailsService
{
    @Autowired
    UserInfoService userInfoService;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        UserInfo ui = this.userInfoService.findByName(username);
        Collection<GrantedAuthority> authList = getAuthorities();
        UserDetails ud = new User(username, ui.getPassword(), true, true, true, true, authList);
        return ud;
    }

    private Collection<GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authList = new ArrayList();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authList;
    }
}
