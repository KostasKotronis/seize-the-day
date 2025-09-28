package com.seizetheday.backend.repository;

import com.seizetheday.backend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
