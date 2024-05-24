package com.salesmanager.core.business.repositories.accesscontrol;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.accesscontrol.AccessControl;

public interface AccessControlRepository extends JpaRepository<AccessControl, Integer> {

}
