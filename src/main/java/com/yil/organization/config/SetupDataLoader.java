package com.yil.organization.config;

import com.yil.organization.model.Organization;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.repository.OrganizationDao;
import com.yil.organization.repository.OrganizationTypeDao;
import com.yil.organization.service.OrganizationService;
import com.yil.organization.service.OrganizationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextStartedEvent> {

    @Autowired
    private OrganizationTypeDao organizationTypeDao;

    @Autowired
    private OrganizationDao organizationDao;


    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("Start Up Events");
        System.out.println(new Date(event.getTimestamp()));
        System.out.println("----------------------");

        try {
            initOrgTypes();
            initDefaultOrg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOrgTypes() {
        OrganizationTypeService.gercekKisi = OrganizationType
                .builder()
                .id(1)
                .name("Gerçek Kişi")
                .real(true)
                .build();
        organizationTypeDao.save(OrganizationTypeService.gercekKisi);
        OrganizationTypeService.tuzelKisi = OrganizationType
                .builder()
                .id(2)
                .name("Tüzel Kişi")
                .real(false)
                .build();
        organizationTypeDao.save(OrganizationTypeService.tuzelKisi);
    }

    private void initDefaultOrg() {
        OrganizationService.bireysel = Organization
                .builder()
                .id(1L)
                .name("Bireysel Kullanım")
                .description("Bireysel kullanıcıların organizasyonu")
                .organizationTypeId(OrganizationTypeService.gercekKisi.getId())
                .build();
        organizationDao.save(OrganizationService.bireysel);
        OrganizationService.kurumsal = Organization
                .builder()
                .id(2L)
                .name("Kurumlar")
                .description("Kurumlar")
                .organizationTypeId(OrganizationTypeService.tuzelKisi.getId())
                .build();
        organizationDao.save(OrganizationService.kurumsal);
    }
}
