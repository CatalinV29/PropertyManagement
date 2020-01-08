package com.sda.propertyManager.config;
        import com.sda.propertyManager.model.Role;
        import com.sda.propertyManager.model.User;
        import com.sda.propertyManager.service.RoleService;
        import com.sda.propertyManager.service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;

        import java.util.ArrayList;
        import java.util.List;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        String password = user.getPassword();

        List<Role> roles = roleService.findByUserId(user.getUserId());

        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role : roles) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorityList.add(grantedAuthority);
        }

        UserDetails userDetails = new CustomUserDetails(username, password, authorityList,
                user.getUserId(), user.getUserName(),user.getPassword());

        return userDetails;
    }

}
