package com.banar.postal.repository;

import com.banar.postal.model.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
}
