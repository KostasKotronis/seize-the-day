package com.seizetheday.backend.repository;

import com.seizetheday.backend.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

}