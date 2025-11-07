package com.project_management.service;


import com.project_management.model.Response;

public interface OrganizationService {

    Response createOrganization(String name, String timezone);

    Response getAllOrganizations();

    Response getOrganizationById(Long id);

    Response getOrganizationByName(String name);

    Response updateOrganization(Long id, String newName, String timezone);

    Response deleteOrganization(Long id);
}
