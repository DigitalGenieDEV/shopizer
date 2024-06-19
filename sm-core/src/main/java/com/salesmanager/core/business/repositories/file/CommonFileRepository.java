package com.salesmanager.core.business.repositories.file;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.common.file.CommonFile;

public interface CommonFileRepository  extends JpaRepository<CommonFile, Integer> {

}
