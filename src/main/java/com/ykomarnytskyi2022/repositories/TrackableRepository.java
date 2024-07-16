package com.ykomarnytskyi2022.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ykomarnytskyi2022.freight.Trackable;

public interface TrackableRepository extends CrudRepository<Trackable, Long> {

}
