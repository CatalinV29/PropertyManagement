package com.sda.propertyManager.service;

        import com.sda.propertyManager.model.Role;
        import com.sda.propertyManager.repository.RoleRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findByUserId(Integer userId){
        return roleRepository.findByUserId(userId);
    }

}
