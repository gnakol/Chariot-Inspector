package fr_scapartois_auto.chariot_inspector.role.services;

import fr_scapartois_auto.chariot_inspector.role.beans.Role;
import fr_scapartois_auto.chariot_inspector.role.dtos.RoleDTO;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapper;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapperImpl;
import fr_scapartois_auto.chariot_inspector.role.repositories.RoleRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements Webservices<RoleDTO> {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper = new RoleMapperImpl();

    public List<RoleDTO> getDefaultRoles()
    {
        Role defaulRole = this.roleRepository.findByRoleName("RECEPTIONNAIRE")
                .orElseThrow(() -> new IllegalStateException("Default role 'customer' is not defined in database"));

        RoleDTO roleDTO = this.roleMapper.fromRole(defaulRole);



        return Collections.singletonList(roleDTO);
    }

    @Override
    public Page<RoleDTO> all(Pageable pageable) {
        return this.roleRepository.findAll(pageable)
                .map(this.roleMapper::fromRole);
    }

    @Override
    public RoleDTO add(RoleDTO e) {
        return this.roleMapper.fromRole(this.roleRepository.save(this.roleMapper.fromRoleDTO(e)));
    }

    @Override
    public RoleDTO update(Long id, RoleDTO e) {
        return this.roleMapper.fromRole(this.roleRepository.findById(id)
                .map(r -> {
                    if (r.getRoleName() != null)
                        r.setRoleName(e.getRoleName());
                    return this.roleRepository.save(r);
                })
                .orElseThrow(() -> new RuntimeException("role with id : "+id+ "was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Role> role = this.roleRepository.findById(id);

        if (role.isEmpty())
        {
            throw new RuntimeException("role with :"+id+ "was not found");
        }

        this.roleRepository.delete(role.get());

    }

    @Override
    public Optional<RoleDTO> getById(Long id) {
        return this.roleRepository.findById(id)
                .map(this.roleMapper::fromRole);
    }
}
