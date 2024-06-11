package com.salesmanager.core.business.repositories.banner;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.banner.Banner;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

}
