package com.handicraft.core.service;

import com.handicraft.core.dto.Sheets;
import com.handicraft.core.repository.SheetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetsServiceImp implements SheetsService{

    @Autowired
    SheetsRepository sheetsRepository;

    @Override
    public List<Sheets> findSheets() {
        return sheetsRepository.findAll();
    }

    @Override
    public Sheets insertSheets(Sheets sheets) {

        return sheetsRepository.save(sheets);
    }

    @Override
    public List<Sheets> updageSheets(List<Sheets> sheetsList) {
        return sheetsRepository.save(sheetsList);
    }

    @Override
    public void deleteSheets() {
        sheetsRepository.deleteAll();
    }

    @Override
    public Sheets findSheetsBySid(long sid) {
        return sheetsRepository.findOne(sid);
    }

    @Override
    public Sheets updateSheetsBySid(Sheets sheets) {
        return sheetsRepository.save(sheets);
    }

    @Override
    public void deleteSheetsBySid(long sid) {
        sheetsRepository.delete(sid);
    }

    @Override
    public Sheets findLastSheetsBySid() {
        return sheetsRepository.findTopByOrderBySidDesc();
    }

}
