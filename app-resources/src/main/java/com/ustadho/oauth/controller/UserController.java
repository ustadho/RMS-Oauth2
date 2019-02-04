package com.ustadho.oauth.controller;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ustadho.oauth.controller.dto.UserDTO;
import com.ustadho.oauth.controller.util.HeaderUtil;
import com.ustadho.oauth.dao.UserDao;
import com.ustadho.oauth.domain.User;
import com.ustadho.oauth.exception.EntityNotFoundException;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("api/setting/users")
public class UserController {
    private final String ENTITY_NAME = "User";
    private final UserDao dao;
    Logger log = LoggerFactory.getLogger(UserController.class);
    public UserController(UserDao dao) {
        this.dao = dao;
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
    public ResponseEntity<Iterable<UserDTO>> findAll(Pageable pg) throws URISyntaxException{
        final Page<UserDTO> page = dao.findAll(pg).map(UserDTO::new);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api/users");
        return new ResponseEntity<>(page, HttpStatus.OK);

    }

    @GetMapping("all")
    public Iterable<User> findAll2() throws URISyntaxException{
        return dao.findAll();

    }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User {}", user);
        if(user.getId()!=null){
            return update(user);
        }
        if(dao.findOneByUsername(user.getUsername()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                            "userExists", "User with value '"+ user.getUsername()+"' already exists"))
                    .body(null);

        }else{
            User newUser = dao.save(user);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getId()))
                    .headers(HeaderUtil.createAlert( "A User is created with identifier " + newUser.getId(), newUser.getId()))
                    .body(newUser);
        }
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User {}", user);
        Optional<User> existingUser = dao.findOneByUsername(user.getUsername());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(user.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "nameExists", "Username already in use")).body(null);
        }
        Optional<User> updatedUser = Optional.of(dao.save(user));

        return ResponseUtil.wrapOrNotFound(updatedUser,
                HeaderUtil.createAlert("A User is updated with identifier " + user.getUsername(), user.getUsername()));
    }

    @Transactional
    @GetMapping("{id}")
    public User findOne(@PathVariable String id)  throws EntityNotFoundException {
        log.debug("REST request to get User by id : {}", id);

        Optional<User> result = dao.findById(id);
        if (!result.isPresent()) {
            throw new EntityNotFoundException(User.class, "id", id);
        }
        result.get().getRole().getAuthorities().size();
        return result.get();

    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        log.debug("REST request to delete User: {}", id);
        dao.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A User is deleted with identifier: "+id, id)).build();
    }

}
