package com.yil.organization.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextStartedEvent> {
    public final static long createOrganizationPermissionId = 500;

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("Start Up Events");
        System.out.println(new Date(event.getTimestamp()));
        System.out.println("----------------------");
    }
}
