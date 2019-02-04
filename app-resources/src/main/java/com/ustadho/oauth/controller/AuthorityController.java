package com.ustadho.oauth.controller;
import com.ustadho.oauth.controller.util.HeaderUtil;
import com.ustadho.oauth.dao.AuthorityDao;
import com.ustadho.oauth.domain.Authority;
import com.ustadho.oauth.exception.EntityNotFoundException;

import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("api/setting/authorities")
public class AuthorityController {
    private final String ENTITY_NAME = "Authority";
    private final AuthorityDao dao;
    Logger log = LoggerFactory.getLogger(AuthorityController.class);
    public AuthorityController(AuthorityDao dao) {
        this.dao = dao;
    }

    @GetMapping("")
    public Iterable<Authority> findAll(){
        return dao.findAll();
    }

    @PostMapping
    public ResponseEntity<Authority> create(@RequestBody Authority auth) throws URISyntaxException {
        log.debug("REST request to save Authority {}", auth);
        if(auth.getId()!=null){
            return update(auth);
        }
        if(dao.findOneByName(auth.getName()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                            "AuthorityValueExists", "Authority with value '"+ auth.getName()+"' already exists"))
                    .body(null);

        }else{
            Authority newAuth = dao.save(auth);
            return ResponseEntity.created(new URI("/Authoritys/" + newAuth.getId()))
                    .headers(HeaderUtil.createAlert( "A Authority is created with identifier " + newAuth.getId(), newAuth.getId()))
                    .body(newAuth);
        }
    }

    @PutMapping
    public ResponseEntity<Authority> update(@RequestBody Authority auth) throws URISyntaxException {
        log.debug("REST request to save Authority {}", auth);
        Optional<Authority> existingAuthority = dao.findOneByName(auth.getName());
        if (existingAuthority.isPresent() && (!existingAuthority.get().getId().equals(auth.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "valueExists", "Value already in use")).body(null);
        }
        Optional<Authority> updatedUser = Optional.of(dao.save(auth));

        return ResponseUtil.wrapOrNotFound(updatedUser,
                HeaderUtil.createAlert("A Authority is updated with identifier " + auth.getName(), auth.getName()));
    }

    @GetMapping("{id}")
    public Authority getAuthority(@PathVariable String id)  throws EntityNotFoundException {
        log.debug("REST request to get Authority by id : {}", id);

        Optional<Authority> result = dao.findById(id);
        if (!result.isPresent()) {
            throw new EntityNotFoundException(Authority.class, "id", id);
        }
        return result.get();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        log.debug("REST request to delete Authority: {}", id);
        dao.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A Authority is deleted with identifier: "+id, id)).build();
    }

}
