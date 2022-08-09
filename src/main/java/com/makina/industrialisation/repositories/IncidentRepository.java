package com.makina.industrialisation.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.makina.industrialisation.models.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, UUID>,
JpaSpecificationExecutor<Incident> {

}
