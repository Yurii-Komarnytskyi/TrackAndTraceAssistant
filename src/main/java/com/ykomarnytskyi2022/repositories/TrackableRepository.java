package com.ykomarnytskyi2022.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ykomarnytskyi2022.freight.Trackable;

import jakarta.transaction.Transactional;

public interface TrackableRepository extends CrudRepository<Trackable, Long> {

	@Transactional
	@Modifying
	@Query("update Trackable t set t.shipmentID = ?1 where t.id = ?2")
	void setShipmentIDById(String shipmentID, Long id);

	@Transactional
	@Modifying
	@Query("update Trackable t set t.scac = ?1 where t.id = ?2")
	void setScacById(String scac, Long id);

	@Transactional
	@Modifying
	@Query("update Trackable t set t.destinationCity = ?1 where t.id = ?2")
	void setDestinationCityById(String destinationCity, Long id);

	@Transactional
	@Modifying
	@Query("update Trackable t set t.originCity = ?1 where t.id = ?2")
	void setOriginCityById(String originCity, Long id);

}
