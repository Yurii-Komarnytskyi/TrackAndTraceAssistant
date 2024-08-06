package com.ykomarnytskyi2022.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ykomarnytskyi2022.freight.Trackable;

public interface TrackableRepository extends CrudRepository<Trackable, Long> {

//	@Modifying
//	@Query("DELETE FROM Trackable s WHERE s.shipmentID = '18225017'")
//	void deleteByShipmentID(String shipmentID);
}
