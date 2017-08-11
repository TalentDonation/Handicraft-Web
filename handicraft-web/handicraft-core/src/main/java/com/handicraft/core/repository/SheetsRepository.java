package com.handicraft.core.repository;

import com.handicraft.core.dto.Sheets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetsRepository extends JpaRepository<Sheets , Long>{
}
