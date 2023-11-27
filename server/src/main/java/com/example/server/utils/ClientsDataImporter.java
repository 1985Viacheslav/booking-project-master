package com.example.server.utils;

import com.example.server.dto.ClientDto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public interface ClientsDataImporter {
    List<ClientDto> loadFromXml(String path) throws IOException;
}
