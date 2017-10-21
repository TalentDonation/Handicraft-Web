package com.handicraft.core.service.Excels;

import com.handicraft.core.dto.Excels.Excel;
import com.handicraft.core.repository.Excels.ExcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelService {

    @Autowired
    ExcelRepository excelRepository;

    public List<Excel> findSheets() {
        return excelRepository.findAll();
    }

    public Excel insertSheets(Excel sheets) {

        return excelRepository.save(sheets);
    }

    public List<Excel> updageSheets(List<Excel> sheetsList) {
        return excelRepository.save(sheetsList);
    }

    public void deleteSheets() {
        excelRepository.deleteAll();
    }

    public Excel findSheetsBySid(long sid) {
        return excelRepository.findOne(sid);
    }

    public Excel updateSheetsBySid(Excel sheets) {
        return excelRepository.save(sheets);
    }

    public void deleteSheetsBySid(long sid) {
        excelRepository.delete(sid);
    }

    public Excel findLastSheetsBySid() {
        return excelRepository.findTopByOrderBySidDesc();
    }

}
