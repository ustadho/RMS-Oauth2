package com.mitrais.rms.resource.controller;
//import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mitrais.rms.resource.controller.dto.UserDTO;
import com.mitrais.rms.resource.controller.util.HeaderUtil;
import com.mitrais.rms.resource.domain.Role;
import com.mitrais.rms.resource.domain.User;
import com.mitrais.rms.resource.exception.EntityNotFoundException;
import com.mitrais.rms.resource.repository.UserRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("api/setting/users")
public class UserController {
    private final String ENTITY_NAME = "User";
    private final UserRepository repository;
    Logger log = LoggerFactory.getLogger(UserController.class);
    public UserController(UserRepository repo) {
        this.repository = repo;
    }

    //Both @PreAuthorize and @PostAuthorize annotations provide expression-based access control. Hence, predicates can be written using SpEL (Spring Expression Language).
    //@PreAuthorize("hasRole('ROLE_VIEWER')")

    //The @RoleAllowed annotation is the JSR-250’s equivalent annotation of the @Secured annotation.
    //@RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" })

    //The @PreAuthorize(“hasRole(‘ROLE_VIEWER’)”) has the same meaning as @Secured(“ROLE_VIEWER”)
    //@Secured({"ADMIN"})
    //@PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasPermission(#report['name'], 'Employee', 'expenseReport.allowed')")
//    @PreAuthorize("hasPermission('ROLE_VIEWER')")
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('can_read_user')")
    public ResponseEntity<Iterable<UserDTO>> findAll(Pageable pg) throws URISyntaxException{
        final Page<UserDTO> page = repository.findAll(pg).map(UserDTO::new);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api/users");
        return new ResponseEntity<>(page, HttpStatus.OK);

    }

    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('can_read_user')")
    public Iterable<User> findAll2() throws URISyntaxException{
        return repository.findAll();

    }

    @PreAuthorize("hasAnyAuthority('can_update_user')")
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User {}", user);
        if(user.getId()!=null){
            return null;//update(user);
        }
        if(repository.findByUsername(user.getUsername()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                            "userExists", "User with value '"+ user.getUsername()+"' already exists"))
                    .body(null);

        }else{
            User newUser = repository.save(user);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getId()))
                    .headers(HeaderUtil.createAlert( "A User is created with identifier " + newUser.getId(), String.valueOf(newUser.getId())))
                    .body(newUser);
        }
    }

   /* @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User {}", user);
        Optional<User> existingUser = repository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(user.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "nameExists", "Username already in use")).body(null);
        }
        Optional<User> updatedUser = Optional.of(repository.save(user));

        return ResponseUtil.wrapOrNotFound(updatedUser,
                HeaderUtil.createAlert("A User is updated with identifier " + user.getUsername(), user.getUsername()));
    }*/

    @Transactional
    @GetMapping("{id}")
    public User findOne(@PathVariable Long id)  throws EntityNotFoundException {
        log.debug("REST request to get User by id : {}", id);

        Optional<User> result = repository.findById(id);
        if (!result.isPresent()) {
            throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
        }
        result.get().getRoles().size();
        for(Role r: result.get().getRoles()) {
        	r.getPermissions().size();
        }
        return result.get();

    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.debug("REST request to delete User: {}", id);
        repository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A User is deleted with identifier: "+id, String.valueOf(id))).build();
    }

}
