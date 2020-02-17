package org.flowable.ui.common.security;

import org.flowable.idm.api.User;
import org.flowable.ui.common.model.RemoteUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 重写获取当前用户信息
 *
 * @author pm
 * @date 2019/7/28 18:06
 */
public class SecurityUtils {
    private static User assumeUser;

    private SecurityUtils() {
    }


    public static String getCurrentUserId() {
        User user = getCurrentUserObject();
        if (user != null) {
            return user.getId();
        }
        return null;
    }


    public static User getCurrentUserObject() {
        if (assumeUser != null) {
            return assumeUser;
        }

        RemoteUser user = new RemoteUser();
        user.setId("admin");
        user.setDisplayName("admin");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmail("admin@admin.com");
        user.setPassword("admin");
        List<String> pris = new ArrayList<>();
        pris.add(DefaultPrivileges.ACCESS_MODELER);
        pris.add(DefaultPrivileges.ACCESS_IDM);
        pris.add(DefaultPrivileges.ACCESS_ADMIN);
        pris.add(DefaultPrivileges.ACCESS_TASK);
        pris.add(DefaultPrivileges.ACCESS_REST_API);
        user.setPrivileges(pris);
        return user;
    }

    public static FlowableAppUser getCurrentFlowableAppUser() {
        FlowableAppUser user = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null && securityContext.getAuthentication() != null) {
            Object principal = securityContext.getAuthentication().getPrincipal();
            if (principal instanceof FlowableAppUser) {
                user = (FlowableAppUser) principal;
            }
        }
        return user;
    }

    public static boolean currentUserHasCapability(String capability) {
        FlowableAppUser user = getCurrentFlowableAppUser();
        if(user!=null){
	        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
	            if (capability.equals(grantedAuthority.getAuthority())) {
	                return true;
	            }
	        }
        }
        return false;
    }

    public static void assumeUser(User user) {
        assumeUser = user;
    }

    public static void clearAssumeUser() {
        assumeUser = null;
    }

}
