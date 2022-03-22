package br.com.nielsonferreira.listener;

import br.com.nielsonferreira.data.model.Revision;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class EntityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {

        Revision revision = (Revision) revisionEntity;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object loggedUser = auth.getPrincipal();

        if (loggedUser instanceof UserDetails){
            revision.setUser(((UserDetails) loggedUser).getUsername());
        }
    }
}
