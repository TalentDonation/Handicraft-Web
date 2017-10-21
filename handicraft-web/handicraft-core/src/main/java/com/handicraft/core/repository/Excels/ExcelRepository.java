package com.handicraft.core.repository.Excels;

import com.handicraft.core.dto.Excels.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends JpaRepository<Excel, Long>{


    Excel findTopByOrderBySidDesc();

}
