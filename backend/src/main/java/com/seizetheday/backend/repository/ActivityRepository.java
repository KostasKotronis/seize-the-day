package com.seizetheday.backend.repository;

import com.seizetheday.backend.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
