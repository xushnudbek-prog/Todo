package project.spring.jdbc.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.spring.jdbc.dao.AuthPermissionDao;
import project.spring.jdbc.dao.AuthRoleDao;
import project.spring.jdbc.dao.AuthUserDao;
import project.spring.jdbc.domains.AuthPermission;
import project.spring.jdbc.domains.AuthRole;
import project.spring.jdbc.domains.AuthUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthUserDao authUserDao;
    private final AuthRoleDao authRoleDao;
    private final AuthPermissionDao authPermissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        int userId = authUser.getId();
        List<AuthRole> roles = authRoleDao.findAllByUserId(userId);
        for (AuthRole role : roles) {
            List<AuthPermission> permissions = authPermissionDao.findAllByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        authUser.setRoles(roles);
        return new AuthUserDetails(authUser);
    }
}
