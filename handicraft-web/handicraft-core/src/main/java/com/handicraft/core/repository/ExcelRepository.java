package com.handicraft.core.repository;

import com.handicraft.core.dto.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends JpaRepository<Excel, Long>{


    Excel findTopByOrderBySidDesc();

}
