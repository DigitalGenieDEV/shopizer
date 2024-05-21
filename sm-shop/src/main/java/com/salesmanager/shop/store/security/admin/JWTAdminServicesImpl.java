package com.salesmanager.shop.store.security.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.manager.ManagerService;
import com.salesmanager.core.model.manager.Manager;
import com.salesmanager.shop.admin.security.SecurityDataAccessException;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.store.security.user.JWTUser;


@Service("jwtAdminDetailsService")
public class JWTAdminServicesImpl implements UserDetailsService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTAdminServicesImpl.class);

	@Inject
	private ManagerService managerService;
	
	public final static String ROLE_PREFIX = "ROLE_";//Spring Security 4


	private UserDetails userDetails(String userName, Manager user, Collection<GrantedAuthority> authorities) {
		Date lastModified = null;
	
		return new JWTUser(
        		user.getId(),
        		userName,
        		user.getAdminName(),
        		"",
                user.getAdminEmail(),
                user.getAdminPassword(),
                authorities,
                true,
                lastModified
        );
		
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Manager user = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		try {
			
				LOGGER.debug("Loading user by user id: {}", userName);
				user = managerService.getByUserName(userName);
				if(user==null) {
					//return null;
					throw new UsernameNotFoundException("User " + userName + " not found");
				}
				
				GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + Constants.PERMISSION_AUTHENTICATED);//required to login
				authorities.add(role); 
				
				List<Integer> groupsId = new ArrayList<Integer>();
				groupsId.add(user.getGrpId());
				
				if(CollectionUtils.isNotEmpty(groupsId)) {
		    		GrantedAuthority auth = new SimpleGrantedAuthority(user.getGrpName());
		    		authorities.add(auth);
			    }


		} catch (ServiceException e) {
			LOGGER.error("Exception while querrying customer",e);
			throw new SecurityDataAccessException("Cannot authenticate customer",e);
		}

		return userDetails(userName, user, authorities);
	}
	
	public void updateLoginFailCount(String emplId) throws UsernameNotFoundException{
		try {
			managerService.updateLoginFailCount(emplId);
		} catch (ServiceException e) {
			throw new SecurityDataAccessException("Cannot authenticate customer",e);
		}
	}

}
