package com.ykomarnytskyi2022.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.ykomarnytskyi2022.freight.Trackable;

public interface TrackableRepository extends ListCrudRepository<Trackable, Long>, TrackableBaseRepository {



}
