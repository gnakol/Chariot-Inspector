package fr_scapartois_auto.chariot_inspector.role.controller;

import fr_scapartois_auto.chariot_inspector.role.dtos.RoleDTO;
import fr_scapartois_auto.chariot_inspector.role.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("role")
@Slf4j
public class RoleController {

    private final RoleService roleService;

    @GetMapping("all-role")
    public ResponseEntity<Page<RoleDTO>> allRole(Pageable pageable)
    {
        return ResponseEntity.ok(this.roleService.all(pageable));
    }

    @PostMapping("add-new-role")
    public ResponseEntity<RoleDTO> addNewRole(@Validated @RequestBody RoleDTO roleDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.add(roleDTO));
    }

    @PutMapping("update-role/{idRole}")
    public ResponseEntity<RoleDTO> updateRole(@Validated @PathVariable Long idRole, @RequestBody RoleDTO roleDTO)
    {
        return ResponseEntity.status(202).body(this.roleService.update(idRole, roleDTO));
    }

    @DeleteMapping("remove-role/{idRole}")
    public ResponseEntity<String> removeRole(@Validated @PathVariable Long idRole)
    {
        this.roleService.remove(idRole);

        return ResponseEntity.status(202).body("Role with id : " +idRole+ " successfully delete");
    }

    @GetMapping("get-role-by-id/{idRole}")
    public ResponseEntity<RoleDTO> getRole(@Validated @PathVariable Long idRole)
    {
        return this.roleService.getById(idRole)
                .map(role -> {
                    log.info("role with id : " +idRole+ "was found");
                    return new  ResponseEntity<>(role, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("role with id : " +idRole+ "was not found");
                    return new RuntimeException("not found this role");
                });
    }
}
