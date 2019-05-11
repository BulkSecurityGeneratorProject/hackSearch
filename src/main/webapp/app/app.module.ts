import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { HackSearchSharedModule } from 'app/shared';
import { HackSearchCoreModule } from 'app/core';
import { HackSearchAppRoutingModule } from './app-routing.module';
import { HackSearchHomeModule } from './home/home.module';
import { HackSearchAccountModule } from './account/account.module';
import { HackSearchEntityModule } from './entities/entity.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import * as moment from 'moment';
import { HackSearchAppHiThereModule } from './hi-there/hi-there.module';
import { HackSearchAppImpressumModule } from './impressum/impressum.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import { NavbarService } from 'app/layouts/navbar/navbar.service';
import { FooterService } from 'app/layouts/footer/footer.service';

@NgModule({
    imports: [
        BrowserModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            alertTimeout: 5000,
            i18nEnabled: true,
            defaultI18nLang: 'de'
        }),
        HackSearchSharedModule.forRoot(),
        HackSearchCoreModule,
        HackSearchHomeModule,
        HackSearchAccountModule,
        HackSearchAppHiThereModule,
        HackSearchAppImpressumModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        HackSearchEntityModule,
        HackSearchAppRoutingModule,
        BrowserAnimationsModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        },
        NavbarService,
        FooterService
    ],
    bootstrap: [JhiMainComponent]
})
export class HackSearchAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
