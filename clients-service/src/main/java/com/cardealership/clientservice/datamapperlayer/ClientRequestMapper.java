package com.cardealership.clientservice.datamapperlayer;

import com.cardealership.clientservice.datalayer.Client;
import com.cardealership.clientservice.presentationlayer.ClientRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientRequestMapper {

    @Mapping(target = "clientIdentifier", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    Client entityToRequestModel(ClientRequestModel clientRequestModel);

}
