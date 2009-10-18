package ubc.eece419.pod1.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.UserRepository;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.security.SecurityUtils;

@Transactional
@Controller
public class UserController extends CRUDController<User> {

    @Autowired
    UserRepository userRepository;

    @Override
    @Secured("ROLE_ADMIN")
    public ModelAndView delete(Long id) {
        return super.delete(id);
    }

    @Override
    public ModelAndView save(User bound) {
        // TODO Auto-generated method stub
        if (!(bound.isNewEntity() || bound.equals(SecurityUtils.getCurrentUser()))) {
            // only admins can edit /other/ users
            SecurityUtils.assertAdmin();
        }

        // TODO: is this a validation step? for now, it'll just take effect silently
        if (!SecurityUtils.isAdmin()) {
            bound.setRoles(Roles.USER);
        }

        // TODO: remember to update the SecurityContext if editing the logged-in user

        // right now, password is the only changable field
        if (!bound.isNewEntity()) {
            User old = userRepository.findById(bound.getId());
            if (StringUtils.hasText(bound.getPassword())) {
                if (!bound.getPassword().equals(old.getPassword())) {
                    old.setPassword(User.encryptPassword(bound.getPassword(), bound.getUsername()));
                    userRepository.save(old);
                }
            }
        } else {
            bound.setPassword(User.encryptPassword(bound.getPassword(), bound.getUsername()));
            userRepository.save(bound);
        }
        return redirectToListView();
    }

    @Override
    public ModelAndView edit(Long id) {
        User entity;
        if (id == null) {
            entity = getNewEntity();
        } else {
            entity = getRepository().findById(id);
            if (!entity.equals(SecurityUtils.getCurrentUser())) {
                // only admins can edit /other/ users
                SecurityUtils.assertAdmin();
            }
        }
        entity.setPassword("");
        return editView(entity);
    }

    @Override
    protected User getNewEntity() {
        User u = new User();
        u.setRoles("ROLE_USER"); // default to least privilege
        return u;
    }

    @Override
    protected UserRepository getRepository() {
        return userRepository;
    }

    @RequestMapping("/**/login")
    public ModelAndView login() {
        log.info("login " + getEntityName());
        ModelAndView mav = new ModelAndView("user/login");
        return mav;

    }
}
