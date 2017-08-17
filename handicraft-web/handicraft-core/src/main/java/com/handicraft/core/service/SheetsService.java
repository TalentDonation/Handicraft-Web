package com.handicraft.core.service;

import com.handicraft.core.dto.Sheets;

import java.util.List;

public interface SheetsService {

    List<Sheets> findSheets();

    Sheets insertSheets(Sheets sheets);

    List<Sheets> updageSheets(List<Sheets> sheetsList);

    void deleteSheets();

    Sheets findSheetsBySid(long sid);

    Sheets updateSheetsBySid(Sheets sheets);

    void deleteSheetsBySid(long sid);

    Sheets findLastSheetsBySid();

}
