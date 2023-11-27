package com.example.server.utils;

import com.example.server.dto.ClientDto;
import com.example.server.dto.UserDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientsDataImporterImpl implements ClientsDataImporter {

    @Override
    public List<ClientDto> loadFromXml(String path) throws IOException {
        File fileWithUsersData = new File(path);
        List<ClientDto> clients = null;

        if (!fileWithUsersData.exists()) {
            return clients;
        }

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String textWithUsersData = new String(java.nio.file.Files.readAllBytes(fileWithUsersData.toPath()));

        List<UserDto> userDtoList = xmlMapper.readValue(textWithUsersData, xmlMapper.getTypeFactory().constructCollectionType(List.class, UserDto.class));

        clients = userDtoList.parallelStream()
                .map(user -> user.toClientDto())
                .collect(Collectors.toList());

        return clients;
    }
}
